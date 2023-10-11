package com.ulises.usecase.countdown

import com.example.domain.models.CountdownDate
import com.ulises.data.repositories.CountdownRepository
import javax.inject.Inject

class EditEventUseCase @Inject constructor(
    private val repository: CountdownRepository
) {
    suspend operator fun invoke(event: CountdownDate) {
        repository.editEvent(event)
    }
}