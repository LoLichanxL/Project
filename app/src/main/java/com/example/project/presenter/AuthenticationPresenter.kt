package com.example.project.presenter

import com.example.project.Contract
import com.example.project.LoginActivity
import com.example.project.model.Authentication

class AuthenticationPresenter(val loginActivity: LoginActivity) : Contract.LoginPresenter{
    private val authentication: Authentication = Authentication()

    override fun onLoginButtonIsPressed(userEmail: String, userPassword: String) {
        authentication.signInWithEmail(userEmail, userPassword, this)
    }
    override fun onLoginActivityIsStarted() {
        if (authentication.checkUserAuth()){
            loginActivity.openMainActivity()
        }
    }

    override fun onLoginIsSuccessful() {
        loginActivity.openMainActivity()
    }

    override fun onLoginIsFailed() {
        loginActivity.showSignInIsFailedSnackbar()
    }


}