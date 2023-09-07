package com.example.phinconattendance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LogDay(
    var item_Image: Long,
    var item_namePlace: String,
    var item_address: String,
    var item_status: String,
    var item_time: String
) : Parcelable