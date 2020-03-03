package berlin.eloquent.eloquentandroid.recorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecorderViewModel: ViewModel() {

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile = ""
    private var isRecording: Boolean = false
    private var recordingPaused: Boolean = false

    fun startRecording() {
        Log.i("RecorderFragment", "fun startRecording called")
        val timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").withZone(ZoneOffset.UTC).format(
            Instant.now())

        outputFile = activity?.getExternalFilesDir(null)?.absolutePath + "/recording_$timestamp"
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
            recordingPaused = false
            isRecording = true
            Toast.makeText(activity, "Recording started!", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecording(binding: RecorderFragmentBinding) {
        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            binding.pauseRecording.setImageResource(R.drawable.ic_pause)
            isRecording = false
            mediaRecorder = null
            Toast.makeText(activity, "Recording stopped!", Toast.LENGTH_SHORT).show()
        }
    }

    fun pauseRecording(binding: RecorderFragmentBinding) {
        if (isRecording) {
            if (!recordingPaused) {
                Toast.makeText(activity, "Recording paused!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                recordingPaused = true
                binding.pauseRecording.setImageResource(R.drawable.ic_refresh)
            } else {
                resumeRecording(binding)
            }
        }
    }

    fun resumeRecording(binding: RecorderFragmentBinding) {
        Toast.makeText(activity,"Recording resumed!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        binding.pauseRecording.setImageResource(R.drawable.ic_pause)
        recordingPaused = false
    }

    fun playRecording() {
        var mediaPlayer = MediaPlayer()
        Toast.makeText(activity,"Recording is playing!", Toast.LENGTH_SHORT).show()
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