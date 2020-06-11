package android.latihan.my_finance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Category (
    var userid : String? = null,
    var Ctype : String? = null,
    var Ctitle: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to userid,
            "title" to Ctitle,
            "type" to Ctype
        )
    }
}