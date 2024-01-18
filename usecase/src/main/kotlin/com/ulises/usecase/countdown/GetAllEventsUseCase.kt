package com.ulises.usecase.countdown

import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val repository: CountdownRepository
) {
    operator fun invoke(): Flow<List<CountdownDate>> = repository.getAllCountdown()
}