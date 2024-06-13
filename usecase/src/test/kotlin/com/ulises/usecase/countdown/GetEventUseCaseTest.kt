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

class GetEventUseCaseTest {

    private val repository = mockk<CountdownRepository>(relaxed = true)
    private lateinit var useCase: GetEventUseCase

    @BeforeEach
    fun setup() {
        useCase = GetEventUseCase(repository)
    }

    @Test
    fun `Given an Id returns an item from repository`() = runTest {
        val item = mockk<CountdownDate>()
        val flowResult = flowOf(item)
        val id = "123456789"
        coEvery { repository.getCountdown(any()) } returns flowResult
        //
        useCase(id).test {
            assertEquals(item, awaitItem())
            awaitComplete()
        }
        //
        coVerify { repository.getCountdown(id) }
    }
}