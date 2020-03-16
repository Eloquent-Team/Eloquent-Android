package berlin.eloquent.eloquentandroid.recorder

import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.Navigation
import berlin.eloquent.eloquentandroid.R
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecorderViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Attributes
     */
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var timer: CountDownTimer
    private var timePassed = 0L

    /**
     * Live Data
     */
    private  val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> get() = _recordingState

    private val _timestamp = MutableLiveData<String>()

    private val _outputFile = MutableLiveData<String>()
    val outputFile: LiveData<String> get() = _outputFile

    private val _isPlayingRecording = MutableLiveData<Boolean>()
    val isPlayingRecording: LiveData<Boolean> get() = _isPlayingRecording

    private val _currentTimeCode = MutableLiveData<Long>()

    val timeCodeText: LiveData<String> = Transformations.map(_currentTimeCode) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        _recordingState.value = RecordingState.STOPPED
        _isPlayingRecording.value = false
        _currentTimeCode.value = 0L
        _outputFile.value = ""
    }

    /**
     * Configures a MediaRecorder object with predefined attributes
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
     * Returns the current timestamp in the given pattern as a String
     *
     * @param pattern
     * @example yyyy-MM-DD or HH-mm-ss
     * @return Formatted String in given pattern
     */
    private fun getCurrentTimestamp(pattern: String = ""): String {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneOffset.UTC).format(Instant.now())
    }

    /**
     * Returns a CountDownTimer which counts up to display the timecode in the recorder screen
     * It counts up at the given time
     *
     * @param time
     * @return CountDownTimer with given time
     */
    private fun getTimer(time: Long) : CountDownTimer {
        return object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (_recordingState.value == RecordingState.PAUSED) {
                    cancel()
                    Log.i("RecorderViewModel", "timer canceled")
                } else {
                    _currentTimeCode.value = (Long.MAX_VALUE - millisUntilFinished) / 1000
                    timePassed = millisUntilFinished
                    Log.i("RecorderViewModel", "Timepassed $timePassed")
                }
            }
            override fun onFinish() {}
        }
    }

    /**
     * Starts the MediaRecorder object and sets the "isRecording" value to true and the
     * "recordingPaused" value to false
     * Starts a new Timer starting at zero which stops when the recording gets paused
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
                timer = getTimer(Long.MAX_VALUE).start()
            }
        }
    }

    /**
     * If "isRecording" is true, the MediaRecorder stops the current recording
     * and releases itself, else nothing will happen
     */
    fun stopRecording() {
        if (_recordingState.value != RecordingState.STOPPED) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            timer.cancel()
            _recordingState.value = RecordingState.STOPPED
            mediaRecorder = null
        }
    }

    /**
     * If "isRecording" is true, the MediaRecorder pauses the current recording
     * and sets "recordingPaused" to true, else it will call the function resumeRecording()
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
     * Resumes the paused recording and set "recordingPaused" to false
     * Starts a new timer and starts with the already passed time and stops when
     * the recording gets paused
     */
    private fun resumeRecording() {
        mediaRecorder?.resume()
        _recordingState.value = RecordingState.RECORDING
        timer = getTimer(timePassed).start()
    }

    /**
     * Override for onCleared lifecycle hook to release th MediaRecorder object
     * if the app gets into the background and sets it to null
     */
    override fun onCleared() {
        super.onCleared()
        mediaRecorder?.release()
        mediaRecorder = null
    }

}