package com.ulises.usecase.countdown

import app.cash.turbine.test
import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetAllEventsUseCaseTest {

    private val repository = mockk<CountdownRepository>(relaxed = true)
    private lateinit var useCase: GetAllEventsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetAllEventsUseCase(repository)
    }

    @Test
    fun `A list is returned at invoke operator`() = runTest {
        val item = mockk<CountdownDate>()
        val flowResult = flowOf(listOf( item, item, item))
        coEvery { repository.getAllCountdown() } returns flowResult
        //
        useCase().test {
            val result = awaitItem()
            assertEquals(3, result.size)
            awaitComplete()
        }
        //
        coVerify { repository.getAllCountdown() }
    }
}