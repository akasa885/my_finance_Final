package android.latihan.my_finance.model

import java.util.*

data class dataTransaksi(
    var title : String,
    var date: Date,
    var type : Int,
    var category : String,
    var amount : Int
)