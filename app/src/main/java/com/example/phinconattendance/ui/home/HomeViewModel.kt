package com.example.phinconattendance.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.Location

class HomeViewModel : ViewModel() {

    var buttonState: Boolean = false
        get() = field
        set(value)  {
            field = value
        }

    private val _selectedLocation = MutableLiveData<Location?>()
    val selectedLocation: LiveData<Location?>
        get() = _selectedLocation

    private val _namePlace = MutableLiveData<String>()
    val namePlace: LiveData<String>
        get() = _namePlace

    private val _image = MutableLiveData<Int>()
    val image: LiveData<Int>
        get() = _image

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    fun setSelectedLocation(location: Location?) {
        _selectedLocation.value = location
        _namePlace.value = location?.item_namePlace
        _image.value = location?.item_Image
        _address.value = location?.item_address
    }

}