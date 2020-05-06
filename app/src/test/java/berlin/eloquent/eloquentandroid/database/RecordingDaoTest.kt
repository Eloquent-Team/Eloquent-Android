package berlin.eloquent.eloquentandroid.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecordingDaoTest : TestDatabase() {

    @Test
    fun `test insert recording and retrieve from db`() {
        // Given
        val dao = eloquentDatabase.recordingDao()

        // When
        dao.insertRecording(recording)

        // Then
        val retrievedRecording = dao.getRecording(1L)
        assertThat<Recording>(retrievedRecording, notNullValue())
        assertThat(retrievedRecording.title, `is`(recording.title))
        assertThat(retrievedRecording.tags, `is`(recording.tags))
        assertThat(retrievedRecording.date, `is`(recording.date))
        assertThat(retrievedRecording.length, `is`(recording.length))
        assertThat(retrievedRecording.fileUrl, `is`(recording.fileUrl))
    }

    @Test
    fun `test retrieving recording which doesn't exist returned value is null`() {
        // Given
        val dao = eloquentDatabase.recordingDao()

        // When
        dao.insertRecording(recording)

        // Then
        val retrievedRecording = dao.getRecording(5L)
        assertThat<Recording>(retrievedRecording, nullValue())
    }

    @Test
    fun `test insert 2 recordings delete one`() {
        // Given
        val secondRecording = Recording(
            title = "newTitle",
            tags = "moreTags",
            fileUrl = "newFileUrl",
            date = "00.00.000",
            length = 200L
        )
        val dao = eloquentDatabase.recordingDao()

        // When
        dao.insertRecording(recording)
        dao.insertRecording(secondRecording)
        var recordings = dao.getAllRecordings().getOrAwaitValue()
        assertThat(recordings.size, `is`(2))
        dao.deleteRecording(recordings[0])

        // Then
        recordings = dao.getAllRecordings().getOrAwaitValue()
        assertThat(recordings.size, `is`(1))
    }

    @Test
    fun `test getNewestRecording`() {
        // Given
        val secondRecording = Recording(
            title = "newTitle",
            tags = "moreTags",
            fileUrl = "newFileUrl",
            date = "00.00.000",
            length = 200L
        )
        val dao = eloquentDatabase.recordingDao()

        // When
        dao.insertRecording(recording)
        dao.insertRecording(secondRecording)
        val retrievedRecording = dao.getNewestRecording()

        // Then
        assertThat<Recording>(retrievedRecording, notNullValue())
        assertThat(retrievedRecording!!.title, `is`(secondRecording.title))
        assertThat(retrievedRecording.tags, `is`(secondRecording.tags))
        assertThat(retrievedRecording.date, `is`(secondRecording.date))
        assertThat(retrievedRecording.length, `is`(secondRecording.length))
        assertThat(retrievedRecording.fileUrl, `is`(secondRecording.fileUrl))
    }

    @Test
    fun `test insert recording and updating it`() {
        // Given
        val dao = eloquentDatabase.recordingDao()
        dao.insertRecording(recording)
        val newRecording = Recording(
            recordingId = 1L,
            title = "newTitle",
            tags = "moreTags",
            fileUrl = "newFileUrl",
            date = "00.00.000",
            length = 200L
        )

        // When
        dao.updateRecording(newRecording)

        // Then
        val retrievedRecording = dao.getRecording(1L)
        assertThat<Recording>(retrievedRecording, notNullValue())
        assertThat(retrievedRecording.title, `is`(newRecording.title))
        assertThat(retrievedRecording.tags, `is`(newRecording.tags))
        assertThat(retrievedRecording.date, `is`(newRecording.date))
        assertThat(retrievedRecording.length, `is`(newRecording.length))
        assertThat(retrievedRecording.fileUrl, `is`(newRecording.fileUrl))
    }

}
