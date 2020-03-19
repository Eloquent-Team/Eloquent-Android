package berlin.eloquent.eloquentandroid.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import berlin.eloquent.eloquentandroid.models.Recording
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Assert.assertThat
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayerViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var playerViewModel: PlayerViewModel

    @Before
    fun setupViewModel() {
        playerViewModel = PlayerViewModel()
    }

    @Test
    fun `when setOutputFile() is called, outPutFile must not be empty`() {
        // When
        val recording = Recording()
        recording.fileUrl = "recording_2020-03-20"
        playerViewModel.setRecording(recording)

        val outputFile = playerViewModel.recording.getOrAwaitValue()
        assertThat(outputFile.fileUrl, not(""))
    }

    @Test
    fun `when startPlayback() is called, the PlayingState should be PLAYING`() {
        // When
        playerViewModel.controlPlayback()

        // Then
        val playingState = playerViewModel.playingState.getOrAwaitValue()
        assertThat(playingState, `is`(PlayingState.PLAYING))
    }

    @Test
    fun `when pausePlayback() is called, the PlayingState should be PAUSED`() {

    }

}