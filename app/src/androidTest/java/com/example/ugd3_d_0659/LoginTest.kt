package com.example.ugd3_d_0659


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val materialButton = Espresso.onView(
            Matchers.allOf(
                withId(R.id.btnLogin), withText("Login"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    1
                )
            )
        )
        materialButton.perform(scrollTo(), click())
        Espresso.onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = Espresso.onView(
            Matchers.allOf(
                withId(R.id.etLayoutUsername),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutUsername),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("fred113"), closeSoftKeyboard())
        val materialButton2 = Espresso.onView(
            Matchers.allOf(
                withId(R.id.btnLogin), withText("Login"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    1
                )
            )
        )
        materialButton2.perform(scrollTo(), click())
        Espresso.onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = Espresso.onView(
            Matchers.allOf(
                withId(R.id.etLayoutPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("12345678"), closeSoftKeyboard())
        val materialButton3 = Espresso.onView(
            Matchers.allOf(
                withId(R.id.btnLogin), withText("Login"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    1
                )
            )
        )
        materialButton3.perform(scrollTo(), click())
        Espresso.onView(isRoot()).perform(waitFor(3000))
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

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController?, view: View?) {
                uiController!!.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
