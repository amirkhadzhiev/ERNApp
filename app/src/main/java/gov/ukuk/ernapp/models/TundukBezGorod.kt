package gov.ukuk.ernapp.models

import com.google.gson.annotations.SerializedName

data class TundukBezGorod(
    val message:String,
    val protocols:MutableList<TundukProtocol1>? = null
)

data class TundukProtocol1(
    val paymentNumber:String,
    val violationAmmount:String,
    var govPlate: String? = null
)
data class Info (
    @SerializedName("plate") val plate: String?
)