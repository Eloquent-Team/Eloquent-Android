package berlin.eloquent.eloquentandroid.recorder

import android.media.MediaRecorder
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecorderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun startRecording_isRecording_true() {
        // Given
        val recorderViewModel = RecorderViewModel()

        // When
        recorderViewModel.startRecording()

        // Then
        val isRecording = recorderViewModel.isRecording.getOrAwaitValue()
        //assertThat(value, (not(nullValue())))
        assertThat(isRecording, `is`(true))
    }

    @Test
    fun stopRecording_isRecording_false() {
        // Given
        val recorderViewModel = RecorderViewModel()

        // When
        recorderViewModel.startRecording()
        recorderViewModel.stopRecording()

        // Then
        val isRecording = recorderViewModel.isRecording.getOrAwaitValue()
        assertThat(isRecording, `is`(false))
    }

    @Test
    fun pauseRecording_recordingPaused_true() {
        // Given
        val recorderViewModel = RecorderViewModel()

        // When
        recorderViewModel.startRecording()
        recorderViewModel.pauseRecording()

        // Then
        val recordingPaused = recorderViewModel.recordingPaused.getOrAwaitValue()
        assertThat(recordingPaused, `is`(true))
    }

    @Test
    fun resumeRecording_recordingPaused_false() {
        // Given
        val recorderViewModel = RecorderViewModel()

        // When
        recorderViewModel.startRecording()
        recorderViewModel.pauseRecording()
        recorderViewModel.pauseRecording()

        // Then
        val recordingPaused = recorderViewModel.recordingPaused.getOrAwaitValue()
        assertThat(recordingPaused, `is`(false))
    }

}
