package com.ulises.usecase.countdown

import com.ulises.data.repositories.CountdownRepository
import javax.inject.Inject

class DeleteCountdownUseCase @Inject constructor(
    private val repository: CountdownRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteCountdown(id)
    }
}