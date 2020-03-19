package berlin.eloquent.eloquentandroid.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.home.models.DataSource
import berlin.eloquent.eloquentandroid.home.models.RecordingRecyclerAdapter

class HomeViewModel : ViewModel() {

    // Live Data
    private val _recordingAdapter = MutableLiveData<RecordingRecyclerAdapter>()
    val recordingAdapter: LiveData<RecordingRecyclerAdapter> get() = _recordingAdapter

    init {
        _recordingAdapter.value = RecordingRecyclerAdapter()
    }

    /**
     * Adds the dummy set to the RecyclerView
     */
    fun addDataSet(){
        _recordingAdapter.value?.submitList(DataSource.createDataSet())
    }

}