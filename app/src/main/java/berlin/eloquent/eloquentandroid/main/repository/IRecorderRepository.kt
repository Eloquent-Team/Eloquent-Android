package berlin.eloquent.eloquentandroid.main.repository

import berlin.eloquent.eloquentandroid.database.Recording

interface IRecorderRepository {
    suspend fun getRecording(recordingId: Long): Recording

    suspend fun updateRecording(recording: Recording)
}