package berlin.eloquent.eloquentandroid.main.feedback

import android.text.format.DateUtils
import android.widget.Toast
import androidx.lifecycle.*
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.feedback.retrofit.AudioService
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(private val repo: IRecorderRepository) : ViewModel() {

    // Live Data
    private val _recording = MutableLiveData<Recording>()
    val recording: LiveData<Recording> get() = _recording

    private val _analysis_value = MutableLiveData<String>()
    val analysis_value: LiveData<String> get() = _analysis_value

    private val _length = MutableLiveData<Long>()

    val timeCodeText: LiveData<String> = Transformations.map(_length) {
        DateUtils.formatElapsedTime(it)
    }


    fun getData(){
        viewModelScope.launch(Dispatchers.Main){
            try {
                val response = repo.getAnalysis()
                // Check if response was successful
                if (response.isSuccessful && response.body() != null) {
                    // Retrieve data.
                    val data = response.body()!!
                    _analysis_value.value = data.wordsPerSecond

                } else {
                    // Show API error.
                    // This is when the server responded with an error.
                    print("Error")
                }
            } catch (e: Exception) {
                // Show API error. This is the error raised by the client.
                // The API probably wasn't called in this case, so better check before assuming.
                print("Error")
            }
        }

    }

    fun setRecording(recordingId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _recording.value = repo.getRecording(recordingId)
            _length.value = _recording.value!!.length
        }
    }

}