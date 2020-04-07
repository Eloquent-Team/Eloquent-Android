package berlin.eloquent.eloquentandroid.main.repository

import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecorderRepository @Inject constructor(val database: RecordingDao) : IRecorderRepository{

    override suspend fun getRecording(recordingId: Long): Recording {
        return withContext(Dispatchers.IO) {
            database.getRecording(recordingId)
        }
    }

    override suspend fun updateRecording(recording: Recording) {
        withContext(Dispatchers.IO) {
            database.updateRecording(recording)
        }
    }

}