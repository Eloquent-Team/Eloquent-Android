package berlin.eloquent.eloquentandroid.feedback

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import berlin.eloquent.eloquentandroid.MainCoroutineRule
import berlin.eloquent.eloquentandroid.fakes.FakeRecorderRepository
import berlin.eloquent.eloquentandroid.getOrAwaitValue
import berlin.eloquent.eloquentandroid.main.feedback.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.`is`
import org.hamcrest.junit.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedbackViewModelTest {

    private lateinit var feedbackViewModel: FeedbackViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        // Given
        feedbackViewModel = FeedbackViewModel(FakeRecorderRepository())
    }

    @Test
    fun `when setRecording() is called, the recording object should be set`() = runBlocking {
        // When
        feedbackViewModel.setRecording(1L)

        // Then
        val recording = feedbackViewModel.recording.getOrAwaitValue()
        assertThat(recording.recordingId, `is`(1L))
        assertThat(recording.title, `is`("TestRecording"))
        assertThat(recording.tags, `is`("test more tests"))
        assertThat(recording.length, `is`(100L))
        assertThat(recording.date, `is`("2020-03-24 12:24:34"))
        assertThat(recording.fileUrl, `is`("fileUrl"))
    }

    @Test
    fun `when setRecording() is called, the timeCodeText value should be formatted`() = runBlocking {
        // When
        feedbackViewModel.setRecording(1L)

        // Then
        val timeCodeText = feedbackViewModel.timeCodeText.getOrAwaitValue()
        assertThat(timeCodeText, `is`("01:40"))
    }

}