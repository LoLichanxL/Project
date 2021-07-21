package com.example.project

interface Contract {
    interface Authentication{
        fun createAccount(email:String, password:String)
        fun signIn(email: String, password: String)
    }
    interface AuthPresenter{
        fun onEmailIsEmpty()
        fun onPasswordIsShort()
        fun onRegistrationIsSuccess()
        fun onSignInIsSuccess()
        fun onRegistrationIsFailed()
        fun onSignInIsFailed()
        fun onUserIsExist()
        fun onUserSignOut()
    }
}