package berlin.eloquent.eloquentandroid.main.recorder

import android.app.Application
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class RecorderViewModel @Inject constructor(
        private val repo: IRecorderRepository,
        val application: Application
) : ViewModel() {

    // Attributes
    private lateinit var timer: CountDownTimer
    private var timePassed = 0L
    private val SAMPLE_RATE = 44100

    // Live Data
    private val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> get() = _recordingState

    private val _outputFile = MutableLiveData<String>()
    val outputFile: LiveData<String> get() = _outputFile

    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    private val _createdRecordingId = MutableLiveData<Long>()
    val createdRecordingId: LiveData<Long> get() = _createdRecordingId

    private val _currentTimeCode = MutableLiveData<Long>()

    val timeCodeText: LiveData<String> = Transformations.map(_currentTimeCode) { time ->
        DateUtils.formatElapsedTime(time)
    }

    lateinit var audioRecord: AudioRecord
    var shouldAudioRecordContinue = true

    private val minAudioBufferSize = AudioRecord.getMinBufferSize(
            SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
    )
    private lateinit var fileCreationStream: FileOutputStream

    init {
        _recordingState.value =
                RecordingState.NOT_STARTED
        _currentTimeCode.value = 0L
        _outputFile.value = ""
    }

    fun resetViewModel() {
        _recordingState.value = RecordingState.NOT_STARTED
        _currentTimeCode.value = 0L
        _outputFile.value = ""
    }

    /**
     * Configures an AudioRecord Object
     *
     * Allows for recording of raw audio files.
     */
    private fun configureAudioRecord() {
        audioRecord = AudioRecord.Builder()
                .setAudioSource(MediaRecorder.AudioSource.DEFAULT)
                .setAudioFormat(
                        AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(SAMPLE_RATE)
                                .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                                .build()
                )
                .setBufferSizeInBytes(minAudioBufferSize)
                .build()
    }

    private fun startAudioRecord() {
        configureAudioRecord()
        _outputFile.value = application.getExternalFilesDir(null)?.absolutePath + "/recording_${
            getCurrentTimestamp("yyyy-MM-dd_HH-mm-ss")
        }.raw"

        shouldAudioRecordContinue = true
        viewModelScope.launch(Dispatchers.IO) {
            var bufferSize = minAudioBufferSize

            if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
                bufferSize = SAMPLE_RATE * 2
            }
            val audioBuffer = ByteArray(bufferSize / 2)

            if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
                Log.e("Error", "Audio Record can't initialize!")
                return@launch
            }
            audioRecord.startRecording()

            Log.v("Success", "Start recording")
            while (shouldAudioRecordContinue) {
                audioRecord.read(audioBuffer, 0, audioBuffer.size)

                val outputFile = File(_outputFile.value!!)
                fileCreationStream = FileOutputStream(outputFile, true)
                fileCreationStream.write(audioBuffer)
            }
        }
    }

    private fun stopAudioRecord() {
        shouldAudioRecordContinue = false
        audioRecord.stop()
        audioRecord.release()
        fileCreationStream.close()
        Log.v("Success", "Recording stopped")
    }

    /**
     * Returns the current timestamp in the given pattern as a String.
     *
     * @param pattern like yyyy-MM-DD or HH-mm-ss
     * @return Formatted String in given pattern
     */
    private fun getCurrentTimestamp(pattern: String = ""): String {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneOffset.UTC).format(Instant.now())
    }

    /**
     * Returns a CountDownTimer which counts up to display the timecode in the recorder screen.
     * It counts up at the given time.
     *
     * @param startTime Long.MAX_VALUE when starting from zero or passed time value for restarting from there.
     * @return CountDownTimer with given time.
     */
    private fun getCountUpTimer(startTime: Long): CountDownTimer {
        return object : CountDownTimer(startTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTimeCode.value = (Long.MAX_VALUE - millisUntilFinished) / 1000
                timePassed = millisUntilFinished
            }
            override fun onFinish() {}
        }.start()
    }

    fun controlStartStopRecording() {
        when (_recordingState.value) {
            RecordingState.NOT_STARTED -> startRecording()
            else -> stopRecording()
        }
    }

    private fun startRecording() {
        startAudioRecord()
        timer = getCountUpTimer(Long.MAX_VALUE)
        _recordingState.value = RecordingState.RECORDING
    }

    private fun stopRecording() {
        stopAudioRecord()
        timer.cancel()

        val outputFileBytes = File(_outputFile.value!!).readBytes()
        val content = Base64.getEncoder().encodeToString(outputFileBytes)

        viewModelScope.launch {
            _recording.value = Recording()
            _recording.value!!.apply {
                title = "Rec_${getCurrentTimestamp("yyyy-MM-dd_HH:mm")}"
                date = getCurrentTimestamp("yyyy-MM-dd HH:mm:ss")
                length = _currentTimeCode.value!!
                fileUrl = _outputFile.value!!
                base64Content = content
            }
            repo.insertRecording(_recording.value!!)
            _createdRecordingId.value = repo.getNewestRecording()!!.recordingId
        }
        _recordingState.value =
            RecordingState.STOPPED
    }
}
