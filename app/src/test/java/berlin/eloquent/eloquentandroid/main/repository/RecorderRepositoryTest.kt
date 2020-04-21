package berlin.eloquent.eloquentandroid.main.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.MainCoroutineRule
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.database.RecordingDao
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecorderRepositoryTest {

    private lateinit var repository: RecorderRepository
    private lateinit var dao: RecordingDao

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val recording = Recording(
        title = "TestRecording",
        tags = "test more tests",
        date = "2020-03-24 12:24:34",
        length = 100L,
        fileUrl = "fileUrl"
    )

    @Before
    fun setUp() {
        dao = mockk()
        repository = RecorderRepository(dao)
    }

    @Test
    fun `insert Recording into db`() = runBlocking {
        every { dao.insertRecording(any()) } just runs

        repository.insertRecording(recording)

        verify { dao.insertRecording(recording) }

        confirmVerified(dao)
    }

}