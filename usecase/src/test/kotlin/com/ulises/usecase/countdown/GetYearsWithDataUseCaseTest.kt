package com.ulises.usecase.countdown

import app.cash.turbine.test
import com.ulises.data.repositories.CountdownRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetYearsWithDataUseCaseTest {

    private lateinit var useCase: GetYearsWithDataUseCase
    private val repository = mockk<CountdownRepository>()

    @BeforeEach
    fun setup() {
        useCase = GetYearsWithDataUseCase(repository)
    }

    @Test
    fun `Should get elements from repository`() = runTest {
        //
        val years = listOf("200", "201", "202")
        every { repository.getAllYearsWithItems() } returns flowOf(years)
        //
        useCase().test {
            assertEquals(years, awaitItem())
            awaitComplete()
        }
        //
        verify { repository.getAllYearsWithItems() }
    }

    @Test
    fun `An error is received from repository`() = runTest {
        //
        val errorMessage = "Error from repo"
        every { repository.getAllYearsWithItems() } returns flow { throw Exception(errorMessage) }
        //
        useCase().test {
            assertEquals(awaitError().message, errorMessage)
        }
        //
        verify { repository.getAllYearsWithItems() }
    }
}