package com.example.uee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uee.dataClasses.Client
import com.example.uee.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchToCaregiverActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var caregiverUsername: String
    private lateinit var usertype: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_to_caregiver)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_new)

        //Getting shared pref details
        val clientData: Client? = intent.getParcelableExtra("clientData")
        val sharedPref = getSharedPreferences("appPref", MODE_PRIVATE)
        username = sharedPref.getString("userName", null)!!
        caregiverUsername = intent.getStringExtra("caregiverUsername")!!
        usertype = intent.getStringExtra("usertype")!!

        //Setting default fragment
        if (clientData != null) {
            setCurrentFragment(ClientProfileFragment(), clientData)

        }

        setCurrentFragment(CaregiverProfileFragment(), null)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    // Respond to navigation item 1 click
                    if (clientData != null) {
                        setCurrentFragment(ClientProfileFragment(), clientData)
                    }
                    true
                }

                R.id.page_2 -> {
                    // Respond to navigation item 1 click
                    if (clientData != null) {
                        setCurrentFragment(MyFavoritesFragment(), clientData)
                    }
                    true
                }

                R.id.page_3 -> {
                    // Respond to navigation item 1 click
                    setCurrentFragment(ClientChannelListFragment(), null)
                    true
                }

                R.id.page_4 -> {
                    // Respond to navigation item 1 click
                    if (clientData != null) {
                        setCurrentFragment(ClientSettingsPageFragment(), clientData)
                    }
                    true
                }

                R.id.page_5 -> {
                    // Respond to navigation item 1 click
                    if (clientData != null) {
                        setCurrentFragment(SearchFragment(), clientData)
                    }
                    true
                }

                else -> false
            }

        }
    }

    private fun setCurrentFragment(Myfragment: Fragment, clientData: Client?) {


        val fragment = Myfragment

        val args = Bundle()
        args.putParcelable("clientData", clientData)
        args.putString("clientUsername", username)
        args.putString("caregiverUsername", caregiverUsername)
        args.putString("usertype", usertype)
        fragment.arguments = args



        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, Myfragment)
            commit()
        }


    }
}