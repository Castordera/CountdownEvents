package com.ulises.addevent.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ulises.addevent.model.UiState
import com.ulises.date_utils.toHumanReadable
import com.ulises.theme.CountdownAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@HiltAndroidTest
class AddEventScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testComposeCreation() {
        val eventName = "This is a huge demo event name"
        val today = LocalDateTime.now()
        initComposeView(withUiState())
        composeTestRule.onNodeWithText(eventName).assertIsDisplayed()
        composeTestRule.onNodeWithText(today.toHumanReadable()).assertIsDisplayed()
    }

//    @Test
//    fun testDemo() {
//        initComposeRoute()
//    }

    private fun initComposeRoute() {
        composeTestRule.setContent {
            CountdownAppTheme {
                AddEventRoute()
            }
        }
    }

    private fun initComposeView(uiState: UiState) {
        composeTestRule.setContent {
            CountdownAppTheme {
                AddEventScreen(uiState = { uiState })
            }
        }
    }

    private fun withUiState(
        isLoading: Boolean = false,
    ): UiState = UiState(
        isLoading = isLoading,
        dateTime = LocalDateTime.now()
    )
}