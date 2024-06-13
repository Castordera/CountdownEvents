package com.ulises.usecase.countdown

import com.ulises.data.repositories.CountdownRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteEventUseCaseTest {

    private val repository = mockk<CountdownRepository>(relaxed = true)
    private lateinit var useCase: DeleteEventUseCase

    @BeforeEach
    fun setup() {
        useCase = DeleteEventUseCase(repository)
    }

    @Test
    fun `Given an Id a deletion call is performed`() = runTest {
        //
        val id = "123456789"
        useCase(id)
        //
        coVerify { repository.deleteCountdown(id) }
    }

    @Test
    fun `Given a Set of strings a deletion call is performed`() = runTest {
        //
        val ids = setOf("111", "222", "333")
        useCase(ids)
        //
        coVerify { repository.deleteCountdown(ids) }
    }
}