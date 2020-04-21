package berlin.eloquent.eloquentandroid.main.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(private val repo: IRecorderRepository) : ViewModel() {

    // Attributes
    // create own job and scope, because viewModelScope has a bug with DI, it won't get called again
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    // Live Data
    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording


    fun setRecording(recordingId: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            _recording.value = repo.getRecording(recordingId)
        }
    }

}