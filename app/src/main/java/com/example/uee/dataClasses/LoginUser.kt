package com.example.uee.dataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUser(
    var userName: String? = null,
    var psw : String? = null,
    var userType : String? = null,
) : Parcelable
