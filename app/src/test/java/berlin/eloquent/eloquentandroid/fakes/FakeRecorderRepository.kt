package berlin.eloquent.eloquentandroid.fakes

import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository

class FakeRecorderRepository : IRecorderRepository {

    private val recording = Recording(
        recordingId = 1L,
        title = "TestRecording",
        tags = "test more tests",
        date = "2020-03-24 12:24:34",
        length = 100L,
        fileUrl = "com.example..."
    )

    override suspend fun getRecording(recordingId: Long): Recording {
        return recording
    }

    override suspend fun updateRecording(recording: Recording) {

    }


}