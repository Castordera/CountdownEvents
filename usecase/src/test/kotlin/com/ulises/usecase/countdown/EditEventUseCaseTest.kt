package com.ulises.usecase.countdown

import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EditEventUseCaseTest {

    private val repository = mockk<CountdownRepository>(relaxed = true)
    private lateinit var useCase: EditEventUseCase

    @BeforeEach
    fun setup() {
        useCase = EditEventUseCase(repository)
    }

    @Test
    fun `Edit call to repository is performed`() = runTest {
        //
        val item = mockk<CountdownDate>()
        useCase(item)
        //
        coVerify { repository.editEvent(item) }
    }
}