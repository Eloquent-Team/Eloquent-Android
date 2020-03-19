package berlin.eloquent.eloquentandroid.recorder

import android.app.Application
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.models.Recording
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecorderViewModel(application: Application) : AndroidViewModel(application) {

    // Attributes
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var timer: CountDownTimer
    private var timePassed = 0L

    // Live Data
    private  val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> get() = _recordingState

    private val _timestamp = MutableLiveData<String>()

    private val _outputFile = MutableLiveData<String>()
    val outputFile: LiveData<String> get() = _outputFile

    private val _currentTimeCode = MutableLiveData<Long>()

    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    val timeCodeText: LiveData<String> = Transformations.map(_currentTimeCode) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        _recordingState.value = RecordingState.STOPPED
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
        _outputFile.value = getApplication<Application>().getExternalFilesDir(null)?.absolutePath + "/recording_$_timestamp"
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
    private fun getCountUpTimer(startTime: Long) : CountDownTimer {
        return object: CountDownTimer(startTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (_recordingState.value == RecordingState.PAUSED) {
                    cancel()
                    Log.i("RecorderViewModel", "timer canceled")
                } else {
                    _currentTimeCode.value = (Long.MAX_VALUE - millisUntilFinished) / 1000
                    Log.i("RecorderViewModel", "${_currentTimeCode.value!!}")
                    timePassed = millisUntilFinished
                    Log.i("RecorderViewModel", "Timepassed $timePassed")
                }
            }
            override fun onFinish() {}
        }
    }

    /**
     * Starts the MediaRecorder if the RecordingState is not STOPPED and sets the RecordingState
     * to RECORDING.
     *
     * @throws IOException
     */
    fun startRecording() {
        if (_recordingState.value == RecordingState.STOPPED) {
            _timestamp.value = getCurrentTimestamp("yyyy-MM-dd_HH-mm-ss")
            mediaRecorder = getConfiguredMediaRecorder().apply {
                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e("RecorderFragment", "prepare() failed")
                }
                start()
                _recordingState.value = RecordingState.RECORDING
                timer = getCountUpTimer(Long.MAX_VALUE).start()
            }
        }
    }

    /**
     * If the RecordingState is not STOPPED, the MediaRecorder stops the current recording
     * and releases itself.
     */
    fun stopRecording() {
        if (_recordingState.value != RecordingState.STOPPED) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            timer.cancel()
            _recording.value = Recording(_outputFile.value!!, getCurrentTimestamp("yyyy-MM-dd_HH-mm-ss"), _currentTimeCode.value!!, listOf(), _outputFile.value!!)
            _recordingState.value = RecordingState.STOPPED
            mediaRecorder = null
        }
    }

    /**
     * If the RecordingState is RECORDING, the MediaRecorder pauses the current recording
     * and sets RecordingState to PAUSED, else it will call the function resumeRecording().
     */
    fun pauseRecording() {
        if (_recordingState.value == RecordingState.RECORDING) {
            mediaRecorder?.pause()
            _recordingState.value = RecordingState.PAUSED
        } else if (_recordingState.value == RecordingState.PAUSED) {
            resumeRecording()
        }
    }

    /**
     * Resumes the paused recording and sets the RecordingState to RECORDING.
     * Starts a new timer and starts with the already passed time.
     */
    private fun resumeRecording() {
        mediaRecorder?.resume()
        _recordingState.value = RecordingState.RECORDING
        timer = getCountUpTimer(timePassed).start()
    }

    /**
     * Override for onCleared lifecycle hook to release th MediaRecorder object
     * if the app gets into the background and sets it to null.
     */
    override fun onCleared() {
        super.onCleared()
        mediaRecorder?.release()
        mediaRecorder = null
    }

}