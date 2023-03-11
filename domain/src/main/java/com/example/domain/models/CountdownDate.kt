package com.example.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("CountdownDate")
data class CountdownDate(
    val id: String = "${Date().time}",
    val name: String,
    val dateToCountdown: String
) : NavClass

@Serializable
sealed interface NavClass