package berlin.eloquent.eloquentandroid.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.TestBaseApplication
import berlin.eloquent.eloquentandroid.di.TestAppComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private lateinit var app: TestBaseApplication

    @Before
    fun setUpApp() {
        app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication
        injectTest(app)
    }

    @Test
    fun test_bottomNavigationView_navigation() {
        launchActivity<MainActivity>()

        onView(withId(R.id.home_container)).check(matches(isDisplayed()))

        // navigate to account
        onView(withId(R.id.account)).perform(click())
        onView(withId(R.id.account_container)).check(matches(isDisplayed()))

        // navigate to home
        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.home_container)).check(matches(isDisplayed()))

        // navigate to recorder
        onView(withId(R.id.recorder)).perform(click())
        onView(withId(R.id.recorder_container)).check(matches(isDisplayed()))

        // press back to get to home
        pressBack()
        onView(withId(R.id.home_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recording_procedure() {
        launchActivity<MainActivity>()

        // navigate to recorder
        onView(withId(R.id.recorder)).perform(click())
        onView(withId(R.id.recorder_container)).check(matches(isDisplayed()))

        val recorderButton = onView(withId(R.id.startStopRecording))
        val pauseBtn = onView(withId(R.id.pauseResumeRecording))
        val navigateBtn = onView(withId(R.id.navigate))

        // perform recording
        navigateBtn.check(matches(withEffectiveVisibility(Visibility.GONE)))
        recorderButton.perform(click())
        runBlocking { delay(1000) }
        pauseBtn.perform(click())
        runBlocking { delay(1000) }
        pauseBtn.perform(click())
        runBlocking { delay(1000) }
        recorderButton.perform(click())

        // navigate to player
        navigateBtn.perform(click())

        val titleTextView = onView(withId(R.id.recordingTitle))
        val tagsTextView = onView(withId(R.id.recordingTags))

        // check screen
        onView(withText("00:02")).check(matches(isDisplayed()))
        onView(withSubstring("Rec_")).check(matches(isDisplayed()))
        titleTextView.perform(clearText(), typeText("testTitle"))
        tagsTextView.perform(typeText("test tags"))

        // close keyboard
        pressBack()

        // navigate to feedback
        onView(withId(R.id.analyzeRecording)).perform(click())
        onView(withId(R.id.feedback_container)).check(matches(isDisplayed()))

        // pressBack to get to recorder screen to test playerScreen has been removed from backStack
        pressBack()
        onView(withId(R.id.recorder_container)).check(matches(isDisplayed()))

        // navigate to home
        pressBack()
        onView(withId(R.id.home_container)).check(matches(isDisplayed()))

        // check recycler view
        onView(withText("testTitle")).check(matches(isDisplayed()))
        onView(withText("00:02")).check(matches(isDisplayed()))
    }

    fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

}
