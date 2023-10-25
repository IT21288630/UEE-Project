package com.example.uee.dataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class  favourites(
    var username: String? = null,
    var password: String? = null,
    var name: String? = null,
    var age: String? = null,
    var location: String? = null,
    var about: String? = null,
    var daily: String? = null,
    var shortNotice: String? = null,
    var sleeping: String? = null,
    var waking: String? = null,
    var partTime: String? = null,
    var fullTime: String? = null,
    var image: String? = null
) : Parcelable
