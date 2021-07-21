package com.example.project.presenter

import com.example.project.Contract

class AuthenticationPresenter : Contract.AuthPresenter{
    override fun onEmailIsEmpty() {
        // Throw email exception
    }

    override fun onPasswordIsShort() {
        // Throw password exception
    }

    override fun onRegistrationIsSuccess() {
        // Update our UI

    }

    override fun onSignInIsSuccess() {
        // Update our UI
    }

    override fun onRegistrationIsFailed() {
        // Throw registration failed exception
    }

    override fun onSignInIsFailed() {
        // Throw sign in failed exception

    }

    override fun onUserIsExist() {
        // Update UI
    }

    override fun onUserSignOut() {
        // Update UI

    }


}