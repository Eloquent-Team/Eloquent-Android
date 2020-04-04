package berlin.eloquent.eloquentandroid.main.home

import androidx.lifecycle.ViewModel
import berlin.eloquent.eloquentandroid.database.RecordingDao
import javax.inject.Inject

class HomeViewModel @Inject constructor(val database: RecordingDao) : ViewModel(){

    // Live Data
    val allRecordings = database.getAllRecordings()

}
