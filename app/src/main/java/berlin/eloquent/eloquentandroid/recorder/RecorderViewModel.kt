package berlin.eloquent.eloquentandroid.recorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecorderViewModel: ViewModel() {

    /**
     * Parameter
     */
    var outputFile = ""
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var timer: CountDownTimer
    private var timePassed = 0L

    /**
     * Live Data
     */
    private val _isRecording = MutableLiveData<Boolean>()
    val isRecording: LiveData<Boolean> get() = _isRecording

    private val _timestamp = MutableLiveData<String>()
    val timeStamp: LiveData<String> get() = _timestamp

    private val _recordingPaused = MutableLiveData<Boolean>()
    val recordingPaused: LiveData<Boolean> get() = _recordingPaused

    private val _isPlayingRecording = MutableLiveData<Boolean>()

    private val _currentTimeCode = MutableLiveData<Long>()

    val timeCodeText: LiveData<String> = Transformations.map(_currentTimeCode) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        _isRecording.value = false
        _recordingPaused.value = false
        _isPlayingRecording.value = false
        _currentTimeCode.value = 0L
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
        return MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(outputFile)
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
     * Starts the MediaRecorder object and sets the "isRecording" value to true and the
     * "recordingPaused" value to false
     * Starts a new Timer starting at zero which stops when the recording gets paused
     *
     * @throws IOException
     */
    fun startRecording() {
        if (!_isRecording.value!!) {
            _timestamp.value = getCurrentTimestamp("yyyy-MM-dd_HH-mm-ss")
            mediaRecorder = getConfiguredMediaRecorder().apply {
                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e("RecorderFragment", "prepare() failed")
                }
                start()
                _recordingPaused.value = false
                _isRecording.value = true

                timer = object: CountDownTimer(Long.MAX_VALUE, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (_recordingPaused.value!!) {
                            cancel()
                        } else {
                            _currentTimeCode.value = (Long.MAX_VALUE - millisUntilFinished) / 1000
                            timePassed = millisUntilFinished
                        }
                    }
                    override fun onFinish() {}
                }.start()
            }
        }
    }

    /**
     * If "isRecording" is true, the MediaRecorder stops the current recording
     * and releases itself, else nothing will happen
     */
    fun stopRecording() {
        if (_isRecording.value!!) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            timer.cancel()
            _isRecording.value = false
            mediaRecorder = null
        }
    }

    /**
     * If "isRecording" is true, the MediaRecorder pauses the current recording
     * and sets "recordingPaused" to true, else it will call the function resumeRecording()
     */
    fun pauseRecording() {
        if (_isRecording.value!!) {
            if (!_recordingPaused.value!!) {
                mediaRecorder?.pause()
                _recordingPaused.value = true
            } else {
                resumeRecording()
            }
        }
    }

    /**
     * Resumes the paused recording and set "recordingPaused" to false
     * Starts a new timer and starts with the already passed time and stops when
     * the recording gets paused
     */
    private fun resumeRecording() {
        mediaRecorder?.resume()
        _recordingPaused.value = false
        timer =  object: CountDownTimer(timePassed, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (_recordingPaused.value!!) {
                    cancel()
                } else {
                    _currentTimeCode.value = (Long.MAX_VALUE - millisUntilFinished) / 1000
                    timePassed = millisUntilFinished
                }
            }
            override fun onFinish() {}
        }.start()
    }

    /**
     * Plays the saved file with a new created MediaPlayer object when "isRecording" is false
     * and it's not playing already a file. If every state is like it should
     * it prepares the object and starts the player.
     * When the audio file is finished it sets the "isPlayingRecording" back to false
     */
    fun playRecording() {
        if (!_isRecording.value!!) {
            if (!_isPlayingRecording.value!!) {
                if (!outputFile.isEmpty()) {
                    val mediaPlayer = MediaPlayer().apply {
                        setDataSource(outputFile)
                        prepare()
                        start()
                    }
                    _isPlayingRecording.value = true
                    mediaPlayer.setOnCompletionListener {
                        _isPlayingRecording.value = false
                    }
                }
            }
        }
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