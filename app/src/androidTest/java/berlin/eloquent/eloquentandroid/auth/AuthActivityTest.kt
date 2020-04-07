package berlin.eloquent.eloquentandroid.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import berlin.eloquent.eloquentandroid.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AuthActivityTest {

    @get: Rule
    val activityScenario = ActivityScenarioRule(AuthActivity::class.java)

    @Test
    fun test_activity_in_view() {
        onView(withId(R.id.login_container)).check(matches(isDisplayed()))
    }

    @Test
    fun click_on_login_navigates_to_main_activity() {
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.home_container)).check(matches(isDisplayed()))
    }

}