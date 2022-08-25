package gov.ukuk.ernapp.models.protocol

data class Part(
    val fineCategory: String,
    val fineForJuridical: Int,
    val fineForPhysical: Int,
    val hasChild: Boolean,
    val hasDiscount: Boolean,
    val id: Int,
    val number: String,
    val parent: Parent?,
    val title: String
)