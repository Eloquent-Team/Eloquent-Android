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

    private val _outputFile = MutableLiveData<String>()
    val outputFile: LiveData<String> get() = _outputFile

    private val _isPlayingRecording = MutableLiveData<Boolean>()
    val isPlayingRecording: LiveData<Boolean> get() = _isPlayingRecording

    init {
        _isPlayingRecording.value = false
    }

    /**
     * Sets the outputFile in the viewModel.
     *
     * @param outputFile recording from the recorder screen
     */
    fun setOutputFile(outputFile: String) {
        _outputFile.value = outputFile
    }

    /**
     * Plays the saved file with a new created MediaPlayer object when isPlayingRecording is false.
     * It prepares the object and starts the player.
     * When the audio file is finished it sets the "isPlayingRecording" back to false.
     */
    fun playRecording() {
        if (!_isPlayingRecording.value!!) {
            if (_outputFile.value!!.isNotBlank()) {
                val mediaPlayer = MediaPlayer().apply {
                    setDataSource(_outputFile.value)
                    try {
                        prepare()
                    } catch (e: IOException) {
                        Log.e("PlayerFragment", "prepare() failed")
                    }
                    start()
                }
                _isPlayingRecording.value = true
                mediaPlayer.setOnCompletionListener {
                    _isPlayingRecording.value = false
                }
            }
        }

    }

}