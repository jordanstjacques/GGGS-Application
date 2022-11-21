package com.example.gggsapplication.RentALawn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gggsapplication.R

class LawnAdapter (private val lawnList : ArrayList<LawnListing>): RecyclerView.Adapter<LawnAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lawn_listing,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = lawnList[position]

        holder.lawnAddress.text = currentItem.address
        holder.lawnHours.text = currentItem.availableHours
        holder.lawnPrice.text = currentItem.price
    }

    override fun getItemCount(): Int {
        return lawnList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lawnAddress : TextView = itemView.findViewById(R.id.listedLawnTitletv)
        val lawnHours : TextView = itemView.findViewById(R.id.listedLawnHourstv)
        val lawnPrice : TextView = itemView.findViewById(R.id.listedLawnPricetv)
    }
}