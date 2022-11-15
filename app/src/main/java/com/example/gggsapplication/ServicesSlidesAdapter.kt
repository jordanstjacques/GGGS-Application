package com.example.gggsapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Creating an adapter for the recycler view on the homepage
class ServicesSlidesAdapter (private val slideList : ArrayList<NewsSlide>) :
    RecyclerView.Adapter<ServicesSlidesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val slideTitle : TextView = itemView.findViewById(R.id.ServiceNametv)
        val slideDescription : TextView = itemView.findViewById(R.id.InfoAboutServicetv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = slideList[position]
        holder.slideTitle.text = currentItem.serviceName
        holder.slideDescription.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return slideList.size
    }
}