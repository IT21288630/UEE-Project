package com.example.uee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uee.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.uee.fragments.ClientProfileFragment
import com.example.uee.fragments.ClientSettingsPageFragment
import com.example.uee.fragments.MyFavoritesFragment

class ClientUIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_uiactivity)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        setCurrentFragment(ClientProfileFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    // Respond to navigation item 1 click
                    setCurrentFragment(ClientProfileFragment())
                    true
                }

                R.id.page_2 -> {
                    // Respond to navigation item 1 click
                    setCurrentFragment(MyFavoritesFragment())
                    true
                }

                R.id.page_3 -> {
                    // Respond to navigation item 1 click
                    val intent = Intent(this,ChatActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.page_4 -> {
                    // Respond to navigation item 1 click
                    setCurrentFragment(ClientSettingsPageFragment())
                    true
                }

                else -> false
            }

        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.ClientUIFrag, fragment)
            commit()
        }
    }
}