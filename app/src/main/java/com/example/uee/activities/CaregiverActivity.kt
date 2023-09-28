package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uee.fragments.CaregiverSettingsFragment
import com.example.uee.R
import com.example.uee.dataClasses.ChatUser
import com.example.uee.databinding.ActivityCaregiverBinding
import com.example.uee.fragments.CaregiverProfileFragment
import com.example.uee.fragments.ChannelListFragment

class CaregiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaregiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaregiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCaregiverProfileFragment()

        binding.bottomAppBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    setCaregiverProfileFragment()
                    true
                }

                R.id.settings -> {
                    setCurrentFragment(CaregiverSettingsFragment())
                    true
                }

                R.id.chats -> {
                    setCurrentFragment(ChannelListFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {

        val args = Bundle()
        args.putParcelable("chatUser", ChatUser("ila2", "null"))
        fragment.arguments = args

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit()
    }

    private fun setCaregiverProfileFragment() {

        val caregiverProfileFragment = CaregiverProfileFragment()
        val args = Bundle()
        args.putParcelable("chatUser", ChatUser("ila2", "null"))
        caregiverProfileFragment.arguments = args

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, CaregiverProfileFragment()).commit()
    }
}