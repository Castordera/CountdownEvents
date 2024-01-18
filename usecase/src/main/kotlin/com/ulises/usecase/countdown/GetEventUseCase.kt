package com.ulises.usecase.countdown

import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val repository: CountdownRepository
) {
    operator fun invoke(id: String): Flow<CountdownDate> {
        return repository.getCountdown(id)
    }
}