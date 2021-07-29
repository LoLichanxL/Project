package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.project.model.Authentication
import com.example.project.presenter.AuthenticationPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import android.content.Intent

class LoginActivity : AppCompatActivity(), Contract.LoginView{
    private lateinit var userEmailEditText:TextInputEditText
    private lateinit var userPasswordEditText:TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var loginView:ConstraintLayout
    lateinit var fragmentContainer: ConstraintLayout
    lateinit var mainScreen : LinearLayout
    private val fragmentRegistration = RegistrationFragment(this)
    private val presenter:AuthenticationPresenter = AuthenticationPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onLoginActivityIsStarted()
        setContentView(R.layout.activity_login)
        userEmailEditText = findViewById(R.id.login_email_edit)
        userPasswordEditText = findViewById(R.id.login_password_edit)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.login_register_button)
        loginView = findViewById(R.id.login_view)
        fragmentContainer = findViewById(R.id.registration_fragment_container)
        mainScreen = findViewById(R.id.login_main_screen)
        loginButton.setOnClickListener(View.OnClickListener {
            presenter.onLoginButtonIsPressed(userEmailEditText.text.toString(), userPasswordEditText.text.toString())
        })

        registerButton.setOnClickListener(View.OnClickListener {
            openSignUpFragment()
        })
    }

    override fun openMainActivity(){
        // Update user UI
    }

    override fun catchInvalidDataException() {
        Snackbar.make(loginView, "Email or password is invalid", Snackbar.LENGTH_LONG).show()
    }

    override fun showSignInIsFailedSnackbar() {
        Snackbar.make(loginView, "SignIn id failed", Snackbar.LENGTH_LONG).show()
    }

    override fun openSignUpFragment() {
        if (supportFragmentManager.fragments.size == 0)
            supportFragmentManager.beginTransaction().add(R.id.registration_fragment_container, fragmentRegistration).addToBackStack(null).commit()
        else
            supportFragmentManager.beginTransaction().show(fragmentRegistration).addToBackStack(null).commit()
        fragmentContainer.visibility = View.VISIBLE
        mainScreen.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }else if (supportFragmentManager.fragments.get(0).isVisible){
            supportFragmentManager.popBackStack()
            mainScreen.visibility = View.VISIBLE
            Log.d("Fragment is destroyed", "true")
        }
    }
}