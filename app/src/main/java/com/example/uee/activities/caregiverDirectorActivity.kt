package com.example.uee.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.uee.R
import com.example.uee.dataClasses.Caregiver
import com.example.uee.fragments.CaregiverProfileFragment

class caregiverDirectorActivity : AppCompatActivity(){


    private lateinit var caregiver: Caregiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        val careGiverName = findViewById<Button>(R.id.careGiverName)
        val username = careGiverName.text.toString() // Get the username from the button's text




        careGiverName.setOnClickListener {
            // Create an intent to start the CaregiverProfileActivity
            val intent = Intent(this, CaregiverProfileFragment::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }


}