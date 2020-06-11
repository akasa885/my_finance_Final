package android.latihan.my_finance.model


import android.latihan.my_finance.R
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class categoryViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    var catTitle : TextView = itemView.findViewById(R.id.categoryTitle)
    var catType : TextView = itemView.findViewById(R.id.categoryType)
    var buttonDel : ImageView = itemView.findViewById(R.id.delViewButt)
    fun bind(kategori : Category){
        buttonDel.setOnClickListener {
            Toast.makeText(itemView.context, "${kategori.Ctitle} akan dihapus", Toast.LENGTH_LONG).show()
        }
    }
}