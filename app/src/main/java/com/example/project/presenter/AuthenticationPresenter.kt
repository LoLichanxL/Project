package com.example.project.presenter

import android.provider.ContactsContract
import com.example.project.Contract
import com.example.project.LoginActivity
import com.example.project.model.Authentication

class AuthenticationPresenter(val loginActivity: LoginActivity) : Contract.LoginPresenter{
    private val authentication: Authentication = Authentication()

    override fun onLoginButtonIsPressed(userEmail: String, userPassword: String) {
        if (validateData(userEmail, userPassword)) {
            authentication.signInWithEmail(userEmail, userPassword, this)
        }
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
    private fun validateData(email: String, password:String):Boolean{
        if (email.length == 0) {
            loginActivity.catchInvalidDataException()
            return false
        }
        else {
            if(password.length >= 6){
                return true
            }else{
                loginActivity.catchInvalidDataException()
                return false
            }
        }
    }

}