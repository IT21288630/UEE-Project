package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uee.R
import com.example.uee.databinding.ActivityCaregiverRegisterBinding
import com.example.uee.fragments.CaregiverProfileFragment
import com.example.uee.fragments.CaregiverRegister1Fragment

class CaregiverRegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityCaregiverRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaregiverRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, CaregiverRegister1Fragment()).commit()
    }
}