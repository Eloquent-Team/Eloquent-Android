package berlin.eloquent.eloquentandroid.player

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.models.Recording
import java.io.IOException

class PlayerViewModel : ViewModel() {

    // Attributes
    private lateinit var mediaPlayer: MediaPlayer

    // Live Data
    private val _playingState = MutableLiveData<PlayingState>()
    val playingState: LiveData<PlayingState> get() = _playingState

    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    init {
        _playingState.value = PlayingState.STOPPED
    }

    fun setRecording(recording: Recording) {
        _recording.value = recording
        setupMediaRecorder(_recording.value!!.fileUrl)
    }

    fun setupMediaRecorder(fileUrl: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(fileUrl)
    }

    fun analyzeRecording() {
        Log.i("PlayerViewModel", "Analyzing...")
    }

    fun controlPlayback() {
        when (_playingState.value) {
            PlayingState.STOPPED -> startPlayback(mediaPlayer)
            PlayingState.PLAYING -> pausePlayback(mediaPlayer)
            PlayingState.PAUSED -> resumePlayback(mediaPlayer)
        }
    }

    private fun startPlayback(mediaPlayer: MediaPlayer) {
        if (_recording.value!!.fileUrl.isNotBlank()) {
            mediaPlayer.apply {
                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e("PlayerFragment", "prepare() failed")
                }
                start()
            }
            _playingState.value = PlayingState.PLAYING
            stopPlayback(mediaPlayer)
        }
    }

    private fun pausePlayback(mediaPlayer: MediaPlayer) {
            mediaPlayer.pause()
            _playingState.value = PlayingState.PAUSED
    }

    private fun resumePlayback(mediaPlayer: MediaPlayer) {
            mediaPlayer.start()
            _playingState.value = PlayingState.PLAYING
    }

    private fun stopPlayback(mediaPlayer: MediaPlayer) {
        mediaPlayer.setOnCompletionListener {
            it.stop()
            _playingState.value = PlayingState.STOPPED
        }
    }

}