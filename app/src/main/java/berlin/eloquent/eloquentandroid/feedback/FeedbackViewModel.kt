package berlin.eloquent.eloquentandroid.feedback

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedbackViewModel(val database: RecordingDao, application: Application) : AndroidViewModel(application) {

    // Live Data
    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    fun setRecording() {
        viewModelScope.launch {
            _recording.value = getNewestRecording()
            Log.i("FeedbackViewModel", _recording.value!!.toString())
        }
    }

    private suspend fun getNewestRecording(): Recording? {
        return withContext(Dispatchers.IO) {
            database.getNewestRecording()
        }
    }

}