package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.project.chat.SingleChatFragment
import com.example.project.databinding.ActivityMainBinding
import com.example.project.model.Authentication
import com.example.project.model.Chat
import com.example.project.model.User
import com.example.project.model.database.Database
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class MainActivity : AppCompatActivity(), Contract.MainView {
    private val ADVERT_FIRST_IMAGE_PICK_REQUEST_CODE: Int = 1
    private val ADVERT_SECOND_IMAGE_PICK_REQUEST_CODE: Int = 2
    private val ADVERT_THIRD_IMAGE_PICK_REQUEST_CODE: Int = 3
    private lateinit var viewBinding: ActivityMainBinding
    private val addAdvertFragment: AddAdvertFragment = AddAdvertFragment()
    private lateinit var mainScreen: ConstraintLayout
    lateinit var fragmentContainer: FragmentContainerView
    private lateinit var chat_toolbar: ConstraintLayout
    private lateinit var returnButton: ImageButton
    private lateinit var toolbar_info: Toolbar
    private lateinit var profile_fullname: TextView
    private lateinit var profile_photo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val bottomNavView = viewBinding.bottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView.setupWithNavController(navController)
        mainScreen = findViewById(R.id.main_screen)
        fragmentContainer = findViewById(R.id.fragment_container)
        chat_toolbar = findViewById(R.id.chat_toolbar)
        toolbar_info = findViewById(R.id.toolbar_info)
        returnButton = findViewById(R.id.profile_back)
        profile_fullname = findViewById(R.id.profile_fullname)
        profile_photo = findViewById(R.id.profile_image)

    }

    override fun openCreateAdvertFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, addAdvertFragment, null)
            .addToBackStack(null).commit()
        Log.d("BackStackSize", supportFragmentManager.backStackEntryCount.toString())
        mainScreen.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
    }

    override fun closeCreateAdvertFragment() {
        supportFragmentManager.popBackStack()
        Log.d("BackStackSize", supportFragmentManager.backStackEntryCount.toString())

        mainScreen.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }

    override fun uploadAdvertImage(code: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        when (code) {
            1 -> startActivityForResult(intent, ADVERT_FIRST_IMAGE_PICK_REQUEST_CODE)
            2 -> startActivityForResult(intent, ADVERT_SECOND_IMAGE_PICK_REQUEST_CODE)
            3 -> startActivityForResult(intent, ADVERT_THIRD_IMAGE_PICK_REQUEST_CODE)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            closeCreateAdvertFragment()
            Log.d("FragmentISClosed:", "true")
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADVERT_FIRST_IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUrl = data?.data
            Picasso.get().load(imageUrl).into(addAdvertFragment.binding.firstAdvertImageView)
            addAdvertFragment.binding.firstContainer.visibility = View.GONE
            addAdvertFragment.binding.firstImageViewContainer.visibility = View.VISIBLE
        }
        if (requestCode == ADVERT_SECOND_IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUrl = data?.data
            Picasso.get().load(imageUrl).into(addAdvertFragment.binding.secondAdvertImage)
            addAdvertFragment.binding.secondContainer.visibility = View.GONE
            addAdvertFragment.binding.secondImageViewContainer.visibility = View.VISIBLE
        }
        if (requestCode == ADVERT_THIRD_IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUrl = data?.data
            Picasso.get().load(imageUrl).into(addAdvertFragment.binding.thirdAdvertImage)
            addAdvertFragment.binding.thirdContainer.visibility = View.GONE
            addAdvertFragment.binding.thirdImageViewContainer.visibility = View.VISIBLE
        }
    }

    fun createAdvertFragment(data: HashMap<String, Object>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AdvertFragment(data, this), null).commit()
        Log.d("BackStackSize", supportFragmentManager.backStackEntryCount.toString())
        fragmentContainer.visibility = View.VISIBLE
        mainScreen.visibility = View.GONE
    }

    fun closeAdvertFragment() {
        fragmentContainer.visibility = View.GONE
        mainScreen.visibility = View.VISIBLE
    }

    fun createSingleChatFragment(uid: String) {
        val bundle = Bundle()
        bundle.putString("strArg", uid)
        val singleChatFragment = SingleChatFragment()
        singleChatFragment.arguments = bundle
        Log.d("1", "$bundle")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, singleChatFragment).commit()
        fragmentContainer.visibility = View.VISIBLE
        mainScreen.visibility = View.GONE
        toolbar_info.visibility = View.VISIBLE
        chat_toolbar.visibility = View.VISIBLE
        returnButton.setOnClickListener() {
            closeSingleChatFragment()
        }

        Database.database.reference.child("Users").child(uid).child("name").get()
            .addOnSuccessListener {
                if (it.value != null) {
                    val userName = it.value as String
                    profile_fullname.text = userName
                }
            }

//        Database.storage.reference.child("users/$uid/userPhoto.jpg").downloadUrl
//            .addOnSuccessListener {
//                Picasso.get().load(it).resize(150, 150).centerCrop().into(profile_photo)
//            }

        }

    fun closeSingleChatFragment() {
        fragmentContainer.visibility = View.GONE
        mainScreen.visibility = View.VISIBLE
        chat_toolbar.visibility = View.GONE
        toolbar_info.visibility = View.GONE
    }

}