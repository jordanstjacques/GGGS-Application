package com.example.gggsapplication.FeaturedHouses

import android.R.attr.left
import android.R.attr.right
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gggsapplication.R
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.google.firebase.storage.FirebaseStorage


internal class FeaturedHomeAdapter (private var featuredHomesList: List<FeaturedHouse>,
                                    private var context: Context):
    RecyclerView.Adapter<FeaturedHomeAdapter.FeaturedHomeViewHolder>() {
    internal inner class FeaturedHomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val featuredHomeName : TextView = itemView.findViewById(R.id.fhName)
        val featuredHomeImageView: ImageView = itemView.findViewById(R.id.fhImage)
        val featuredHomeAddress: TextView = itemView.findViewById(R.id.fhAddress)
        val fhTagGrid: FlexboxLayout = itemView.findViewById(R.id.fhTagGrid)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedHomeViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.featured_home,
            parent, false)
        return FeaturedHomeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        println("nithin returning size")
        println(featuredHomesList.size)
        return featuredHomesList.size
    }

    override fun onBindViewHolder(holder: FeaturedHomeViewHolder, position: Int) {
        val currentItem = featuredHomesList[position]
        println("nithin item is")
        println(featuredHomesList[position])
        holder.featuredHomeName.text = currentItem.name
        val storageReference =
            currentItem.fireStorageImagePath?.let { FirebaseStorage.getInstance().getReference(it) }
        storageReference!!.downloadUrl.addOnSuccessListener { uri: Uri? ->
            Glide.with(context).load(uri)
                .into(holder.featuredHomeImageView)
        }
        holder.featuredHomeAddress.text = currentItem.Address
        currentItem.tagList?.forEach { tag ->
            val button = MaterialButton(context)
            var params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginStart = 15
            button.text = tag
            holder.fhTagGrid.addView(button, params)
        }

       // Glide.with(context)
       //     .load(currentItem.fireStorageImagePath?.let {
       //         FirebaseStorage.getInstance().reference.child(
       //             it
       //         )
       //     })
       //     .into(holder.featuredHomeImageView)
    }
}