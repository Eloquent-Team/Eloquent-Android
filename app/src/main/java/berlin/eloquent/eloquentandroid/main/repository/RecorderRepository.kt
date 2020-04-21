package berlin.eloquent.eloquentandroid.main.repository

import androidx.lifecycle.LiveData
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecorderRepository @Inject constructor(
    val database: RecordingDao,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecorderRepository{

    override suspend fun insertRecording(recording: Recording) {
        withContext(ioDispatcher) {
            database.insertRecording(recording)
        }
    }

    override suspend fun updateRecording(recording: Recording) {
        withContext(ioDispatcher) {
            database.updateRecording(recording)
        }
    }

    override suspend fun deleteRecording(recording: Recording) {
        withContext(ioDispatcher) {
            database.deleteRecording(recording)
        }
    }

    override suspend fun getRecording(recordingId: Long): Recording {
        return withContext(ioDispatcher) {
           return@withContext database.getRecording(recordingId)
        }
    }

    override suspend fun getAllRecordings(): LiveData<List<Recording>> {
        return withContext(ioDispatcher) {
           return@withContext database.getAllRecordings()
        }
    }

    override suspend fun getNewestRecording(): Recording? {
        return withContext(ioDispatcher) {
            return@withContext database.getNewestRecording()
        }
    }

}