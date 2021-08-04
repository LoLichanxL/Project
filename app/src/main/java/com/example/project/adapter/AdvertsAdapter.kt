package com.example.project.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.HttpAuthHandler
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.project.AdvertFragment
import com.example.project.MainActivity
import com.example.project.R
import com.example.project.model.Advert
import com.example.project.model.database.Database
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.HashMap

class AdvertsAdapter(val list: List<HashMap<String, Object>>,val activity: MainActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_advert, parent, false)

        return AdvertsViewHolder(itemView, activity, list)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemsViewHolder = holder as AdvertsViewHolder
        if(list.isEmpty()){}
        else{

            itemsViewHolder.advertTitle.setText(list[position].get("title").toString())
            itemsViewHolder.advertCoast.setText(list[position].get("coast").toString() + " " + "руб.")
            itemsViewHolder.date.setText(splitDate(list[position].get("date").toString()))
            Log.d("Date", list[position].get("date").toString().split(" ").size.toString())
            Database.storage.reference.child("adverts/" + list[position].get("id") + "/firstImage.jpg").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).resize(
                    itemsViewHolder.advertImageView.width,
                    itemsViewHolder.advertImageView.height
                ).centerCrop().into(itemsViewHolder.advertImageView)

        }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class AdvertsViewHolder(itemView: View, val activity: MainActivity, val list: List<HashMap<String, Object>>) : RecyclerView.ViewHolder(itemView){
        val item = itemView.findViewById<ConstraintLayout>(R.id.item_view)
        val advertImageView = itemView.findViewById<ImageView>(R.id.advert_image_view)
        val advertTitle = itemView.findViewById<MaterialTextView>(R.id.advert_title_text_view)
        val advertCoast = itemView.findViewById<MaterialTextView>(R.id.advert_coast_text_view)
        val date = itemView.findViewById<MaterialTextView>(R.id.advert_date_text_view)
        init {
            item.setOnClickListener(View.OnClickListener {
                activity.createAdvertFragment(list[adapterPosition])
            })
        }
    }

    fun splitDate(date:String): String{
        val result = date.split(" ")[1] + " " + date.split(" ")[2] + " " + date.split(" ")[5]
        return result
    }

}