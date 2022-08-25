package gov.ukuk.ernapp.models.protocol

import java.io.Serializable

data class Protocol(
    val article: Article,
    val code: String,
    val expireEndAt: String,
    val expireStartAt: String,
    val id: Int,
    val juridicalData: String,
    val movementPoint: String,
    val organTitle: String,
    val part: Part,
    val paymentCode: String,
    val paymentFineTotal: Double,
    val paymentStatus: String,
    val penaltyAmount: Double,
    val physicalData: PhysicalData,
    val pointType: String,
    val pssiName: String,
    val registrationDate: String,
    val type: String,
    val vehicleBrand: String,
    val vehicleModel: String,
    val violationAt: String
): Serializable