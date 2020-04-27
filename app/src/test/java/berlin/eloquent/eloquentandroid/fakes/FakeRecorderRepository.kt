package berlin.eloquent.eloquentandroid.fakes

import androidx.lifecycle.LiveData
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository

class FakeRecorderRepository : IRecorderRepository {

    private val recording = Recording(
        recordingId = 1L,
        title = "TestRecording",
        tags = "test more tests",
        date = "2020-03-24 12:24:34",
        length = 100L,
        fileUrl = "fileUrl"
    )

    override suspend fun getRecording(recordingId: Long): Recording {
        return recording
    }

    override suspend fun getAllRecordings(): LiveData<List<Recording>> {
        TODO("not implemented")
    }

    override suspend fun getNewestRecording(): Recording? {
        TODO("not implemented")
    }

    override suspend fun insertRecording(recording: Recording) {
        TODO("not implemented")
    }

    override suspend fun updateRecording(recording: Recording) {
        TODO("not implemented")
    }

    override suspend fun deleteRecording(recording: Recording) {
        TODO("not implemented")
    }

}
