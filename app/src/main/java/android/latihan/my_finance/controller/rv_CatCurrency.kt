package android.latihan.my_finance.controller

import android.content.Context
import android.latihan.my_finance.R
import android.latihan.my_finance.model.Currency
import android.latihan.my_finance.model.currencyViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class rv_CatCurrency (val context: Context, val curList: List<Currency>, val clickListener: (Currency) -> Unit) : RecyclerView.Adapter<currencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): currencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_currency, parent,false)
        return currencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: currencyViewHolder, position: Int) {
        if(position < curList.size){
            val currentCurrency = curList[position]
            holder.curTag.text = currentCurrency.curTag
            holder.curCountry.text = currentCurrency.curCountry
            if(currentCurrency.attend == 1){
                holder.imgCheck.setVisibility(View.VISIBLE)
            }
            holder.curTag.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
            holder.bind(curList[position], clickListener)
        }
    }

    override fun getItemCount(): Int {
        return  curList.size
    }
}