package com.ulises.list.utils

internal fun List<String>.addCurrentYearIfNotPresent(currentYear: String): List<String> {
    val items = this.toSortedSet()
    if (!items.contains(currentYear)) {
        return with(items) {
            add(currentYear)
            toList().reversed()
        }
    }
    return this
}

internal fun List<String>.yearSelected(currentYear: String): String {
    return when {
        this.contains(currentYear) -> currentYear
        this.size > 1 -> this[0]
        else -> ""
    }
}