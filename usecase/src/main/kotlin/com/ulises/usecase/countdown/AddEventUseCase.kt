package com.ulises.usecase.countdown

import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val repository: CountdownRepository
) {
    suspend operator fun invoke(item: CountdownDate) {
        repository.addNewCountdown(item)
    }
}