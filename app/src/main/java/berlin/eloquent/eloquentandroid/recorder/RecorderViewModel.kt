package berlin.eloquent.eloquentandroid.recorder

import android.app.Application
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.*
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RecorderViewModel @Inject constructor(val database: RecordingDao, val application: Application) : ViewModel() {

    // Attributes
    // create own job and scope, because viewModelScope has a bug with DI, it won't get called again
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var timer: CountDownTimer
    private var timePassed = 0L

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


    init {
        _recordingState.value = RecordingState.NOT_STARTED
        _currentTimeCode.value = 0L
        _outputFile.value = ""
    }

    fun resetViewModel() {
        _recordingState.value = RecordingState.NOT_STARTED
        _currentTimeCode.value = 0L
        _outputFile.value = ""
    }

    /**
     * Configures a MediaRecorder object with predefined attributes.
     *
     * @return Configured MediaRecorder with:
     *  AudioSource.MIC /
     *  OutputFormat.MPEG_4 /
     *  AudioEncoder.AAC
     */
    private fun getConfiguredMediaRecorder(): MediaRecorder {
        _outputFile.value = application.getExternalFilesDir(null)?.absolutePath + "/recording_${getCurrentTimestamp("yyyy-MM-dd_HH-mm-ss")}"
        return MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(_outputFile.value)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        }
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
                if (_recordingState.value == RecordingState.PAUSED) {
                    cancel()
                } else {
                    _currentTimeCode.value = (Long.MAX_VALUE - millisUntilFinished) / 1000
                    timePassed = millisUntilFinished
                }
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
        mediaRecorder = getConfiguredMediaRecorder().apply {
            try {
                prepare()
            } catch (e: IOException) {
                Log.e("ViewModel Recorder", "prepare() failed")
            }
            start()
            timer = getCountUpTimer(Long.MAX_VALUE)
            _recordingState.value = RecordingState.RECORDING
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        timer.cancel()
        coroutineScope.launch {
            _recording.value = Recording()
            _recording.value!!.apply {
                title = "Rec_${getCurrentTimestamp("yyyy-MM-dd_HH:mm")}"
                date = getCurrentTimestamp("yyyy-MM-dd HH:mm:ss")
                length = _currentTimeCode.value!!
                fileUrl = _outputFile.value!!
            }
            insert(_recording.value!!)
            _createdRecordingId.value = getNewestRecording()!!.recordingId
        }
        _recordingState.value = RecordingState.STOPPED
    }

    private suspend fun insert(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.insertRecording(recording)
        }
    }

    private suspend fun getNewestRecording(): Recording? {
        return withContext(Dispatchers.IO) {
            database.getNewestRecording()
        }
    }

    fun controlPauseResumeRecording() {
        when (_recordingState.value) {
            RecordingState.RECORDING -> pauseRecording()
            RecordingState.PAUSED -> resumeRecording()
            else -> Log.i("ViewModel Recorder", "not right action")
        }
    }

    private fun pauseRecording() {
        mediaRecorder?.pause()
        _recordingState.value = RecordingState.PAUSED
    }

    private fun resumeRecording() {
        mediaRecorder?.resume()
        timer = getCountUpTimer(timePassed)
        _recordingState.value = RecordingState.RECORDING
    }

    override fun onCleared() {
        super.onCleared()
        mediaRecorder?.release()
        mediaRecorder = null
    }

}
