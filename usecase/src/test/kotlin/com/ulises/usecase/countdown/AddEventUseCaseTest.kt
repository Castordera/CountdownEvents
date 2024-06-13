package com.ulises.usecase.countdown

import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddEventUseCaseTest {

    private val repository = mockk<CountdownRepository>(relaxed = true)
    private lateinit var useCase: AddEventUseCase

    @BeforeEach
    fun setup() {
        useCase = AddEventUseCase(repository)
    }

    @Test
    fun `Invoke is called with a return value`() = runTest {
        //
        val item = mockk<CountdownDate>()
        useCase(item)
        //
        coVerify { repository.addNewCountdown(item) }
    }
}