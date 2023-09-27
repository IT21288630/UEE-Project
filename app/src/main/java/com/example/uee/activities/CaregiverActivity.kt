package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uee.CaregiverSettingsFragment
import com.example.uee.R
import com.example.uee.databinding.ActivityCaregiverBinding
import com.example.uee.databinding.FragmentCaregiverProfileBinding
import com.example.uee.fragments.CaregiverProfileFragment

class CaregiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaregiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaregiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(CaregiverProfileFragment())

        binding.bottomAppBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    setCurrentFragment(CaregiverProfileFragment())
                    true
                }

                R.id.settings -> {
                    setCurrentFragment(CaregiverSettingsFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}