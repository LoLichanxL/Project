package com.example.project.chat

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.AppValueEventListener

import com.example.project.MainActivity
import com.example.project.R
import com.example.project.databinding.FragmentSingleChatBinding
import com.example.project.model.Authentication
import com.example.project.model.Chat
import com.example.project.model.Message
import com.example.project.model.database.Database
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class SingleChatFragment() : Fragment(R.layout.fragment_single_chat) {
    private lateinit var binding: FragmentSingleChatBinding
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppValueEventListener
    private var mListMessages = emptyList<Message>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleChatBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val uid = arguments?.getString("strArg").toString()
        val chatid = uid.hashCode().toString()
        binding.sendMessageButton.setOnClickListener {
            var message = Message(
                binding.chatInputMessage.text.toString(),
                Authentication().getUserID(),
                System.currentTimeMillis().toString()
            )
            if (message.text.isEmpty()) {
                Toast.makeText(context, "Введите сообщение", Toast.LENGTH_LONG).show()
                Log.d("a", "msg")
            } else {
//                fun chatNotExists(selfUID: String, chat: Chat) {
//                    var list: List<Chat>
//                    Database.database.reference.child("UserChats").child(selfUID).get()
//                        .addOnSuccessListener {
//                            list = it.getValue() as List<Chat>
//                            if (!list.contains(chat)) {
                                Database.addUserChat(Authentication().getUserID(), chatid)
                                Database.addChat(
                                    chat = Chat(chatid),
                                    Authentication().getUserID(),
                                    uid
                                )
                            //}
                        //}
                //}
                Database.addChatMessage(chatid, message)
                mRecyclerView = binding.chatRecyclerView
                mRecyclerView.layoutManager=LinearLayoutManager(context)
                mAdapter = SingleChatAdapter()
                mRefMessages = FirebaseDatabase.getInstance().reference.child("ChatMessages")
                mRecyclerView.adapter = mAdapter
                mMessagesListener = AppValueEventListener { dataSnapshot ->
                    Database.database.reference.child("ChatMessages").child(chatid).child("messages").get()
                        .addOnSuccessListener {
                            mListMessages = it.getValue() as List<Message>
                            mAdapter.setList(mListMessages)
                            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
                        }.addOnFailureListener {println("FAILURE!!!")}
                }
                mRefMessages.addValueEventListener(mMessagesListener)
                binding.chatInputMessage.text = Editable.Factory.getInstance().newEditable("")

            }
        }
    }
}

