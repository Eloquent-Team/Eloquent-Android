package berlin.eloquent.eloquentandroid.main.player

import android.media.MediaPlayer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(private val repo: IRecorderRepository) : ViewModel() {

    // Attributes
    private lateinit var mediaPlayer: MediaPlayer

    // Live Data
    private val _playingState = MutableLiveData<PlayingState>()
    val playingState: LiveData<PlayingState> get() = _playingState

    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    private val _timeCode = MutableLiveData<Long>()
    val timeCode: LiveData<Long> get() = _timeCode

    val timeCodeText: LiveData<String> = Transformations.map(_timeCode) {
        DateUtils.formatElapsedTime(it)
    }


    init {
        _playingState.value = PlayingState.STOPPED
        _recording.value = Recording()
    }

    fun setRecording(recordingId: Long) {
        viewModelScope.launch {
            _recording.value = repo.getRecording(recordingId)
            _timeCode.value = _recording.value!!.length
            setupMediaRecorder(_recording.value!!.fileUrl)
        }
    }

    fun analyzeRecording(newTitle: String, newTags: String) {
        viewModelScope.launch {
            if (newTitle != "") {
                _recording.value!!.title = newTitle
            }
            if(newTags != "") {
                _recording.value!!.tags = newTags
            }
            repo.updateRecording(_recording.value!!)
        }
    }

    private fun setupMediaRecorder(fileUrl: String) {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(fileUrl)
        } catch (iae: IllegalArgumentException) {
            Log.e("PlayerViewModel", "URL was not appropriate")
        }
    }

    fun controlPlayback() {
        when (_playingState.value) {
            PlayingState.STOPPED -> startPlayback()
            PlayingState.PLAYING -> pausePlayback()
            PlayingState.PAUSED -> resumePlayback()
        }
    }

    private fun startPlayback() {
        if (_recording.value!!.fileUrl.isNotBlank()) {
            mediaPlayer.apply {
                try {
                    prepare()
                    start()
                } catch (e: Exception) {
                    Log.e("PlayerViewModel", "Error with preparing or starting MediaPlayer")
                }
            }
            stopPlayback()
            _playingState.value = PlayingState.PLAYING
        }
    }

    private fun pausePlayback() {
        mediaPlayer.pause()
        _playingState.value = PlayingState.PAUSED
    }

    private fun resumePlayback() {
        mediaPlayer.start()
        _playingState.value = PlayingState.PLAYING
    }

    private fun stopPlayback() {
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

    fun setPlayingState(state: PlayingState) {
        _playingState.value = state
    }

}
