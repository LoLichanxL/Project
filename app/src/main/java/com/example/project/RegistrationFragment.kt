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


class RegistrationFragment (val loginActivity: LoginActivity): Fragment(), Contract.RegistrationView {
    private lateinit var arrowBack:ImageView
    private lateinit var userNameEditText: TextInputEditText
    private lateinit var userEmailEditText:TextInputEditText
    private lateinit var firstUserPasswordEditText: TextInputEditText
    private lateinit var secondUserPasswordEditText: TextInputEditText
    private lateinit var registrationButton: Button
    private lateinit var viewContainer: View
    private val presenter:Contract.RegistrationPresenter = RegistrationPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        viewContainer = view
        arrowBack = view.findViewById(R.id.registration_fragment_arrow_back)
        userNameEditText = view.findViewById(R.id.register_username_edit)
        userEmailEditText = view.findViewById(R.id.register_email_edit)
        firstUserPasswordEditText = view.findViewById(R.id.register_password_edit_first)
        secondUserPasswordEditText = view.findViewById(R.id.register_password_edit_second)
        registrationButton = view.findViewById(R.id.register_button)
        arrowBack.setOnClickListener(View.OnClickListener {
            closeFragment()
        })
        registrationButton.setOnClickListener(View.OnClickListener {
            presenter.onRegistrationButtonIsPressed(userNameEditText.text.toString(), userEmailEditText.text.toString(),
                firstUserPasswordEditText.text.toString(), secondUserPasswordEditText.text.toString())
        })
        return view
    }

    override fun showEmailIsInvalidSnackBar() {
        Snackbar.make(viewContainer, "Email is invalid", Snackbar.LENGTH_LONG).show()
    }

    override fun showPasswordIsInvalidSnackBar() {
        Snackbar.make(viewContainer, "Password is invalid", Snackbar.LENGTH_LONG).show()
    }

    override fun showPasswordSnackBar() {
        Snackbar.make(viewContainer, "Passwords must be equals", Snackbar.LENGTH_LONG).show()
    }

    override fun showRegistrationIsFailedSnackBar() {
        Snackbar.make(viewContainer, "Registration is failed", Snackbar.LENGTH_LONG).show()
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

}