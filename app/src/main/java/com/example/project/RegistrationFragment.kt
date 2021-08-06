package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.project.presenter.RegistrationPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import com.example.project.databinding.FragmentRegisterBinding
import com.google.android.material.imageview.ShapeableImageView
import java.io.ByteArrayOutputStream


class RegistrationFragment (val loginActivity: LoginActivity): Fragment(), Contract.RegistrationView {

    lateinit var binding: FragmentRegisterBinding
    private val presenter:Contract.RegistrationPresenter = RegistrationPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.registrationFragmentArrowBack.setOnClickListener(View.OnClickListener {
            closeFragment()
        })
        binding.registerButton.setOnClickListener(View.OnClickListener {
            presenter.onRegistrationButtonIsPressed(binding.registerUsernameEdit.text.toString(), binding.registerEmailEdit.text.toString(),
                binding.registerPasswordEditFirst.text.toString(), binding.registerPasswordEditSecond.text.toString(), getUserPhoto())
        })
        binding.registerUserImage.setOnClickListener(View.OnClickListener {
            loginActivity.onChooseUserImageButtonIsClicked()
        })
        return binding.root
    }
    override fun showEmailIsInvalidSnackBar() {
        Snackbar.make(binding.root, "Email is invalid", Snackbar.LENGTH_LONG).show()
    }

    override fun showPasswordIsInvalidSnackBar() {
        Snackbar.make(binding.root, "Password is invalid", Snackbar.LENGTH_LONG).show()
    }

    override fun showPasswordSnackBar() {
        Snackbar.make(binding.root, "Passwords must be equals", Snackbar.LENGTH_LONG).show()
    }

    override fun showRegistrationIsFailedSnackBar() {
        Snackbar.make(binding.root, "Registration is failed", Snackbar.LENGTH_LONG).show()
    }

    override fun closeFragment() {
        loginActivity.supportFragmentManager.beginTransaction().hide(this)
                .addToBackStack(null).commit()
        loginActivity.fragmentContainer.visibility = View.GONE
        loginActivity.mainScreen.visibility = View.VISIBLE
    }

    override fun openMainActivity() {
        loginActivity.openMainActivity()
    }

    override fun showRegistrationSiSuccessSnackbar() {
        Snackbar.make(binding.root, "Registration is successful", Snackbar.LENGTH_LONG).show()
    }

    fun getUserPhoto():ByteArray{
        binding.registerUserImage.isDrawingCacheEnabled = true
        binding.registerUserImage.buildDrawingCache()
        val bitmap = (binding.registerUserImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        return data
    }
}