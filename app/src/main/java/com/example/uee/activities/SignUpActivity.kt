package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.uee.R


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)




        val items = listOf("Client", "Caregiver")
        val adapter = ArrayAdapter(this, R.layout.signup_item, items)
        val textField = findViewById<AutoCompleteTextView>(R.id.textField)
        textField.setAdapter(adapter)


    }
}