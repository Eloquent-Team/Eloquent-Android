package berlin.eloquent.eloquentandroid.recorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var isRecording: Boolean = false


    /**
     * Live Data
     */
    private val _timestamp = MutableLiveData<String>()
    val timeStamp: LiveData<String> get() = _timestamp

    private val _recordingPaused = MutableLiveData<Boolean>()
    val recordingPaused: LiveData<Boolean> get() = _recordingPaused

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
     *
     * @throws IOException
     */
    fun startRecording() {
        _timestamp.value = getCurrentTimestamp("yyyy-MM-dd_HH-mm-ss")
        mediaRecorder = getConfiguredMediaRecorder().apply {
            try {
                prepare()
            } catch (e: IOException) {
                Log.e("RecorderFragment", "prepare() failed")
            }
            start()
            _recordingPaused.value = false
            isRecording = true
        }
    }

    /**
     * If "isRecording" is true, the MediaRecorder stops the current recording
     * and releases itself, else nothing will happen
     */
    fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            isRecording = false
            mediaRecorder = null
        }
    }

    /**
     * If "isRecording" is true, the MediaRecorder pauses the current recording
     * and sets "recordingPaused" to true, else it will call the function resumeRecording()
     */
    fun pauseRecording() {
        if (isRecording) {
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
     */
    private fun resumeRecording() {
        mediaRecorder?.resume()
        _recordingPaused.value = false
    }

    /**
     * Plays the saved file with a new created MediaPlayer object.
     * Then it prepares the object and starts the player.
     */
    fun playRecording() {
        MediaPlayer().apply {
            setDataSource(outputFile)
            prepare()
            start()
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