package com.example.uee.dataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatUser(
    val userName: String,
    val password: String
) : Parcelable
