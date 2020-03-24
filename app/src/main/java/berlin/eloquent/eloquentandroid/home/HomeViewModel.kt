package berlin.eloquent.eloquentandroid.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import berlin.eloquent.eloquentandroid.database.RecordingDao

class HomeViewModel(val database: RecordingDao, application: Application) : AndroidViewModel(application) {

    // Live Data
    val allRecordings = database.getAllRecordings()

}