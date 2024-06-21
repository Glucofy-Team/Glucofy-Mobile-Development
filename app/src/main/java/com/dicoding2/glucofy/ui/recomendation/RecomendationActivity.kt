package com.dicoding2.glucofy.ui.recomendation

import android.os.Bundle
import android.view.View
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
    private lateinit var recomendationViewModel: RecomendationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)

        recomendationViewModel = RecomendationViewModel.getInstance(this)

        chatHistoryAdapter = ChatHistoryAdapter()
        val historyLayoutManager = LinearLayoutManager(this)

        binding.rvChatHistory.layoutManager = historyLayoutManager
        binding.rvChatHistory.adapter = chatHistoryAdapter
        recomendationViewModel.isLoading.observe(this){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSend.isEnabled = !it
        }

        chatHistoryAdapter.addMessage(RecomendationEntity("Halo, hari ini ingin makan apa ?",false))

        recomendationViewModel.recomendationFood.observe(this){ recomendationResponse ->
            if (recomendationResponse.status == 200){
                chatHistoryAdapter.addMessage(RecomendationEntity(recomendationResponse.message,false))
                binding.tvInputUser.setText("")
                recomendationViewModel.clearFoodData()
            }
        }

        binding.btnSend.setOnClickListener {
            val text = binding.tvInputUser.text.toString()

            chatHistoryAdapter.addMessage(RecomendationEntity(text,true))
            sendRecomendation(text)
        }


        setContentView(binding.root)
    }

    private fun sendRecomendation(food: String){
        recomendationViewModel.postRecomendation(food)
    }
}