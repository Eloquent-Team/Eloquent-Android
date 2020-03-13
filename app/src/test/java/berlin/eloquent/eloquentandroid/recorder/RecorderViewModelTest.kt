package berlin.eloquent.eloquentandroid.recorder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecorderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var recorderViewModel: RecorderViewModel

    @Before
    fun setupViewModel() {
        recorderViewModel = RecorderViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `when startRecording() is called, the RecordingState should be RECORDING`() {
        // When
        recorderViewModel.startRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.RECORDING))
    }

    @Test
    fun `when stopRecording() is called, the RecordingState should be STOPPED`() {
        // Given
        recorderViewModel.startRecording()

        // When
        recorderViewModel.stopRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.STOPPED))
    }

    @Test
    fun `when pauseRecording() is called first, the RecordingState should be PAUSED`() {
        // Given
        recorderViewModel.startRecording()

        // When
        recorderViewModel.pauseRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.PAUSED))
    }

    @Test
    fun `when pauseRecording() is called the second time, the RecordingState should be RECORDING`() {
        // Given
        recorderViewModel.startRecording()
        recorderViewModel.pauseRecording()

        // When
        recorderViewModel.pauseRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.RECORDING))
    }

    @Test
    fun `when playRecording() is called, isPlayingRecording schould be true`() {
        // When
        recorderViewModel.playRecording()

        // Then
        val isPlayingRecording = recorderViewModel.isPlayingRecording.getOrAwaitValue()
        assertThat(isPlayingRecording, `is`(false))
    }

    @Test
    fun `when getConfiguredMediaRecorder() is called, the outputFile should be set`() {
        // When
        recorderViewModel.startRecording()

        // Then
        val outputFile = recorderViewModel.outputFile.getOrAwaitValue()
        assertThat(outputFile, not(""))
    }

}
