package com.example.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.model.Advert
import com.google.android.material.textview.MaterialTextView
import java.util.*

class AdvertsAdapter(val list: List<HashMap<String, Object>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_advert, parent, false)
        return AdvertsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemsViewHolder = holder as AdvertsViewHolder
        itemsViewHolder.advertTitle.setText(list[position].get("title").toString())
        itemsViewHolder.advertCoast.setText(list[position].get("coast").toString() + " " + "руб.")
        itemsViewHolder.date.setText(list[position].get("date").toString())
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AdvertsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val advertImageView = itemView.findViewById<ImageView>(R.id.advert_image_view)
        val advertTitle = itemView.findViewById<MaterialTextView>(R.id.advert_title_text_view)
        val advertCoast = itemView.findViewById<MaterialTextView>(R.id.advert_coast_text_view)
        val date = itemView.findViewById<MaterialTextView>(R.id.advert_date_text_view)

    }
}