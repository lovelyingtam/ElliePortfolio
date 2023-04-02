package com.ellie.jetportfolio

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DemoUITest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun jetPortfolioAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.ellie.jetportfolio", appContext.packageName)
    }

    @Test
    fun navBarIsDisplayed() {
        composeTestRule.onNodeWithText("Profile").assertExists()
        composeTestRule.onNodeWithText("Sample").assertExists()
    }

    @Test
    fun navToProfileScreen() {
        // Click a button using the UI testing API
        composeTestRule.onNodeWithText("Profile").performClick()
        // Wait 3 sec on API callback
        Thread.sleep(3000)
        // Assert that the expected text is displayed
        composeTestRule.onNodeWithContentDescription("Business Card").assertExists()
    }

    @Test
    fun navToSampleScreen() {
        // Click a button using the UI testing API
        composeTestRule.onNodeWithText("Sample").performClick()
        // Assert that the expected text is displayed
        composeTestRule.onNodeWithContentDescription("Add").assertExists()
    }

    @Test
    fun openBusinessCardScreen() {
        navToProfileScreen()
        // Click a button using the UI testing API
        composeTestRule.onNodeWithText("Business Card").performClick()
        // Assert that the expected text is displayed
        composeTestRule.onNodeWithText("Ellie").assertIsDisplayed()
    }
}