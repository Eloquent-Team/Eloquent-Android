package berlin.eloquent.eloquentandroid.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.MainCoroutineRule
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.fakes.FakeRecorderRepository
import berlin.eloquent.eloquentandroid.main.player.PlayerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*

import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayerViewModelTest {

    private lateinit var playerViewModel: PlayerViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        // Given
        playerViewModel = PlayerViewModel(FakeRecorderRepository())
    }

    @Test
    fun `when setRecording() is called, timeCodeText should be in correct pattern`() {
        assertEquals(2+2, 4)
    }

    @Test
    fun `when setRecording() is called, the recording with the given recordingId should be returned`() = runBlockingTest {
        // When
        playerViewModel.setRecording(1L)

        // Then
        assertThat(playerViewModel.recording.value!!.recordingId, `is`(1L))
    }

    @Test
    fun `when setRecording() is called, the timecode value should equals to the recording timecode`() = runBlockingTest {
        // When
        playerViewModel.setRecording(1L)

        // Then
        assertThat(playerViewModel.timeCodeText.value!!, `is`("01:40"))
    }

}
