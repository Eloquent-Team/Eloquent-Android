package berlin.eloquent.eloquentandroid.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding
import java.io.IOException

class RecorderFragment : Fragment() {

    private var outputFile: String? = null
    private var mediaRecorder: MediaRecorder? = null
    var isRecording: Boolean = false
    private var recordingPaused: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        outputFile = activity?.getExternalFilesDir(null)?.absolutePath + "/recording.mp3"

        mediaRecorder = MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(outputFile)

        /*imgbutton_startRecording.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                startRecording()
            }
        }*/

        binding.startRecording.setOnClickListener {
            startRecording()
        }

        binding.stopRecording.setOnClickListener{
            stopRecording()
        }

        binding.pauseRecording.setOnClickListener {
            pauseRecording(binding)
        }

        binding.playRecording.setOnClickListener{
            playRecording()
        }

        return binding.root
    }

    fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            isRecording = true
            Toast.makeText(activity, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            isRecording = false
            Toast.makeText(activity, "Recording stopped!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pauseRecording(binding: RecorderFragmentBinding) {
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

    private fun resumeRecording(binding: RecorderFragmentBinding) {
        Toast.makeText(activity,"Recording resumed!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        binding.pauseRecording.setImageResource(R.drawable.ic_pause)
        recordingPaused = false
    }

    private fun playRecording() {
        var mediaPlayer = MediaPlayer()
        Toast.makeText(activity,"Recording is playing!", Toast.LENGTH_SHORT).show()
        mediaPlayer.setDataSource(outputFile)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }
}