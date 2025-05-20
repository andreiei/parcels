import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

inline fun <reified T> encodeToString(data: T): String = SerializerConfig.json.encodeToString(serializer(), data)
inline fun <reified T> decodeFromString(data: String): T = SerializerConfig.json.decodeFromString(serializer(), data)

open class SerializerConfig {
    companion object {
        val json: Json =
            Json {
                isLenient = true
                prettyPrint = true
                encodeDefaults = true
                ignoreUnknownKeys = true
            }
    }
}
