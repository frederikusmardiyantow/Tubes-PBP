package com.example.ugd3_d_0659.webAPI


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.ugd3_d_0659.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddEditUserActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AddEditUserActivity::class.java)

    @Test
    fun addEditUserActivityTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.et_nama),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_nama),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), replaceText("Ttee"), closeSoftKeyboard())
        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_username),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_username),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText("Test"), closeSoftKeyboard())
        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_email),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("Test"), closeSoftKeyboard())
        val materialButton4 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_email),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText4.perform(scrollTo(), replaceText("edmond@gmail.com"), closeSoftKeyboard())
        val materialButton5 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_password),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("ed3003"), closeSoftKeyboard())
        val materialButton6 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.et_konfirmasiPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_konfirmasiPassword),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText6.perform(scrollTo(), replaceText("ed3004"), closeSoftKeyboard())
        val materialButton7 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.et_konfirmasiPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_konfirmasiPassword),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText7.perform(scrollTo(), replaceText("ed3003"), closeSoftKeyboard())
        val materialButton8 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.et_tglLahir),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_tglLahir),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText8.perform(scrollTo(), replaceText("32/12/2007"), closeSoftKeyboard())
        val materialButton9 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText9 = onView(
            allOf(
                withId(R.id.et_tglLahir),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_tglLahir),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText9.perform(scrollTo(), replaceText("30/12/2007"), closeSoftKeyboard())
        val materialButton10 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton10.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.et_telp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_telp),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText10.perform(scrollTo(), replaceText("0865"), closeSoftKeyboard())
        val materialButton11 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton11.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.et_telp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_telp),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText11.perform(scrollTo(), replaceText("087945673456"), closeSoftKeyboard())
        val materialButton12 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton12.perform(click())
        onView(isRoot()).perform(waitFor(3000))
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
