package com.example.phinconattendance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val dataLiveData = MutableLiveData<String>()
}