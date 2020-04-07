package berlin.eloquent.eloquentandroid.main.player

import android.media.MediaPlayer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

class PlayerViewModel @Inject constructor(val database: RecordingDao) : ViewModel() {

    // Attributes
    // create own job and scope, because viewModelScope has a bug with DI, it won't get called again
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var mediaPlayer: MediaPlayer

    // Live Data
    private val _playingState = MutableLiveData<PlayingState>()
    val playingState: LiveData<PlayingState> get() = _playingState

    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    private val _timeCode = MutableLiveData<Long>()

    val timeCodeText: LiveData<String> = Transformations.map(_timeCode) {
        DateUtils.formatElapsedTime(it)
    }


    init {
        _playingState.value = PlayingState.STOPPED
        _recording.value = Recording()
    }

    fun setRecording(recordingId: Long) {
        coroutineScope.launch {
            _recording.value = getRecording(recordingId)
            _timeCode.value = _recording.value!!.length
            setupMediaRecorder(_recording.value!!.fileUrl)
        }
    }

    private suspend fun getRecording(recordingId: Long): Recording {
        return withContext(Dispatchers.IO) {
            database.getRecording(recordingId)
        }
    }

    fun analyzeRecording(newTitle: String, newTags: String) {
        coroutineScope.launch {
            if (newTitle != "") {
                _recording.value!!.title = newTitle
            }
            _recording.value!!.tags = newTags
            update(_recording.value!!)
        }
    }

    private suspend fun update(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.updateRecording(recording)
        }
    }

    private fun setupMediaRecorder(fileUrl: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(fileUrl)
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
                prepare()
                start()
            }
            stopPlayback(mediaPlayer)
            _playingState.value = PlayingState.PLAYING
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

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
        _playingState.value = PlayingState.STOPPED
    }

}
