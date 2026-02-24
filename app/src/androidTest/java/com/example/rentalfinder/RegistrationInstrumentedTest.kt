package com.example.rentalfinder

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.rentalfinder.view.LoginActivity
import com.example.rentalfinder.view.RegistrationActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RegistrationActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun registerUserSuccessNavigatesToLogin() {

        composeRule.onNodeWithTag("reg_email")
            .performTextInput("testinstrument@gmail.com")

        composeRule.onNodeWithTag("reg_password")
            .performTextInput("Test12345")

        composeRule.onNodeWithTag("reg_confirm_password")
            .performTextInput("Test12345")

        composeRule.onNodeWithTag("terms_checkbox")
            .performClick()

        composeRule.onNodeWithTag("signup_button")
            .performClick()

        // Wait for Firebase + Navigation
        composeRule.waitForIdle()
        Thread.sleep(4000)

        Intents.intended(
            hasComponent(LoginActivity::class.java.name)
        )
    }
}