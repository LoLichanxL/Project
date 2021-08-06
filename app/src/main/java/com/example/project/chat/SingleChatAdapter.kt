package com.example.project.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.model.Authentication
import com.example.project.model.Message
import java.text.SimpleDateFormat
import java.util.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessagesCache = emptyList<Message>()


    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blocUserMessage: ConstraintLayout = view.findViewById(R.id.bloc_out)
        val chatUserMessage: TextView = view.findViewById(R.id.user_message_out)
        val chatUserMessageTime: TextView = view.findViewById(R.id.time_message_out)

        val blocReceivedMessage: ConstraintLayout = view.findViewById(R.id.bloc_in)
        val chatReceivedMessage: TextView = view.findViewById(R.id.user_message_in)
        val chatReceivedMessageTime: TextView = view.findViewById(R.id.time_message_in)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatAdapter.SingleChatHolder, position: Int) {
        if (mListMessagesCache[position].sentBy == Authentication().getUserID()) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text = mListMessagesCache[position].time.toString().asTime()
        } else{
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mListMessagesCache[position].text
            holder.chatReceivedMessageTime.text = mListMessagesCache[position].time.toString().asTime()
        }
    }

    override fun getItemCount(): Int = mListMessagesCache.size

    fun setList(list: List<Message>) {
        mListMessagesCache = list
        notifyDataSetChanged()
    }

}

private fun String.asTime(): String {
    val time = Date(this.toLong())
    val TimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return TimeFormat.format(time)
}
