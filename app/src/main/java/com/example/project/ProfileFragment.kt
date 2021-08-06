package com.example.project

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.project.databinding.FragmentProfileBinding
import com.example.project.presenter.ProfilePresenter
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment(), Contract.ProfileView {
    val UPLOAD_IMAGE_CODE: Int = 1
    lateinit var binding:FragmentProfileBinding
    var profilePresenter: Contract.ProfilePresenter = ProfilePresenter(this)
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.choosePhotoButton.setOnClickListener(View.OnClickListener {
            uploadImage()
        })

        binding.signOutButton.setOnClickListener(View.OnClickListener {
            profilePresenter.onSignOutButtonIsClicked()
        })
        profilePresenter.onFragmentIsStarted()
        return binding.root
    }

    companion object {

    }
    fun uploadImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, UPLOAD_IMAGE_CODE)
    }
    override fun setUserName(name: String) {
        coroutineScope.launch {
            binding.userNameProfile.setText(name)
        }
    }

    override fun setUserEmail(email: String) {
        coroutineScope.launch {
            binding.userEmailProfile.setText(email)
        }
    }

    override fun setUserPhoto(photoUrl: String) {
        coroutineScope.launch {
            Picasso.get().load(photoUrl)
                .resize(binding.userPhotoProfile.width, binding.userPhotoProfile.height)
                .centerCrop().into(binding.userPhotoProfile)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPLOAD_IMAGE_CODE && resultCode == RESULT_OK){
            uploadUserPhoto(data?.data.toString())
        }
    }
    fun uploadUserPhoto(url:String){
            Picasso.get().load(url)
                .resize(binding.userPhotoProfile.width, binding.userPhotoProfile.height)
                .centerCrop()
                .into(binding.userPhotoProfile)
    }
    override fun signOut(){
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}