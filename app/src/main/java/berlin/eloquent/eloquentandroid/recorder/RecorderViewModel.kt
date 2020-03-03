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

    private var mediaRecorder: MediaRecorder? = null

    var outputFile = ""

    private val _timestamp = MutableLiveData<String>()
    val timeStamp: LiveData<String> get() = _timestamp

    private var isRecording: Boolean = false

    private val _recordingPaused = MutableLiveData<Boolean>()
    val recordingPaused: LiveData<Boolean> get() = _recordingPaused

    init {
        _timestamp.value = ""
        _recordingPaused.value = false
    }

    fun startRecording() {
        Log.i("RecorderFragment", "fun startRecording called")
        _timestamp.value = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").withZone(ZoneOffset.UTC).format(
            Instant.now())
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(outputFile)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("RecorderFragment", "prepare() failed")
            }
            start()
            _recordingPaused.value = false
            isRecording = true
            //Toast.makeText(activity, "Recording started!", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            isRecording = false
            mediaRecorder = null
            //Toast.makeText(activity, "Recording stopped!", Toast.LENGTH_SHORT).show()
        }
    }

    fun pauseRecording() {
        if (isRecording) {
            if (!_recordingPaused.value!!) {
                //Toast.makeText(activity, "Recording paused!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                _recordingPaused.value = true
            } else {
                resumeRecording()
            }
        }
    }

    fun resumeRecording() {
        //Toast.makeText(activity,"Recording resumed!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        _recordingPaused.value = false
    }

    fun playRecording() {
        var mediaPlayer = MediaPlayer()
        //Toast.makeText(activity,"Recording is playing!", Toast.LENGTH_SHORT).show()
        mediaPlayer.setDataSource(outputFile)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override fun onCleared() {
        super.onCleared()
        mediaRecorder?.release()
        mediaRecorder = null
    }

}