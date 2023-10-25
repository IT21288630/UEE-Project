package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uee.R
import com.example.uee.dataClasses.Client
import com.example.uee.fragments.ClientChannelListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.uee.fragments.ClientProfileFragment
import com.example.uee.fragments.ClientSettingsPageFragment
import com.example.uee.fragments.MyFavoritesFragment

class ClientUIActivity : AppCompatActivity() {



    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_uiactivity)




        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        //Getting shared pref details
        val clientData: Client? = intent.getParcelableExtra("clientData")
        val sharedPref = getSharedPreferences("appPref", MODE_PRIVATE)
        username = sharedPref.getString("userName", null)!!




        //Setting default fragment
        if (clientData != null) {
            setCurrentFragment(ClientProfileFragment(), clientData)

        }



        setCurrentFragment(ClientProfileFragment(),null)

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
                        setCurrentFragment(MyFavoritesFragment(),clientData)
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
                        setCurrentFragment(ClientSettingsPageFragment(),clientData)
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
        fragment.arguments = args

        if(clientData != null) {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ClientUIFrag, Myfragment)
                commit()
            }
        }

    }
}