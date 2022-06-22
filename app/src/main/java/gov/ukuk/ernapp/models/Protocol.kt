package gov.ukuk.ernapp.models



data class Protocol(
     val id: Int? = null,
     val code: String? = null,
     val paymentCode: String? = null,
     val registrationDate: String? = null,

     val article: Article? = null,

     val paymentFineTotal: Int? = null,
     val penaltyAmount: Int? = null,

     val physicalData: PhysicalData? = null

)

data class PhysicalData(
     val inn: String? = null,
     val name: String? = null,
     val surname: String? = null,
     val patronymic: String? = null
)

data class Article(
     val id: Int? = null,
     val number: String? = null,
     val title: String? = null,
     val hasDiscount: Boolean? = null,
     val hasChild: Boolean? = null,
     val fineForJuridical: Int? = null,
     val fineForPhysical: Int? = null
)



//code
// registrationDate
//artcal.number

//paymentFineTotal" +
//    "penaltyAmount"

//physicalData Name Sername
