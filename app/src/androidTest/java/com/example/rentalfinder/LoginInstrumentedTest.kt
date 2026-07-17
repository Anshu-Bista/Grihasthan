package com.example.rentalfinder

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.rentalfinder.view.DashboardActivity
import com.example.rentalfinder.view.LoginActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun loginSuccessNavigatesToDashboard() {

        composeRule.onNodeWithTag("email")
            .performTextInput("bistanshu3@gmail.com")

        composeRule.onNodeWithTag("password")
            .performTextInput("Anshu@123")

        composeRule.onNodeWithTag("login")
            .performClick()

        // Let Compose + Firebase + Navigation settle
        composeRule.waitForIdle()

        Thread.sleep(2000)

        Intents.intended(
            hasComponent(DashboardActivity::class.java.name)
        )
    }
}