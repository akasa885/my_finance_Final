package android.latihan.my_finance.controller

import android.content.Context
import android.latihan.my_finance.R
import android.latihan.my_finance.model.TransaksiData
import android.latihan.my_finance.model.transaksiViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class rvv_TransView (val context: Context, val transList: List<TransaksiData>, val clickListener: (TransaksiData) -> Unit) : RecyclerView.Adapter<transaksiViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : transaksiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_transaksi, parent,false)
        return transaksiViewHolder(view)
    }

    override fun onBindViewHolder(holder: transaksiViewHolder, position : Int){

    }

    override fun getItemCount(): Int {
        return transList.size
    }
}