package com.example.phinconattendance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    var item_Image: Int,
    var item_namePlace: String,
    var item_address: String
) : Parcelable
