package com.example.project.model

import android.content.IntentSender
import java.security.acl.Owner

class Message (
    var text: String,   // Message text
    val sentBy: String, //UID of sender
    val time: String,   // Date of sending
               ) {

}