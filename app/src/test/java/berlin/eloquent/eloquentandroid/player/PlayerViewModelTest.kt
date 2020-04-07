package berlin.eloquent.eloquentandroid.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.fakes.FakeRecorderRepository
import berlin.eloquent.eloquentandroid.main.player.PlayerViewModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.text.MatchesPattern

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
        playerViewModel = PlayerViewModel(FakeRecorderRepository())
    }

    @Test
    fun `when setRecording() is called, timeCodeText should be in correct pattern`() {
        // Given
        val recording = Recording()
        recording.length = 376L

        // When
        playerViewModel.setRecording(1L)

        // Then
        val timCodeText = playerViewModel.timeCodeText.getOrAwaitValue()
        val pattern = MatchesPattern.matchesPattern("\\d{2}:\\d{2}") // Buggy, the timeCodeText can be longer than that pattern
        assertThat(timCodeText, pattern)
    }

}