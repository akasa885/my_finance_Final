package android.latihan.my_finance.model

import com.google.firebase.database.Exclude

data class Currency(
    var curTag : String? = null,
    var curCountry : String? = null,
    var attend : Int? = null,
    var curId : String? = null
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "curTag" to curTag,
            "curCountry" to curCountry,
            "attend" to attend,
            "curId" to curId
        )
    }
}