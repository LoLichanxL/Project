package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project.chat.SingleChatFragment
import com.example.project.databinding.FragmentAdvertBinding
import com.example.project.model.database.Database
import com.squareup.picasso.Picasso

class AdvertFragment(val data:HashMap<String, Object>, val activity: MainActivity) : Fragment() {
    lateinit var binding: FragmentAdvertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvertBinding.inflate(inflater, container, false)
        binding.advertArrowBack.setOnClickListener(View.OnClickListener {
            activity.closeAdvertFragment()
        })
        val photos = data.get("photosUrl") as List<String>

        if (photos.size == 1){
            Database.storage.reference.child("adverts/" + data.get("id") + "/firstImage.jpg").downloadUrl.addOnSuccessListener {
                binding.advertFragmentFirstImageView.visibility = View.VISIBLE
                Picasso.get().load(it).resize(150, 150).centerCrop().into(binding.advertFragmentFirstImageView)
            }
        }

        if (photos.size == 2){
            Database.storage.reference.child("adverts/" + data.get("id") + "/firstImage.jpg").downloadUrl.addOnSuccessListener {
                binding.advertFragmentFirstImageView.visibility = View.VISIBLE
                Picasso.get().load(it).resize(150, 150).centerCrop().into(binding.advertFragmentFirstImageView)
            }
            Database.storage.reference.child("adverts/" + data.get("id") + "/second.jpg").downloadUrl.addOnSuccessListener {
                binding.advertFragmentSecondImageView.visibility = View.VISIBLE
                Picasso.get().load(it).resize(150, 150).centerCrop().into(binding.advertFragmentSecondImageView)
            }
            }

        if (photos.size == 3){
            Database.storage.reference.child("adverts/" + data.get("id") + "/firstImage.jpg").downloadUrl.addOnSuccessListener {
                binding.advertFragmentFirstImageView.visibility = View.VISIBLE
                Picasso.get().load(it).resize(150, 150).centerCrop().into(binding.advertFragmentFirstImageView)

            }
            Database.storage.reference.child("adverts/" + data.get("id") + "/second.jpg").downloadUrl.addOnSuccessListener {
                binding.advertFragmentSecondImageView.visibility = View.VISIBLE
                Picasso.get().load(it).resize(150, 150).centerCrop().into(binding.advertFragmentSecondImageView)

            }
            Database.storage.reference.child("adverts/" + data.get("id") + "/third.jpg").downloadUrl.addOnSuccessListener {
                binding.advertFragmentThirdImageView.visibility = View.VISIBLE
                Picasso.get().load(it).resize(150, 150).centerCrop().into(binding.advertFragmentThirdImageView)
            }
        }

        binding.advertTitle.setText(data.get("title").toString())
        binding.advertCoast.setText(data.get("coast").toString() + " руб.")
        binding.advertDescription.setText(data.get("description").toString())
        binding.advertGender.setText(data.get("gender").toString())
        binding.advertBreed.setText(data.get("breed").toString())
        binding.advertCategory.setText(data.get("category").toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        binding.advertChatButton.setOnClickListener {
            activity.createSingleChatFragment(data.get("uid").toString())
        }
    }
}