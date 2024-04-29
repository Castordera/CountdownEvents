package com.ulises.event_detail.navigation

import com.example.domain.models.CountdownDate
import com.ulises.common.navigation.Screen

data object CountdownDetailScreen: Screen {

    const val argumentKey = "item"
    override val route: String
        get() = "detailCountDown/{item}"

    fun createRoute(item: CountdownDate) = "detailCountDown/${item.id}"
}