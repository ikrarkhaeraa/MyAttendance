package com.example.phinconattendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val listLocation: ArrayList<Location>
) : RecyclerView.Adapter<ItemAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (photo, place, address) = listLocation[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvNamePlace.text = place
        holder.tvAddress.text = address
    }

    override fun getItemCount(): Int = listLocation.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.item_image)
        var tvNamePlace: TextView = itemView.findViewById(R.id.item_nameplace)
        var tvAddress: TextView = itemView.findViewById(R.id.item_address)
    }

}