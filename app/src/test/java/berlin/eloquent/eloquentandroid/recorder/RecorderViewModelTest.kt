package berlin.eloquent.eloquentandroid.recorder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecorderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Given
    private lateinit var recorderViewModel: RecorderViewModel

    @Before
    fun setupViewModel() {
        recorderViewModel = RecorderViewModel()
    }

    /**
     * When 'startRecording()' is called, 'isRecording' should be true
     */
    @Test
    fun startRecording_isRecording_true() {
        // When
        recorderViewModel.startRecording()

        // Then
        val isRecording = recorderViewModel.isRecording.getOrAwaitValue()
        assertThat(isRecording, `is`(true))
    }

    /**
     * When 'stopRecording()' is called, 'isRecording' should be false
     */
    @Test
    fun stopRecording_isRecording_false() {
        // When
        recorderViewModel.startRecording()
        recorderViewModel.stopRecording()

        // Then
        val isRecording = recorderViewModel.isRecording.getOrAwaitValue()
        assertThat(isRecording, `is`(false))
    }

    /**
     * When 'pauseRecording()' is called, 'isPaused' should be true
     */
    @Test
    fun pauseRecording_isPaused_true() {
        // When
        recorderViewModel.startRecording()
        recorderViewModel.pauseRecording()

        // Then
        val recordingPaused = recorderViewModel.isPaused.getOrAwaitValue()
        assertThat(recordingPaused, `is`(true))
    }

    /**
     * When 'resumeRecording()' is called, 'isPaused' should be false
     */
    @Test
    fun resumeRecording_isPaused_false() {
        // When
        recorderViewModel.startRecording()
        recorderViewModel.pauseRecording()
        recorderViewModel.pauseRecording()

        // Then
        val recordingPaused = recorderViewModel.isPaused.getOrAwaitValue()
        assertThat(recordingPaused, `is`(false))
    }
}
