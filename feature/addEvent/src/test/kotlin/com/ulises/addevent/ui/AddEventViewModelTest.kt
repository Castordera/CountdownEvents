package com.ulises.addevent.ui

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.ulises.addevent.model.Actions
import com.ulises.addevent.model.UiState
import com.ulises.common.navigation.Screen
import com.ulises.date_utils.zero
import com.ulises.preview_data.getMockCountDown
import com.ulises.usecase.countdown.AddEventUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetEventUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
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
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private lateinit var viewModel: AddEventViewModel

    @Test
    fun `Should not call to get an event because is a new one`() = runTest {
        val todayDate = LocalDateTime.now().zero()
        init(id = null)
        assertEquals(viewModel.eventId, null)
        //
        viewModel.uiState.test {
            val item = awaitItem()
            assertTrue(item.dateTime?.compareWith(todayDate) == true)
        }
        //
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
                    dateTime = event.dateToCountdown,
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
    fun `Should clear error message`() = runTest {
        val error = "This is a message Error"
        coEvery { addEventUseCase(any()) } throws Exception(error)
        //
        init("null")
        //
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onHandleAction(Actions.SendData)
            assertEquals(awaitItem().error, error)
            viewModel.onHandleAction(Actions.DismissError)
            assertTrue(awaitItem().error == null)
        }
        coVerify {
            addEventUseCase(any())
        }
    }

    @Test
    fun `Should send data to save item`() = runTest {
        val name = "This is a demo name"
        coEvery { addEventUseCase(any()) } just runs
        //
        init("null")
        //
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onHandleAction(Actions.UpdateName(name))
            viewModel.onHandleAction(Actions.SendData)
            assertTrue(awaitItem().goBack)
        }
        coEvery { addEventUseCase(any()) }
    }

    private fun LocalDateTime.compareWith(date: LocalDateTime): Boolean {
        return year == date.year && dayOfYear == date.dayOfYear
    }

    private fun init(id: String?) {
        val mockModel = mockk<Screen.AddEditCountdown>()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<Screen.AddEditCountdown>() } returns mockModel
        every { mockModel.countdownId } returns id
        viewModel = AddEventViewModel(
            savedStateHandle = savedStateHandle,
            addEventUseCase = addEventUseCase,
            getEventUseCase = getEventUseCase,
            editEventUseCase = editEventUseCase,
        )
    }
}