package com.example.uee.dataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatUser(
    val username: String?,
    val name: String?,
    val image: String?
) : Parcelable
