package com.example.project.model

import android.util.Log
import com.example.project.model.database.Database
import com.example.project.presenter.AuthenticationPresenter
import com.example.project.presenter.RegistrationPresenter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentication(){
    private var authentication: FirebaseAuth = Firebase.auth
    fun createUserAccountWithEmail(email:String, password:String, presenter: RegistrationPresenter, name:String, data:ByteArray){
        authentication.createUserWithEmailAndPassword(email, password).addOnSuccessListener(
            OnSuccessListener {
                Database.uploadUserImageFromRegistration(getUserID(), data)
                presenter.onRegistrationIsSuccessful(name)
                Log.d("UID", getUserID())
                Log.d("Auth is successful", "true")
            }).addOnFailureListener(OnFailureListener {
            Log.d("Auth is successful", "false")
            presenter.onRegistrationIsFailed()
            })

    }
    fun signOut(){
        authentication.signOut()
    }
    fun getEmail():String{
        return authentication.currentUser?.email.toString()
    }
    fun signInWithEmail(email: String, password: String, presenter: AuthenticationPresenter){
        authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener(
            OnSuccessListener {
                presenter.onLoginIsSuccessful()
            }).addOnFailureListener(OnFailureListener {
                presenter.onLoginIsFailed()
        })
    }

    fun checkUserAuth() : Boolean{
        return authentication.currentUser != null
    }
    fun getUserID(): String {
        return authentication.uid.toString()
    }
}


