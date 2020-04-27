package berlin.eloquent.eloquentandroid.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.MainCoroutineRule
import berlin.eloquent.eloquentandroid.fakes.FakeRecorderRepository
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import berlin.eloquent.eloquentandroid.main.player.PlayerViewModel
import berlin.eloquent.eloquentandroid.main.player.PlayingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    fun `when setRecording() is called, the recording with the given recordingId should be returned`() = runBlockingTest {
        // When
        playerViewModel.setRecording(1L)

        // Then
        val recording = playerViewModel.recording.getOrAwaitValue()
        assertThat(recording.recordingId, `is`(1L))
    }

    @Test
    fun `when setRecording() is called, the timecode value should equals to the recording timecode`() = runBlockingTest {
        // When
        playerViewModel.setRecording(1L)

        // Then
        assertThat(playerViewModel.timecode.getOrAwaitValue(), `is`(100L))
    }

    @Test
    fun `when analyzeRecording called newTitle and newTags are set`() = runBlockingTest {
        // Given
        playerViewModel.setRecording(1L)

        // When
        playerViewModel.analyzeRecording("newTitle", "newTags, moreNewTags")

        // Then
        val recording = playerViewModel.recording.getOrAwaitValue()
        assertThat(recording.title, `is`("newTitle"))
        assertThat(recording.tags, `is`("newTags, moreNewTags"))
    }

    @Test
    fun `when analyzeRecording called with empty arguments props stay same`() = runBlockingTest {
        // Given
        playerViewModel.setRecording(1L)

        // When
        playerViewModel.analyzeRecording("", "")

        // Then
        val recording = playerViewModel.recording.getOrAwaitValue()
        assertThat(recording.title, `is`(recording.title))
        assertThat(recording.tags, `is`(recording.tags))
    }

    @Test
    fun `when startPlayback is called, state becomes playing`() {
        // Given
        playerViewModel.setRecording(1L)

        // When
        val startPlayback = playerViewModel.javaClass.getDeclaredMethod("startPlayback")
        startPlayback.isAccessible = true
        startPlayback.invoke(playerViewModel)

        // Then
        val playingState = playerViewModel.playingState.getOrAwaitValue()
        assertThat(playingState, `is`(PlayingState.PLAYING))
    }

    @Test
    fun `when pausePlayback is called, state becomes PAUSE`() {
        // Given
        playerViewModel.setRecording(1L)
        playerViewModel.setPlayingState(PlayingState.PLAYING)

        // When
        playerViewModel.controlPlayback()

        // Then
        val playingState = playerViewModel.playingState.getOrAwaitValue()
        assertThat(playingState, `is`(PlayingState.PAUSED))
    }

}
