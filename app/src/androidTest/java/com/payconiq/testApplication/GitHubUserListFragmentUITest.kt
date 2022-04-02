package co.cdmunoz.nasaroverphotos.ui.home

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.payconiq.testApplication.EspressoIdlingResourceRule
import com.payconiq.testApplication.EspressoTestsHelpers
import com.payconiq.testApplication.R
import com.payconiq.testApplication.ui.MainActivity
import com.payconiq.testApplication.ui.gitHubUserList.GitHubUserListRecyclerViewAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class GitHubUserListFragmentUITest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)


    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun initial_state_git_hub_user_list_screen_UI_test() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.search_view)).check(matches(withText("t")))
        onView(withId(R.id.search_btn)).perform(click())
        onView(withId(R.id.fragment_git_hub_user_progress)).check(matches((isDisplayed())))
        with(onView(withId(R.id.gitHub_user_recyclerView))) {
            check(matches(isDisplayed()))
            check(matches(EspressoTestsHelpers.recyclerViewSizeMatcher(10)))
        }
    }

    @Test
    fun scrollTopositionTest() {
        onView(withId(R.id.gitHub_user_recyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<GitHubUserListRecyclerViewAdapter.UserListViewHolder>(
                    17
                )
            )
    }

    @Test
    fun clickItemtWithPosition() {
        onView(withId(R.id.gitHub_user_recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<GitHubUserListRecyclerViewAdapter.UserListViewHolder>(
                    10,
                    click()
                )
            )
    }
}