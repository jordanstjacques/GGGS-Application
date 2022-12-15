package com.example.gggsapplication

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter (private val newsList : ArrayList<NewsSlide>,
                   private val imageList : ArrayList<NewsImage>,
                   private val context: Context,
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var mListener: onButtonClickListener

    interface onButtonClickListener {
        fun onButtonClicked (websiteUrl : String)
    }

    fun setOnButtonClickListener (listener : onButtonClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,
            parent, false)
        return NewsViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.newsHeading.text = currentItem.headline
        Glide.with(context).load(imageList[position].imageUrl).into(holder.newsImage)
        holder.newsDescription.text = currentItem.newsDescription
        holder.websiteUrl = currentItem.websiteUrl!!
        /*
        holder.lawnAddress.text = currentItem.address
        Glide.with(context).load(imageList[position].imageUrl).into(holder.lawnImage)
        holder.lawnHours.text = currentItem.availableHours
        holder.lawnPrice.text = currentItem.price
         */
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView: View, listener : onButtonClickListener): RecyclerView.ViewHolder(itemView) {
        val newsHeading : TextView = itemView.findViewById(R.id.newsNametv)
        val newsDescription : TextView = itemView.findViewById(R.id.infoAboutNewstv)
        val newsImage : ImageView = itemView.findViewById(R.id.newsImageView)
        val readMorebtn : Button = itemView.findViewById(R.id.MoreInfobtn)
        var websiteUrl = ""
        init {
            readMorebtn.setOnClickListener{
                listener.onButtonClicked(websiteUrl)
            }
        }
    }
}