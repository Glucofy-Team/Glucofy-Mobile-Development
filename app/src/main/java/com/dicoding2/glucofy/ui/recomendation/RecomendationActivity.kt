package com.dicoding2.glucofy.ui.recomendation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.adapter.ChatHistoryAdapter
import com.dicoding2.glucofy.data.local.entity.RecomendationEntity
import com.dicoding2.glucofy.databinding.ActivityRecomendationBinding

class RecomendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecomendationBinding
    private lateinit var chatHistoryAdapter: ChatHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)

        chatHistoryAdapter = ChatHistoryAdapter()
        val historyLayoutManager = LinearLayoutManager(this)

        binding.rvChatHistory.layoutManager = historyLayoutManager
        binding.rvChatHistory.adapter = chatHistoryAdapter

        binding.btnSend.setOnClickListener {
            val text = binding.tvInputUser.text.toString()

            chatHistoryAdapter.addMessage(RecomendationEntity(text,true))

            chatHistoryAdapter.addMessage(RecomendationEntity("Sabar bre",false))
        }


        setContentView(binding.root)
    }
}