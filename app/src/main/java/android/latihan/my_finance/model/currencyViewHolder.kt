package android.latihan.my_finance.model

import android.latihan.my_finance.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class currencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var curTag : TextView = itemView.findViewById(R.id.currencyTag)
    var curCountry : TextView = itemView.findViewById(R.id.currencyTitle)
    var imgCheck : ImageView = itemView.findViewById(R.id.checkViewButt)
    fun bind(curCurrency: Currency, clickListener: (Currency) -> Unit){
        itemView.setOnClickListener{clickListener(curCurrency)}
    }
}