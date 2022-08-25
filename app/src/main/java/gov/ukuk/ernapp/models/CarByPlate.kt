package gov.ukuk.ernapp.models

import com.google.gson.annotations.SerializedName

data class CarByPlate(
    val birthDate: String,
    val brand: String,
    val color: String,
    val govPlate: String,
    val model: String,
    val ownerFullName: String,
    val pin: String,
    val steeringSide: String,
    val techNumber: String,
    val techSeries: String
)
data class CarPlateAndPin (
    @SerializedName("plate") val plate: String?,
    @SerializedName("pin") val pin: String?
)