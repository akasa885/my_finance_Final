package android.latihan.my_finance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TransaksiData (
    var Ttype : String? = null,
    var Tcat : String? = null,
    var Tamount : Number? = 0,
    var Ttitle : String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
    return mapOf(
        "Ttitle" to Ttitle,
        "Ttype" to Ttype,
        "Tamount" to Tamount,
        "Ttitle" to Ttitle
    )
    }
}