package com.example.gggsapplication.RentALawn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gggsapplication.R

class LawnAdapter (private val lawnList : ArrayList<LawnListing>,
                   private val imageList : ArrayList<ListingImage>,
                   private val context: Context
): RecyclerView.Adapter<LawnAdapter.LawnViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawnViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lawn_listing,
            parent, false)
        return LawnViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LawnViewHolder, position: Int) {
        val currentItem = lawnList[position]

        holder.lawnAddress.text = currentItem.address
        Glide.with(context).load(imageList[position].imageUrl).into(holder.lawnImage)
        holder.lawnHours.text = currentItem.availableHours
        holder.lawnPrice.text = currentItem.price
    }

    override fun getItemCount(): Int {
        return lawnList.size
    }

    class LawnViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lawnAddress : TextView = itemView.findViewById(R.id.listedLawnTitletv)
        val lawnImage : ImageView = itemView.findViewById(R.id.listedLawnPicture)
        val lawnHours : TextView = itemView.findViewById(R.id.listedLawnHourstv)
        val lawnPrice : TextView = itemView.findViewById(R.id.listedLawnPricetv)
    }
}