package com.ulises.usecase.countdown

import com.ulises.data.repositories.CountdownRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetYearsWithDataUseCase @Inject constructor(
    private val repository: CountdownRepository,
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getAllYearsWithItems()
    }
}