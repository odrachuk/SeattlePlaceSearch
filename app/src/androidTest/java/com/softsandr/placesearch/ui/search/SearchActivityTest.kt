package com.softsandr.placesearch.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.softsandr.placesearch.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Oleksandr Drachuk on 29.03.19.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun searchActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val searchAutoComplete = onView(
            Matchers.allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete.perform(ViewActions.replaceText("ho"), closeSoftKeyboard())

        val searchAutoComplete2 = onView(
            Matchers.allOf(
                withId(R.id.search_src_text), withText("ho"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete2.perform(ViewActions.click())

        val searchAutoComplete3 = onView(
            Matchers.allOf(
                withId(R.id.search_src_text), withText("ho"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete3.perform(ViewActions.click())

        val searchAutoComplete4 = onView(
            Matchers.allOf(
                withId(R.id.search_src_text), withText("ho"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete4.perform(ViewActions.replaceText("hoote"))

        val searchAutoComplete5 = onView(
            Matchers.allOf(
                withId(R.id.search_src_text), withText("hoote"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete5.perform(closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(5000)

        val cardView = onView(
            Matchers.allOf(
                withId(R.id.gift_card_inventory_header__img_card),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.view_recycler),
                        childAtPosition(
                            withId(R.id.activity_search_content_frame),
                            3
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        cardView.perform(ViewActions.click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val floatingActionButton = onView(
            Matchers.allOf(
                withId(R.id.activity_details_fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        floatingActionButton.perform(ViewActions.click())

        val appCompatImageButton = onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("com.google.android.material.appbar.CollapsingToolbarLayout")),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageButton.perform(ViewActions.click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val checkableImageView = onView(
            Matchers.allOf(
                withId(R.id.list_item_search_star_iv),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.list_item_search_star_iv_container),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        checkableImageView.perform(ViewActions.click())

        val appCompatImageView = onView(
            Matchers.allOf(
                withId(R.id.search_close_btn), ViewMatchers.withContentDescription("Clear query"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageView.perform(ViewActions.click())

        val searchAutoComplete6 = onView(
            Matchers.allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete6.perform(ViewActions.replaceText("time"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(5000)

        val floatingActionButton2 = onView(
            Matchers.allOf(
                withId(R.id.activity_search_fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        floatingActionButton2.perform(ViewActions.click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val floatingActionButton3 = onView(
            Matchers.allOf(
                withId(R.id.activity_details_fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        floatingActionButton3.perform(ViewActions.click())

        Espresso.pressBack()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val floatingActionButton4 = onView(
            Matchers.allOf(
                withId(R.id.activity_search_fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        floatingActionButton4.perform(ViewActions.click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}