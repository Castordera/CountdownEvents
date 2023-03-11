package com.example.countdownapp.ui.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.example.domain.models.NavClass
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

interface ParcelableNav {
    fun encodeValue(item: NavClass): String {
        return Uri.encode(Json.encodeToJsonElement(item).toString())
    }
}

inline fun <reified T : NavClass> SavedStateHandle.getParam(key: String): T? {
    val value = this.get<String>(key)
    return runCatching {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(Uri.decode(value))
    }.getOrElse {
        //  In case of failing return null instead of crash
        null
    }
}