package com.dicoding2.glucofy.ui.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.AddGlucosaResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.di.Injection
import com.dicoding2.glucofy.helper.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddGlucosaViewModel(private val context: Context,private val apiService: ApiService) : ViewModel() {
    private val _glucosa = MutableLiveData<AddGlucosaResponse>()
    val glucosa: LiveData<AddGlucosaResponse> = _glucosa

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postGlucosa(glucosa: String, condition: String, notes: String, datetime: String) {
        _isLoading.value = true
        val client = apiService.postGlocosa(glucosa, condition, notes, datetime)
        client.enqueue(object : Callback<AddGlucosaResponse> {
            override fun onResponse(call: Call<AddGlucosaResponse>, response: Response<AddGlucosaResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _glucosa.value = response.body()
                }else{
                    toast(context, "Post failed")
                }
            }
            override fun onFailure(call: Call<AddGlucosaResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    companion object{
        private const val TAG = "AddGlucosaViewModel"
        @Volatile
        private var instance: AddGlucosaViewModel? = null
        fun getInstance(context: Context): AddGlucosaViewModel =
            instance ?: synchronized(this) {
                instance ?: AddGlucosaViewModel(context, Injection.provideApiConfig(context))
            }.also { instance = it }
    }
}