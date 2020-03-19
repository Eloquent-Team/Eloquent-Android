package berlin.eloquent.eloquentandroid.feedback

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.models.Recording

class FeedbackViewModel : ViewModel() {

    // Live Data
    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    fun setRecording(recording: Recording) {
        _recording.value = recording
        Log.i("FeedbackViewModel", "${_recording.value!!}")
    }

}