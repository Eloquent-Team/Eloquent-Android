package berlin.eloquent.eloquentandroid.player

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.recorder.RecordingState

class PlayerViewModel : ViewModel() {

    private val _outputFile = MutableLiveData<String>()
    val outputFile: LiveData<String> get() = _outputFile

    private val _isPlayingRecording = MutableLiveData<Boolean>()
    val isPlayingRecording: LiveData<Boolean> get() = _isPlayingRecording


    fun setOutputFile(outputFile: String) {
        _outputFile.value = outputFile
    }

    /**
     * Plays the saved file with a new created MediaPlayer object when "isRecording" is false
     * and it's not playing already a file. If every state is like it should
     * it prepares the object and starts the player.
     * When the audio file is finished it sets the "isPlayingRecording" back to false
     */
    fun playRecording() {
        if (_outputFile.value!!.isNotBlank()) {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(_outputFile.value)
                prepare()
                start()
            }
            _isPlayingRecording.value = true
            mediaPlayer.setOnCompletionListener {
                _isPlayingRecording.value = false
            }
        }
    }

}