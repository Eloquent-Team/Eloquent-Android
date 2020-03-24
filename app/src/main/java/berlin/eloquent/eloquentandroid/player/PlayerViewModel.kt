package berlin.eloquent.eloquentandroid.player

import android.app.Application
import android.media.MediaPlayer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PlayerViewModel(val database: RecordingDao, application: Application) : AndroidViewModel(application) {

    // Attributes
    private lateinit var mediaPlayer: MediaPlayer

    // Live Data
    private val _playingState = MutableLiveData<PlayingState>()
    val playingState: LiveData<PlayingState> get() = _playingState

    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    private val _timeCode = MutableLiveData<Long>()

    val timeCodeText: LiveData<String> = Transformations.map(_timeCode) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        _playingState.value = PlayingState.STOPPED
    }

    fun setRecording() {
        viewModelScope.launch {
            _recording.value = getNewestRecording()
            _timeCode.value = _recording.value!!.length
        }
    }

    private suspend fun getNewestRecording(): Recording? {
        return withContext(Dispatchers.IO) {
            database.getNewestRecording()
        }
    }

    fun analyzeRecording(newTitle: String, newTags: String) {
        viewModelScope.launch {
            _recording.value!!.title = newTitle
            _recording.value!!.tags = newTags
            update(_recording.value!!)
        }
    }

    private suspend fun update(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.update(recording)
        }
    }

    private fun setupMediaRecorder(fileUrl: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(fileUrl)
    }

    fun controlPlayback() {
        setupMediaRecorder(_recording.value!!.fileUrl)
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