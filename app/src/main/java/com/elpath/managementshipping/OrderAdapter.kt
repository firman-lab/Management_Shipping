package com.elpath.managementshipping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_order.view.*

class OrderAdapter(var orderList: ArrayList<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
       return orderList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(response: Order){
            with(itemView){
                nama.text = response.nama_pembeli
                barang.text = response.nama_barang
                jml_barang.text=response.jumlah_barang
                alamat.text = response.alamat
                resi.text = response.kode_kirim
                status.text = response.status

                when (response.kurir) {
                    "gojek" -> {
                        imgKurir.setImageResource(R.drawable.gojek)
                    }
                    "grab" -> {
                        imgKurir.setImageResource(R.drawable.grab)
                    }
                    else -> {
                        imgKurir.setImageResource(R.drawable.jnt)
                    }
                }
            }
        }
    }
}

