package com.ulises.addevent.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.ulises.addevent.model.UiState
import com.ulises.date_utils.zero
import com.ulises.preview_data.getMockCountDown
import com.ulises.usecase.countdown.AddEventUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetEventUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.time.LocalDateTime

class AddEventViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherRule = TestCoroutineExtension()

    private val addEventUseCase: AddEventUseCase = mockk()
    private val getEventUseCase: GetEventUseCase = mockk()
    private val editEventUseCase: EditEventUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private lateinit var viewModel: AddEventViewModel

    @Test
    fun `Should not call to get an event because is a new one`() = runTest {
        val todayDate = LocalDateTime.now().zero()
        //
        init(id = "null")
        //
        viewModel.uiState.test {
            val item = awaitItem()
            assertTrue(item.dateTime?.compareWith(todayDate) == true)
        }

        verify(inverse = true) {
            getEventUseCase(any())
        }
    }

    @Test
    fun `Should call to get an event because eventId is not null`() = runTest {
        val eventId = "1234"
        val event = getMockCountDown(id = eventId)
        every { getEventUseCase(any()) } returns flowOf(event)
        //
        init(eventId)
        //
        viewModel.uiState.test {
            assertEquals(
                UiState(
                    eventName = event.name,
                    dateTime = event.dateToCountdown,
                    saveButtonEnabled = true,
                    isLoading = false
                ),
                awaitItem()
            )
        }
        verify {
            getEventUseCase(eventId)
        }
    }

    @Test
    fun `Should change calendar visibility`() = runTest {
        //
        init("null")
        //
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onChangeCalendarVisibility(true)
            assertTrue(awaitItem().dateDialogVisible)
            viewModel.onChangeCalendarVisibility(false)
            assertFalse(awaitItem().dateDialogVisible)
            viewModel.onChangeCalendarVisibility(true)
            assertTrue(awaitItem().dateDialogVisible)
        }
    }

    private fun LocalDateTime.compareWith(date: LocalDateTime): Boolean {
        return year == date.year && dayOfYear == date.dayOfYear
    }

    private fun init(id: String) {
        every { savedStateHandle.get<String>("item") } returns id
        viewModel = AddEventViewModel(
            savedStateHandle = savedStateHandle,
            addEventUseCase = addEventUseCase,
            getEventUseCase = getEventUseCase,
            editEventUseCase = editEventUseCase
        )
    }
}