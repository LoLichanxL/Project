package com.example.project.model

import android.content.IntentSender
import java.security.acl.Owner

class Message (val sentBy: String, //UID of sender
               val text: String,   // Message text
               val date: String,   // Date of sending
               var isChecked:Boolean = false // Is message is checked
               ) {

}