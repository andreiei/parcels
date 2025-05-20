package services

import entity.ParcelId
import entity.PostCode
import kotlinx.serialization.Serializable
import router.parcels.request.ParcelStatus
import utils.LocalDateSerializer
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Mock repository for a persistent database storage.
 */
class ParcelRepository {
    private val parcels = mutableListOf<ParcelStorageObject>()

    fun addParcel(parcel: ParcelStorageObject): Result<Boolean> {
        return Result.success(parcels.add(parcel))
    }

    fun getParcels(date: LocalDate): List<ParcelStorageObject> {
        return parcels.filter { it.createdDate == date }
    }

    companion object {
        @Serializable
        data class ParcelStorageObject(
            val id: ParcelId,
            val weight: Double,
            val postCode: PostCode,
            val address: String,
            val status: ParcelStatus,
            @Serializable(with = LocalDateSerializer::class)
            val createdDate: LocalDate = LocalDate.now(ZoneOffset.UTC),
        )
    }
}
