package berlin.eloquent.eloquentandroid.player

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import berlin.eloquent.eloquentandroid.recorder.RecordingState
import java.io.IOException

class PlayerViewModel : ViewModel() {

    // Attributes
    private lateinit var mediaPlayer: MediaPlayer

    // Live Data
    private val _outputFile = MutableLiveData<String>()
    val outputFile: LiveData<String> get() = _outputFile

    private val _playingState = MutableLiveData<PlayingState>()
    val playingState: LiveData<PlayingState> get() = _playingState

    init {
        _outputFile.value = ""
        _playingState.value = PlayingState.STOPPED
    }

    fun setupMediaPlayer(outputFile: String) {
        _outputFile.value = outputFile
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(_outputFile.value)
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
        if (_outputFile.value!!.isNotBlank()) {
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