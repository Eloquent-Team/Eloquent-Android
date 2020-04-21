package berlin.eloquent.eloquentandroid.main.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.MainCoroutineRule
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class RecorderRepositoryTest {

    private lateinit var repository: RecorderRepository
    private lateinit var dao: RecordingDao

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val newRecording = Recording(
        title = "TestRecording",
        tags = "test more tests",
        date = "2020-03-24 12:24:34",
        length = 100L,
        fileUrl = "fileUrl"
    )

    private val existingRecording = Recording(
        recordingId = 2L,
        title = "TestRecording",
        tags = "test more tests",
        date = "2020-03-24 12:24:34",
        length = 100L,
        fileUrl = "fileUrl"
    )

    @Before
    fun setUp() {
        dao = mockk()
        repository = RecorderRepository(dao, TestCoroutineDispatcher())
    }

    @Test
    fun `insert Recording into db`() = runBlockingTest {
        every { dao.insertRecording(any()) } just runs

        repository.insertRecording(newRecording)

        verify { dao.insertRecording(newRecording) }

        confirmVerified(dao)
    }

    @Test
    fun `update Recording in db`() = runBlockingTest {
        every { dao.updateRecording(any()) } just runs

        repository.updateRecording(newRecording)

        verify { dao.updateRecording(newRecording) }

        confirmVerified(dao)
    }

    @Test
    fun `delete Recording`() = runBlockingTest {
        every { dao.deleteRecording(any()) } just runs

        repository.deleteRecording(newRecording)

        verify { dao.deleteRecording(newRecording) }

        confirmVerified(dao)
    }

    @Test
    fun `get Recording by id from db`() = runBlockingTest {
        every { dao.getRecording(any()) } returns Recording()

        repository.getRecording(existingRecording.recordingId)

        verify { dao.getRecording(existingRecording.recordingId) }

        confirmVerified(dao)
    }

    @Test
    fun `get all Recordings from db`() = runBlockingTest {
        every { dao.getAllRecordings() } returns MutableLiveData<List<Recording>>() as LiveData<List<Recording>>

        repository.getAllRecordings()

        verify { dao.getAllRecordings() }

        confirmVerified(dao)
    }

    @Test
    fun `get newest Recording from db`() = runBlockingTest {
        every { dao.getNewestRecording() } returns Recording()

        repository.getNewestRecording()

        verify { dao.getNewestRecording() }

        confirmVerified(dao)
    }

}
