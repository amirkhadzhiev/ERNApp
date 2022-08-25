package gov.ukuk.ernapp.models.violation

data class Violation(
    val code: String,
    val message: String,
    val protocolsByPin: List<ProtocolsByPin>
)

data class ProtocolsByPin(
    val address: String,
    val birthdate: String,
    val carBrand: String,
    val carModel: String,
    val deliveryDate: String,
    val firstname: String,
    val lastname: String,
    val paymentNumber: String,
    val paymentStatusName: String,
    val plateNumber: String,
    val protocolNumber: String,
    val violationAmmount: String,
    val violationArticle: String,
    val violationDate: String,
    val violationPlace: String,
    val violationType: String
)