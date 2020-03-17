package berlin.eloquent.eloquentandroid.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.home.models.DataSource
import berlin.eloquent.eloquentandroid.home.models.RecordingRecyclerAdapter

class HomeViewModel : ViewModel() {

    private val _blogAdapter = MutableLiveData<RecordingRecyclerAdapter>()
    val blogAdapter: LiveData<RecordingRecyclerAdapter> get() = _blogAdapter

    init {
        _blogAdapter.value = RecordingRecyclerAdapter()
    }

    fun addDataSet(){
        _blogAdapter.value?.submitList(DataSource.createDataSet())
    }

}