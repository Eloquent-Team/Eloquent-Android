package berlin.eloquent.eloquentandroid.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import berlin.eloquent.eloquentandroid.home.models.DataSource
import berlin.eloquent.eloquentandroid.home.models.RecordingRecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(val database: RecordingDao, application: Application) : AndroidViewModel(application) {

    // Live Data
    private val _recordingAdapter = MutableLiveData<RecordingRecyclerAdapter>()
    val recordingAdapter: LiveData<RecordingRecyclerAdapter> get() = _recordingAdapter

    private lateinit var allRecordings: LiveData<List<Recording>>

    init {
        _recordingAdapter.value = RecordingRecyclerAdapter()
    }

    fun getAllRecordings() {
        viewModelScope.launch {
            allRecordings = getAllRecordingsFromDatabase()
            addDataSet()
        }
    }

    private suspend fun getAllRecordingsFromDatabase(): LiveData<List<Recording>> {
        return withContext(Dispatchers.IO) {
            database.getAllRecordings()
        }
    }

    fun addDataSet() {
        viewModelScope.launch {
            _recordingAdapter.value?.submitList(getAllRecordingsFromDatabase().value!!)
        }
    }

}