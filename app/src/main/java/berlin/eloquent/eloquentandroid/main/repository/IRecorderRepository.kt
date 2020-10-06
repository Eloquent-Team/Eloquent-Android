package berlin.eloquent.eloquentandroid.main.repository

import androidx.lifecycle.LiveData
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.feedback.retrofit.dataModel
import retrofit2.Response
import retrofit2.http.GET

interface IRecorderRepository {

    suspend fun insertRecording(recording: Recording)

    suspend fun updateRecording(recording: Recording)

    suspend fun deleteRecording(recording: Recording)

    suspend fun getRecording(recordingId: Long): Recording

    suspend fun getAllRecordings(): LiveData<List<Recording>>

    suspend fun getNewestRecording(): Recording?

    @GET("api/v1/analyze/3")
    suspend fun getAnalysis(): Response<dataModel>
}
