package berlin.eloquent.eloquentandroid.recorder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.database.RecordingDao
import berlin.eloquent.eloquentandroid.fakes.FakeRecorderRepository
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import berlin.eloquent.eloquentandroid.main.recorder.RecorderViewModel
import berlin.eloquent.eloquentandroid.main.recorder.RecordingState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class RecorderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var dao: RecordingDao

    private lateinit var recorderViewModel: RecorderViewModel

    @Before
    fun setupViewModel() {
        // Given
        recorderViewModel = RecorderViewModel(FakeRecorderRepository(), ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `when controlStartStopRecording() is called and the RecordingState is NOT_STARTED, the RecordingState should be RECORDING`() {
        // When
        recorderViewModel.controlStartStopRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.RECORDING))
    }

    @Test
    fun `when controlStartStopRecording() is called and the RecordingState is not NOT_STARTED, the RecordingState should be STOPPED`() {
        // Given
        recorderViewModel.controlStartStopRecording()

        // When
        recorderViewModel.controlStartStopRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.STOPPED))
    }

    @Test
    fun `when controlPauseResumeRecording() is called and the RecordingState is RECORDING, the RecordingState should be PAUSED`() {
        // Given
        recorderViewModel.controlStartStopRecording()

        // When
        recorderViewModel.controlPauseResumeRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.PAUSED))
    }

    @Test
    fun `when controlPauseResumeRecording() is called and the RecordingState is PAUSED, the RecordingState should be RECORDING`() {
        // Given
        recorderViewModel.controlStartStopRecording()
        recorderViewModel.controlPauseResumeRecording()

        // When
        recorderViewModel.controlPauseResumeRecording()

        // Then
        val recordingState = recorderViewModel.recordingState.getOrAwaitValue()
        assertThat(recordingState, `is`(RecordingState.RECORDING))
    }

    @Test
    fun `when getConfiguredMediaRecorder() is called, the outputFile should be set`() {
        // When
        recorderViewModel.controlStartStopRecording()

        // Then
        val outputFile = recorderViewModel.outputFile.getOrAwaitValue()
        assertThat(outputFile, not(""))
    }

}
