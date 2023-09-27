package com.example.uee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uee.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val sharedPref = getSharedPreferences("appPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val btnCareGiver: Button = findViewById(R.id.btnCaregiver)

        btnCareGiver.setOnClickListener {
            editor.apply {
                putString("userType", "caregiver")
                apply()
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }*/
        val btnCareGiver: Button = findViewById(R.id.btnCaregiver)

        btnCareGiver.setOnClickListener{
            val intent = Intent(this, CaregiverActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}