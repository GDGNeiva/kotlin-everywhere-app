package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdgneiva.kotlineverywhere.kotlineverywhereapp.R
import kotlinx.android.synthetic.main.quotation_list_item.view.*
import models.Product

class AdapterReciclerViewQuotation(val items: ArrayList<Product>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    var onItemClick: ((Product) -> Unit)? = null

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.quotation_list_item,
                parent,
                false
            )
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.imgProduct?.setImageResource(items.get(position).img)
        holder?.txvCode?.text = items.get(position).code
        holder?.txvName?.text = items.get(position).name
        holder?.txvPrice?.text = items.get(position).price.toString()

        holder?.itemView.setOnClickListener {
            onItemClick?.invoke(items.get(position))
        }
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val imgProduct = view.imgProduct
    val txvCode = view.txvCode
    val txvName = view.txvName
    val txvPrice = view.txvPrice
}

