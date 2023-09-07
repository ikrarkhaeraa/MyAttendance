package com.example.phinconattendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DayAdapter(
    private val listLogDay: ArrayList<LogDay>
) : RecyclerView.Adapter<DayAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_logs, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (photo, place, address, status, time) = listLogDay[position]
        holder.imgPhoto.setImageResource(photo.toInt())
        holder.tvNamePlace.text = place
        holder.tvAddress.text = address
        holder.tvStatus.text = status
        holder.tvTime.text = time
    }

    override fun getItemCount(): Int = listLogDay.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.item_image)
        var tvNamePlace: TextView = itemView.findViewById(R.id.name_place)
        var tvAddress: TextView = itemView.findViewById(R.id.item_address)
        var tvStatus: TextView = itemView.findViewById(R.id.item_status)
        var tvTime: TextView = itemView.findViewById(R.id.time)
    }


}