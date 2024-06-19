package com.dicoding2.glucofy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.local.entity.RecomendationEntity

class ChatHistoryAdapter : RecyclerView.Adapter<ChatHistoryAdapter.MessageViewHolder>() {

    private val chatHistory = ArrayList<RecomendationEntity>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val messageItemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false) as ViewGroup
        return MessageViewHolder(messageItemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val userProfile = holder.itemView.findViewById<ImageView>(R.id.iv_userProfile)
        val userMessageText = holder.itemView.findViewById<TextView>(R.id.tv_userMessageText)

        val message = chatHistory[position]

//        userProfile.setImageDrawable(getProfileIcon(userProfile.context, message.isFromUser))
        userMessageText.text = message.text
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatHistory[position].isFromUser) {
            R.layout.item_message_user
        } else {
            R.layout.item_message_bot
        }
    }

    override fun getItemCount(): Int {
        return chatHistory.size
    }

    fun addMessage(message: RecomendationEntity) {
        chatHistory.add(message)
        notifyDataSetChanged()
    }

}