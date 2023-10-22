package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.uee.R
import com.example.uee.fragments.DayServiceFragment
import com.example.uee.fragments.HourlyServiceFragment

class RequestServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_service)
        val fragmentManager: FragmentManager = supportFragmentManager

        val Hservice= findViewById<Button>(R.id.hour)
        val Dservice= findViewById<Button>(R.id.day)



        if (savedInstanceState == null){
            val fragment = HourlyServiceFragment()
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.form, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }



        Hservice.setOnClickListener {
            val fragment = HourlyServiceFragment()
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.form, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        Dservice.setOnClickListener {
            val fragment = DayServiceFragment()
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.form, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }
}