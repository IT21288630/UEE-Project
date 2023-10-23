package com.example.uee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import com.example.uee.R


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Val initiations -------------------------------------------------------------------------
        val signUpBtn = findViewById<Button>(R.id.BtnSignUp)
        val goToLoginText = findViewById<TextView>(R.id.goToLoginTxt)

        val items = listOf("Client", "Caregiver")
        val adapter = ArrayAdapter(this, R.layout.signup_item, items)
        val textField = findViewById<AutoCompleteTextView>(R.id.textField)
        textField.setAdapter(adapter)



        // Click listner actions ------------------------------------------------------------------
        signUpBtn.setOnClickListener(){

            val intentSignUp = Intent(this,ClientRegistrationActivity::class.java)
            startActivity(intentSignUp)
        }

        goToLoginText.setOnClickListener(){
            val intentGoToLogin = Intent(this,LoginActivity::class.java)
            startActivity(intentGoToLogin)
        }

    }
}