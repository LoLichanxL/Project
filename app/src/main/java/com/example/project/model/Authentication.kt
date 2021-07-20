package com.example.project.model

import com.example.project.presenter.AuthenticationPresenter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentication {
    private val presenter: AuthenticationPresenter = AuthenticationPresenter()
    private var authentication: FirebaseAuth = Firebase.auth

    fun createUserAccount(email:String, password:String){
        if (validateData(email, password)){
            authentication.createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                OnSuccessListener {
                    presenter.onRegistrationIsSuccess()
                }).addOnFailureListener(OnFailureListener {
                    presenter.onRegistrationIsFailed()
            })
        }
    }

    fun signInWithEmail(email: String, password: String){
        authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener(
            OnSuccessListener {
                presenter.onSignInIsSuccess()
            }).addOnFailureListener(OnFailureListener {
                presenter.onSignInIsFailed()
        })
    }

    fun checkUserAuth(){
        if (authentication.currentUser != null)
            presenter.onUserIsExist()
    }
    private fun validateData(email: String, password: String):Boolean{
        if (email.length == 0) {
            presenter.onEmailIsEmpty()
            return false
        }
        if(password.length < 6) {
            presenter.onPasswordIsShort()
            return false
        }
        else
            return true
    }
}


