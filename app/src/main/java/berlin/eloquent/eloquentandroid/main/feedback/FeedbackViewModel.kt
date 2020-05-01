package berlin.eloquent.eloquentandroid.main.feedback

import android.text.format.DateUtils
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(private val repo: IRecorderRepository) : ViewModel() {

    // Live Data
    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    private val _length = MutableLiveData<Long>()
    val length: LiveData<Long> get() = _length

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    val timeCodeText: LiveData<String> = Transformations.map(_length) {
        DateUtils.formatElapsedTime(it)
    }


    fun setRecording(recordingId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _recording.value = repo.getRecording(recordingId)
            _length.value = _recording.value!!.length
            _date.value = _recording.value!!.date.toString()
        }
    }

}