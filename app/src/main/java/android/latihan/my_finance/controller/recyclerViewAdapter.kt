package android.latihan.my_finance.controller

import android.latihan.my_finance.model.*
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_transaksi.*

//class recyclerViewAdapter : ListAdapter<dataTransaksi, recyclerViewAdapter.transaksiHolder>(DIFF_CALLBACK) {
//    companion object{
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<dataTransaksi>() {
//            override fun areItemsTheSame(oldItem: dataTransaksi, newItem: dataTransaksi): Boolean {
//                return oldItem.title == newItem.title
//            }
//            override fun areContentsTheSame(oldItem: dataTransaksi, newItem: dataTransaksi): Boolean {
//                return oldItem.title == newItem.title
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : transaksiHolder{
//
//    }
//
//    override fun onBindViewHolder(holder: transaksiHolder, position : Int){
//
//    }
//
//    inner class transaksiHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        init {
//
//        }
//    }
//}