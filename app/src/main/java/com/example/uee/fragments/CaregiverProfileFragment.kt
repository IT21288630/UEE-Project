package com.example.uee.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uee.dataClasses.Caregiver
import com.example.uee.databinding.FragmentCaregiverProfileBinding
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CaregiverProfileFragment : Fragment() {

    private lateinit var binding: FragmentCaregiverProfileBinding
    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")
    private lateinit var caregiver: Caregiver
    private lateinit var caregiverUsername: String
    private lateinit var tvName: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvAbout: TextView
    private lateinit var tvHourlyRate: TextView
    private lateinit var tvShortNotice: TextView
    private lateinit var tvSleeping: TextView
    private lateinit var tvWaking: TextView
    private lateinit var tvPartTime: TextView
    private lateinit var tvFullTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaregiverProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        caregiverUsername = args?.getString("caregiverUsername")!!

        tvName = binding.tvName
        tvAge = binding.tvAge
        tvLocation = binding.tvLocation
        tvAbout = binding.tvAbout
        tvHourlyRate = binding.tvHourlyRate
        tvShortNotice = binding.tvShortNotice
        tvSleeping = binding.tvSleeping
        tvWaking = binding.tvWaking
        tvPartTime = binding.tvPartTime
        tvFullTime = binding.tvFullTime

        getCaregiver()
    }

    private fun getCaregiver() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot =
                caregiverCollectionRef.whereEqualTo("username", caregiverUsername).get().await()

            for (document in querySnapshot.documents) {
                caregiver = document.toObject<Caregiver>()!!
            }

            withContext(Dispatchers.Main) {
                setCaregiver()
            }

        } catch (e: Exception) {
            Log.d("CaregiverRegister1Fragment", e.toString())
        }
    }

    private fun setCaregiver() {
        tvName.text = caregiver.name
        tvAge.text = "Age: ${caregiver.age}"
        tvLocation.text = caregiver.location
        tvAbout.text = caregiver.about
        tvHourlyRate.text = "Rs${caregiver.daily}"
        tvShortNotice.text = "Rs${caregiver.shortNotice}"
        tvSleeping.text = "Rs${caregiver.sleeping}"
        tvWaking.text = "Rs${caregiver.waking}"
        tvPartTime.text = "Rs${caregiver.partTime}"
        tvFullTime.text = "Rs${caregiver.fullTime}"

        var user = User(id = caregiver.username!!, name = caregiver.name!!)

        if (caregiver.image != null) {
            user.image = caregiver.image!!
        }

        binding.avatarView.setUserData(user)
    }
}