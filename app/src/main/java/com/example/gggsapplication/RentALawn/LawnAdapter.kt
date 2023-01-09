package com.example.gggsapplication.RentALawn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gggsapplication.R
// ---------------------------- Creating an adapter for the rent-a-lawn recycler view -----------------------------------
// Clarifying all the parameters
class LawnAdapter (private val lawnList : ArrayList<LawnListing>,
                   private val imageList : ArrayList<ListingImage>,
                   private val ownerList : ArrayList<String>,
                   private val context: Context
): RecyclerView.Adapter<LawnAdapter.LawnViewHolder>() {

    // --------------------------------------- Overriding the specified methods --------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawnViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lawn_listing,
            parent, false)
        return LawnViewHolder(itemView)
    }

    // Assigning different parts of the data class to different elements in the view holder
    override fun onBindViewHolder(holder: LawnViewHolder, position: Int) {
        val currentItem = lawnList[position]

        holder.lawnAddress.text = currentItem.address
        Glide.with(context).load(imageList[position].imageUrl).into(holder.lawnImage)
        holder.lawnHours.text = currentItem.availableHours
        holder.lawnPrice.text = currentItem.price
        holder.lawnOwner.text = ownerList[position]
        holder.lawnEmail.text = currentItem.email
        holder.lawnPhone.text = currentItem.phone

        val isVisible : Boolean = currentItem.visibility
        holder.extraInfoLayout.visibility = if(isVisible) View.VISIBLE else View.GONE

        // Making it so that if the user clicks onto an item he will be able to see a more informative extended version of it
        holder.lawnCard.setOnClickListener{
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return lawnList.size
    }

    // Creating variables for all the widgets inside the view holder
    class LawnViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lawnAddress : TextView = itemView.findViewById(R.id.listedLawnTitletv)
        val lawnImage : ImageView = itemView.findViewById(R.id.listedLawnPicture)
        val lawnHours : TextView = itemView.findViewById(R.id.listedLawnHourstv)
        val lawnPrice : TextView = itemView.findViewById(R.id.listedLawnPricetv)
        val extraInfoLayout : LinearLayout = itemView.findViewById(R.id.extraInfoLayout)
        val lawnOwner : TextView = itemView.findViewById(R.id.listedLawnOwnertv)
        val lawnEmail : TextView = itemView.findViewById(R.id.listedLawnEmailtv)
        val lawnPhone : TextView = itemView.findViewById(R.id.listedLawnPhonetv)
        val lawnCard : CardView = itemView.findViewById(R.id.lawnCard)
    }
}