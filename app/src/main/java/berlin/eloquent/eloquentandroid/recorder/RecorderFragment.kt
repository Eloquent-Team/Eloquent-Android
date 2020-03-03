package berlin.eloquent.eloquentandroid.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
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
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecorderFragment : Fragment() {

    private var outputFile: String? = ""
    private var mediaRecorder: MediaRecorder? = null
    var isRecording: Boolean = false
    private var recordingPaused: Boolean = false
    private var prepared = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = RecorderFragmentBinding.inflate(layoutInflater)

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
            stopRecording(binding)
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
        Log.i("RecorderFragment", "fun startRecording called")
        val timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").withZone(ZoneOffset.UTC).format(Instant.now())

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

    private fun stopRecording(binding: RecorderFragmentBinding) {
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

    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
    }
}