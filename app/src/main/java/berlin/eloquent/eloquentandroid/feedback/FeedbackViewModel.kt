package berlin.eloquent.eloquentandroid.feedback

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(val database: RecordingDao) : ViewModel() {

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