package android.latihan.my_finance.controller

import android.content.Context
import android.latihan.my_finance.R
import android.latihan.my_finance.model.Category
import android.latihan.my_finance.model.categoryViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class rv_CatSetting (val context: Context, val catList: List<Category>) : RecyclerView.Adapter<categoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_category, parent,false)
        return categoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        if(position < catList.size){
            val category = catList[position]
            holder.catTitle.text = category.Ctitle
            holder.catType.text = category.Ctype
            if (category.Ctype == "Income"){
                holder.catType.setTextColor(ContextCompat.getColor(context, R.color.textIncome))
            }else{
                holder.catType.setTextColor(ContextCompat.getColor(context, R.color.textOutcome))
            }

            holder.bind(catList[position])
        }
    }

    override fun getItemCount(): Int {
        return  catList.size
    }

}