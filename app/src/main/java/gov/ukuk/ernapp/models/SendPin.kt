package gov.ukuk.ernapp.models

import com.google.gson.annotations.SerializedName

data class SendPin(
    @SerializedName("pin") val pin: String
)
data class CarInfo (
    val govPlate: String? = null
)