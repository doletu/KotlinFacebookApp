package ncmb.mbaas.com.nifcloud.facebook

import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ExecuteUITest {

    var tvHead1: ViewInteraction? = null
    var tvHead2: ViewInteraction? = null
    var btnLogin: ViewInteraction? = null
    var tvHead3: ViewInteraction? = null

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    @Throws(InterruptedException::class)
    fun setup() {
        tvHead1 = Espresso.onView(withId(R.id.textView2))
        tvHead2 = Espresso.onView(withId(R.id.textView))
        btnLogin = Espresso.onView(withId(R.id.login_button))
        tvHead3 = Espresso.onView(withId(R.id.textView4))
        if (TextHelpers.getText(btnLogin).equals("Log out", ignoreCase = true)) {
            btnLogin!!.perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withText("LOG OUT")).perform(ViewActions.click())
        }
    }

    @Test
    fun initialScreen() {
        tvHead1!!.check(ViewAssertions.matches(ViewMatchers.withText("NIFCLOUD")))
        tvHead2!!.check(ViewAssertions.matches(ViewMatchers.withText("mobile backend")))
        btnLogin!!.check(ViewAssertions.matches(ViewMatchers.withText("Log in")))
        tvHead3!!.check(ViewAssertions.matches(ViewMatchers.withText("Touch [Login] to login using Facebook account.")))
    }

    @Test
    @Throws(Exception::class)
    fun doFacebookLogin() {
        val FB_EMAIL = "YOUR_EMAIL@mail.com"
        val FB_PASS = "YOUR_PASS_WORD"
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val timeOut = 1000 * 60

        // Login Activity
        btnLogin!!.perform(ViewActions.click())

        // Facebook WebView - Page 1
        mDevice.wait(Until.findObject(By.clazz(WebView::class.java)), timeOut.toLong())

        // Set Login
        val emailInput = mDevice.findObject(
            UiSelector()
                .instance(0)
                .className(EditText::class.java)
        )
        emailInput.waitForExists(timeOut.toLong())
        emailInput.text = FB_EMAIL

        // Set Password
        val passwordInput = mDevice.findObject(
            UiSelector()
                .instance(1)
                .className(EditText::class.java)
        )
        passwordInput.waitForExists(timeOut.toLong())
        passwordInput.text = FB_PASS

        // Confirm Button Click
        val buttonLogin = mDevice.findObject(
            UiSelector()
                .instance(0)
                .className(Button::class.java)
        )
        buttonLogin.waitForExists(timeOut.toLong())
        buttonLogin.clickAndWaitForNewWindow()

        // Facebook WebView - Page 2
        val buttonOk = mDevice.findObject(
            UiSelector()
                .instance(0)
                .className(Button::class.java)
        )
        buttonOk.waitForExists(timeOut.toLong())
        buttonOk.click()

        // should be properly synchronised with Espresso via IdlingResource,
        // ConditionWatcher or any similar waiting solution
        Thread.sleep(15000)
        btnLogin!!.check(ViewAssertions.matches(ViewMatchers.withText("Log out")))
    }

    private object TextHelpers {
        fun getText(matcher: ViewInteraction?): String? {
            val text = arrayOfNulls<String>(1)
            val va: ViewAction = object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(TextView::class.java)
                }

                override fun getDescription(): String {
                    return "Text of the view"
                }

                override fun perform(uiController: UiController, view: View) {
                    val tv = view as TextView
                    text[0] = tv.text.toString()
                }
            }
            matcher!!.perform(va)
            return text[0]
        }
    }

}