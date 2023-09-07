package com.example.phinconattendance

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(
    private val listLocation: ArrayList<Location>
) : RecyclerView.Adapter<LocationAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var selectedItemPosition: Int = -1

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (photo, place, address) = listLocation[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvNamePlace.text = place
        holder.tvAddress.text = address
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listLocation[holder.adapterPosition])
            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.blue_bg_item_adapter)
            holder.tvNamePlace.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvAddress.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.itemView.setBackgroundResource(R.drawable.white_bg_item_adapter)
            holder.tvNamePlace.setTextColor(Color.parseColor("#042C5C"))
            holder.tvAddress.setTextColor(Color.parseColor("#77869E"))
        }
    }

    override fun getItemCount(): Int = listLocation.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.item_image)
        var tvNamePlace: TextView = itemView.findViewById(R.id.item_nameplace)
        var tvAddress: TextView = itemView.findViewById(R.id.item_address)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Location)
    }

    fun isItemSelected(): Boolean {
        return selectedItemPosition != -1
    }


}