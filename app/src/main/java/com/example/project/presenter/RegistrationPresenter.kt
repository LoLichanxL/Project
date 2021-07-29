package com.example.project.presenter

import android.provider.ContactsContract
import com.example.project.Contract
import com.example.project.model.Authentication

class RegistrationPresenter(val view:Contract.RegistrationView) : Contract.RegistrationPresenter {
    val authentication:Authentication = Authentication()
    override fun onRegistrationButtonIsPressed(name: String, email: String, firstPassword: String, secondPassword: String) {
        if (validateData(email, firstPassword, secondPassword))
            authentication.createUserAccountWithEmail(email, firstPassword, this)

    }

    override fun onRegistrationIsSuccessful() {
        view.openMainActivity()
    }

    override fun onRegistrationIsFailed() {
        view.showRegistrationIsFailedSnackBar()
    }

    fun validateData(userEmail: String, userFirstPassword:String, userSecondPassword:String):Boolean{
        if (userEmail.length > 0){
            if (userFirstPassword == userSecondPassword){
                if (userFirstPassword.length >= 6)
                    return true
                else{
                    view.showPasswordIsInvalidSnackBar()
                    return false
                }
            }
            else{
                view.showPasswordSnackBar()
                return false
            }
        }
        else {
            view.showEmailIsInvalidSnackBar()
            return false
        }
    }
}