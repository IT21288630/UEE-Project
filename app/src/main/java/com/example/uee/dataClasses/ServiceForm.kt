package com.example.uee.dataClasses

data class ServiceForm(
    val Caregiver:String,
    val client:String,
    val Pname:String,
    val Gender:String,
    val startdate: String,
    val enddate: String?, // Nullable for the second form
    val starttime: String,
    val endtime: String?, // Nullable for the first form
    val servicehours: Int?, // Nullable for the second form
    val servicedays: String?

)
