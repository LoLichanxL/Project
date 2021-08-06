package com.example.project.presenter

import android.provider.ContactsContract
import com.example.project.Contract
import com.example.project.model.Authentication
import com.example.project.model.User
import com.example.project.model.database.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfilePresenter(val profileView: Contract.ProfileView) : Contract.ProfilePresenter {
    val authentication: Authentication = Authentication()
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    override fun onFragmentIsStarted() {
        Database.getUser(authentication.getUserID(), this)
    }

    override fun onUserDataIsDownload(user: User) {
        profileView.setUserEmail(authentication.getEmail())
        profileView.setUserName(user.name)
        coroutineScope.launch {
            Database.storage.reference.child(user.photoUrl).downloadUrl.addOnSuccessListener {
                profileView.setUserPhoto(it.toString())
            }
        }
    }

    override fun onSignOutButtonIsClicked() {
        authentication.signOut()
        profileView.signOut()
    }

    override fun onChoosePhotoButtonIsClicked() {
    }

    override fun onUploadUserImage(data:ByteArray) {
    }

}