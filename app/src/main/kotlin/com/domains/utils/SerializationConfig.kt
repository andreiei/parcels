package com.domains.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

inline fun <reified T> encodeToString(data: T): String = SerializerConfig.json.encodeToString(serializer(), data)

open class SerializerConfig {
    companion object {
        val json: Json =
            Json {
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                prettyPrint = true
            }
    }
}
