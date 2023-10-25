package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uee.R

class FilteredCaregiverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_caregiver)

        val FilterValue = intent.getStringExtra("selectedItem")
    }
}