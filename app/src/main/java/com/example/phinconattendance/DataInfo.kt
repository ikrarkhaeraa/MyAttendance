package com.example.phinconattendance

import com.google.firebase.firestore.ServerTimestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date


class DataInfo {

    var dataInfoAddress: String? = null
    var dataInfoDate: Long? = null
    var dataInfoImage: Int? = null
    var dataInfoNamePlace: String? = null
    var dataInfoStatus: String? = null
    var dataInfoTime: String? = null


    constructor() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    constructor(dataInfoAddress: String?, dataInfoDate: Long?, dataInfoImage: Int?, dataInfoNamePlace: String?, dataInfoStatus: String?, dataInfoTime: String?) {
        this.dataInfoAddress = dataInfoAddress
        this.dataInfoDate = dataInfoDate
        this.dataInfoImage = dataInfoImage
        this.dataInfoNamePlace = dataInfoNamePlace
        this.dataInfoStatus = dataInfoStatus
        this.dataInfoTime = dataInfoTime
    }

}