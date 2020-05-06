package berlin.eloquent.eloquentandroid.main.repository

import androidx.lifecycle.LiveData
import berlin.eloquent.eloquentandroid.database.Recording

interface IRecorderRepository {

    suspend fun insertRecording(recording: Recording)

    suspend fun updateRecording(recording: Recording)

    suspend fun deleteRecording(recording: Recording)

    suspend fun getRecording(recordingId: Long): Recording

    suspend fun getAllRecordings(): LiveData<List<Recording>>

    suspend fun getNewestRecording(): Recording?

}
