package com.example.uee.dataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(

    var about: String? = null,
    var age: String? = null,
    var contactNo: String? = null,
    var email: String? = null,
    var location: String? = null,
    var name: String? = null,
    var proPic: String? = null,
    var psw: String? = null,
    var userName: String? = null,
    var userType: String? = null,
) : Parcelable
