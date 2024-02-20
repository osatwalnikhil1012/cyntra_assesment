package com.nikhilosatwal.cyntra_assesment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nikhilosatwal.cyntra_assesment.R
import com.nikhilosatwal.cyntra_assesment.models.Device
import com.nikhilosatwal.cyntra_assesment.models.DeviceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class DeviceStatusViewModel(application: Application) : AndroidViewModel(application) {
    val devicesLiveData = MutableLiveData<List<Device>>()

    fun loadDevicesFromJson() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream = getApplication<Application>().resources.openRawResource(R.raw.devices)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(jsonString)
                val gson = Gson()
                val apiResponse = gson.fromJson(jsonString, DeviceResponse::class.java)

                devicesLiveData.postValue(apiResponse.data.devices)
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }
}