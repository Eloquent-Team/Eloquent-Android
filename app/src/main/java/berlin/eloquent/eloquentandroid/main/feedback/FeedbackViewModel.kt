package berlin.eloquent.eloquentandroid.main.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(private val repo: IRecorderRepository) : ViewModel() {

    // Live Data
    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording


    fun setRecording(recordingId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _recording.value = repo.getRecording(recordingId)
        }
    }

}