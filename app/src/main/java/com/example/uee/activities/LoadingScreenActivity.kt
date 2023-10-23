package com.example.uee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uee.R

class LoadingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)


        //Loading first Loading page
        val loadFirstLoading = Intent(this,FirstLoadingActivity::class.java)
        startActivity(loadFirstLoading)

    }
}