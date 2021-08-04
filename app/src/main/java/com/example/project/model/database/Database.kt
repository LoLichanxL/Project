package com.example.project.model.database

import android.net.Uri
import com.example.project.model.Advert
import com.example.project.model.Chat
import com.example.project.model.Message
import com.example.project.model.User
import com.example.project.presenter.HomePresenter
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*
import java.util.Collections.emptyList

class Database {
    companion object{
        private val database = Firebase.database("https://project-24d51-default-rtdb.europe-west1.firebasedatabase.app/")
        val storage = Firebase.storage
        fun addUser(user: User){
            database.reference.child("Users").child(user.uid).setValue(user).addOnSuccessListener {
            }.addOnFailureListener {  }
        }

        fun addChat(chat: Chat, firstUID:String, secondUID:String){
            database.reference.child("Chats").child(chat.chatID).child("members").setValue(listOf(firstUID, secondUID))
        }
        fun addUserChat(UID:String, chatID:String){
            database.reference.child("UserChats").child(UID).get().addOnSuccessListener {
                if (it.getValue() == null){
                    database.reference.child("UserChats").child(UID).setValue(listOf(chatID))
                }
                else {
                    val list: ArrayList<String> = it.getValue() as ArrayList<String>
                    list.add(chatID)
                    database.reference.child("UserChats").child(UID).setValue(list)
                }
            }.addOnFailureListener {
            }
        }
        fun addChatMessage(chatID: String, message: Message){
            database.reference.child("ChatMessages").child(chatID).child("messages").get().addOnSuccessListener {
                if (it.getValue() == null){
                    database.reference.child("ChatMessages").child(chatID).child("messages").setValue(
                        listOf(message))
                    database.reference.child("Chats").child(chatID).child("lastMessage").setValue(message)
                }else{
                    var list:ArrayList<Message> = it.getValue() as ArrayList<Message>
                    list.add(message)
                    database.reference.child("ChatMessages").child(chatID).child("messages").setValue(list)
                    database.reference.child("Chats").child(chatID).child("lastMessage").setValue(message)
                }
            }

        }

        fun getUserChats(UID:String){
            var list:List<Chat> = emptyList()
            database.reference.child("UserChats").child(UID).get().addOnSuccessListener {
                list = it.getValue() as List<Chat>
            }
        }
        fun getChatMessages(chatID: String){
            var list:List<Message> = emptyList()
            database.reference.child("ChatMessages").child(chatID).child("messages").get().addOnSuccessListener {
                list = it.getValue() as List<Message>
            }
        }
        fun addAdvert(advert: Advert){
            database.reference.child("Adverts").get().addOnSuccessListener {
                if (it.getValue() == null){
                    database.reference.child("Adverts").setValue(listOf(advert))
                }
                else{
                    var list:ArrayList<Advert> = it.getValue() as ArrayList<Advert>
                    list.add(advert)
                    database.reference.child("Adverts").setValue(list)
                }
            }
        }
        fun getAdverts(presenter: HomePresenter){
            database.reference.child("Adverts").get().addOnSuccessListener {
                if (it.getValue() != null) {
                    var list = it.getValue() as List<HashMap<String, Object>>
                    presenter.onDatabaseIsUploadAdverts(list)
                }
                else{
                    presenter.onDatabaseIsUploadAdverts(emptyList())
                }
            }
        }
        fun uploadAdvertImage(advertID: String, count:Int, uri: String){
            if (count == 1) {
                val uploadTask = storage.reference.child("adverts/" + advertID + "/firstImage.jpg").putFile(
                    Uri.parse(uri))
            }
            if (count == 2) {
                val uploadTask = storage.reference.child("adverts/" + advertID + "/secondImage.jpg").putFile(
                    Uri.parse(uri))            }
            if (count == 3) {
                val uploadTask = storage.reference.child("adverts/" + advertID + "/thirdImage.jpg").putFile(
                    Uri.parse(uri))            }
        }
        fun uploadImageAdvertImageFromImageView(advertID: String, count: Int, data:ByteArray){
            if (count == 1) {
                var uploadTask = storage.reference.child("adverts/" + advertID + "/firstImage.jpg").putBytes(data)
            }
            if (count == 2) {
                var uploadTask = storage.reference.child("adverts/" + advertID + "/secondImage.jpg").putBytes(data) }
            if (count == 3) {
                var uploadTask = storage.reference.child("adverts/" + advertID + "/thirdImage.jpg").putBytes(data)
            }
    }

}
}