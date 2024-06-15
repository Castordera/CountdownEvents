package com.ulises.data.repositories

import app.cash.turbine.test
import com.example.domain.models.CountdownDate
import com.ulises.data.datasource.CountdownLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CountdownRepositoryImplTest {

    private val dataSource = mockk<CountdownLocalDataSource>()
    private lateinit var repository: CountdownRepository

    @BeforeEach
    fun setup() {
        repository = CountdownRepositoryImpl(dataSource)
    }

    @Test
    fun `Returns a list of data after calling data source`() = runTest {
        val itemsList = listOf(mockk<CountdownDate>(), mockk<CountdownDate>())
        every { dataSource.getAllCountdown() } returns flowOf(itemsList)
        //
        repository.getAllCountdown().test {
            val response = awaitItem()
            assertTrue(response.size == 2)
            assertEquals(response, itemsList)
            awaitComplete()
        }
        //
        verify {
            dataSource.getAllCountdown()
        }
    }

    @Test
    fun `Return a single item only`() = runTest {
        val item = mockk<CountdownDate>()
        val id = "123456789"
        every { dataSource.getCountdown(any()) } returns flowOf(item)
        //
        repository.getCountdown(id).test {
            assertEquals(item, awaitItem())
            awaitComplete()
        }
        //
        verify {
            repository.getCountdown(id)
        }
    }

    @Test
    fun `A new element is passed over to data source`() = runTest {
        val item = mockk<CountdownDate>()
        coEvery { dataSource.addNewCountdown(any()) } just runs
        //
        repository.addNewCountdown(item)
        //
        coVerify {
            dataSource.addNewCountdown(item)
        }
    }

    @Test
    fun `Delete a item from data source`() = runTest {
        val id = "123456789"
        coEvery { dataSource.deleteCountdown(id) } just runs
        //
        repository.deleteCountdown(id)
        //
        coVerify {
            dataSource.deleteCountdown(id)
        }
    }

    @Test
    fun `Given an event is passed to data source for edit`() = runTest {
        val item = mockk<CountdownDate>()
        coEvery { dataSource.editEvent(any()) } just runs
        //
        repository.editEvent(item)
        //
        coVerify {
            dataSource.editEvent(item)
        }
    }

    @Test
    fun `Given a Set of Ids all passed down to data source`() = runTest {
        val ids = setOf("1","2","3")
        coEvery { dataSource.deleteCountdowns(any()) } just runs
        //
        repository.deleteCountdown(ids)
        //
        coVerify {
            dataSource.deleteCountdowns(ids)
        }
    }
}