package berlin.eloquent.eloquentandroid.main.player

import android.media.MediaPlayer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import berlin.eloquent.eloquentandroid.main.repository.RecorderRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class PlayerViewModel @Inject constructor(val repo: IRecorderRepository) : ViewModel() {

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
            _recording.value = repo.getRecording(recordingId)
            _timeCode.value = _recording.value!!.length
            setupMediaRecorder(_recording.value!!.fileUrl)
        }
    }


    fun analyzeRecording(newTitle: String, newTags: String) {
        coroutineScope.launch {
            if (newTitle != "") {
                _recording.value!!.title = newTitle
            }
            _recording.value!!.tags = newTags
            repo.updateRecording(_recording.value!!)
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
