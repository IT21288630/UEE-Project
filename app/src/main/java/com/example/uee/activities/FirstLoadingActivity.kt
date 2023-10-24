package com.example.uee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.uee.R

class FirstLoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_loading)

        val signUPBtn = findViewById<Button>(R.id.btnSignUpIniti)
        val loginBtn = findViewById<Button>(R.id.btnLoginIniti)

        signUPBtn.setOnClickListener(){
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
        loginBtn.setOnClickListener(){
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}