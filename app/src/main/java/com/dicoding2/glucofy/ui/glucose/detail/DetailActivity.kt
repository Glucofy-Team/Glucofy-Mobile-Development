package com.dicoding2.glucofy.ui.glucose.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.ActivityDetailBinding
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import com.dicoding2.glucofy.ui.glucose.today.GlucosaTodayViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        detailViewModel = obtainViewModel(this)

        val id = intent.getStringExtra("id")
        val glucose = intent.getStringExtra("glucose")
        val condition = intent.getStringExtra("condition")
        val note = intent.getStringExtra("note")
        val date = intent.getStringExtra("date")

        binding.tvGlucose.text = glucose
        binding.tvCondition.text = condition
        binding.tvNote.text = note
        binding.tvDate.text = formatDateString(date.toString())

        binding.btnDelete.setOnClickListener {
            clearGlucoseTables(id.toString())
            finish()
        }


        setContentView(binding.root)
    }

    private fun formatDateString(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        val date = inputFormat.parse(dateString)

        return outputFormat.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun clearGlucoseTables(id: String) {
        lifecycleScope.launch {
            detailViewModel.deleteGlucoseById(id).observe(this@DetailActivity, Observer{

            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(this)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }
}