package berlin.eloquent.eloquentandroid.main.repository

import androidx.lifecycle.LiveData
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecorderRepository @Inject constructor(val database: RecordingDao) : IRecorderRepository{

    override suspend fun insertRecording(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.insertRecording(recording)
        }
    }

    override suspend fun updateRecording(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.updateRecording(recording)
        }
    }

    override suspend fun deleteRecording(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.deleteRecording(recording)
        }
    }

    override suspend fun getRecording(recordingId: Long): Recording {
        return withContext(Dispatchers.IO) {
            database.getRecording(recordingId)
        }
    }

    override suspend fun getAllRecordings(): LiveData<List<Recording>> {
        return withContext(Dispatchers.IO) {
            database.getAllRecordings()
        }
    }

    override suspend fun getNewestRecording(): Recording? {
        return withContext(Dispatchers.IO) {
            database.getNewestRecording()
        }
    }

}