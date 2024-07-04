package com.ulises.addevent.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ulises.addevent.model.UiState
import com.ulises.date_utils.toHumanReadable
import com.ulises.theme.CountdownAppTheme
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class AddEventScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testComposeCreation() {
        val eventName = "This is a huge demo event name"
        val today = LocalDateTime.now()
        initComposeView(withUiState(eventName = eventName))
        composeTestRule.onNodeWithText(eventName).assertIsDisplayed()
        composeTestRule.onNodeWithText(today.toHumanReadable()).assertIsDisplayed()
    }

    @Test
    fun testDemo() {
        initComposeRoute()
    }

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
                AddEventScreen(uiState = uiState)
            }
        }
    }

    private fun withUiState(
        isLoading: Boolean = false,
        eventName: String,
    ): UiState = UiState(
        isLoading = isLoading,
        eventName = eventName,
        dateTime = LocalDateTime.now()
    )
}