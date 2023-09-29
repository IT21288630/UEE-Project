package com.example.uee.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.uee.activities.CaregiverActivity
import com.example.uee.dataClasses.Caregiver
import com.example.uee.databinding.FragmentCaregiverRegister3Binding
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CaregiverRegister3Fragment : Fragment() {

    private lateinit var binding: FragmentCaregiverRegister3Binding
    private lateinit var caregiver: Caregiver
    private lateinit var hourlyRateLayout: TextInputLayout
    private lateinit var shortNoticeLayout: TextInputLayout
    private lateinit var sleepingLayout: TextInputLayout
    private lateinit var wakingLayout: TextInputLayout
    private lateinit var partTimeLayout: TextInputLayout
    private lateinit var fullTimeLayout: TextInputLayout
    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward = */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward = */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCaregiverRegister3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hourlyRateLayout = binding.hourlyRateLayout
        shortNoticeLayout = binding.shortNoticeLayout
        sleepingLayout = binding.sleepingLayout
        wakingLayout = binding.wakingLayout
        partTimeLayout = binding.partTimeLayout
        fullTimeLayout = binding.fullTimeLayout

        val args = arguments
        caregiver = args?.getParcelable("caregiver")!!

        hourlyRateLayout.error = null
        shortNoticeLayout.error = null
        sleepingLayout.error = null
        wakingLayout.error = null
        partTimeLayout.error = null
        fullTimeLayout.error = null

        binding.btnDone.setOnClickListener {
            if (hourlyRateLayout.editText?.text.toString().isEmpty()) {
                hourlyRateLayout.error = "Hourly rate is empty"
                return@setOnClickListener
            }
            if (shortNoticeLayout.editText?.text.toString().isEmpty()) {
                shortNoticeLayout.error = "Short notice hourly rate is empty"
                return@setOnClickListener
            }
            if (sleepingLayout.editText?.text.toString().isEmpty()) {
                sleepingLayout.error = "Sleeping is empty"
                return@setOnClickListener
            }
            if (wakingLayout.editText?.text.toString().isEmpty()) {
                wakingLayout.error = "Waking is empty"
                return@setOnClickListener
            }
            if (partTimeLayout.editText?.text.toString().isEmpty()) {
                partTimeLayout.error = "Part time is empty"
                return@setOnClickListener
            }
            if (fullTimeLayout.editText?.text.toString().isEmpty()) {
                fullTimeLayout.error = "Full time is empty"
                return@setOnClickListener
            }

            caregiver.daily = hourlyRateLayout.editText?.text.toString()
            caregiver.shortNotice = shortNoticeLayout.editText?.text.toString()
            caregiver.sleeping = sleepingLayout.editText?.text.toString()
            caregiver.waking = wakingLayout.editText?.text.toString()
            caregiver.partTime = partTimeLayout.editText?.text.toString()
            caregiver.fullTime = fullTimeLayout.editText?.text.toString()

            registerCaregiver()

            val intent = Intent(requireContext(), CaregiverActivity::class.java)
            intent.putExtra("caregiver", caregiver)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun registerCaregiver() = CoroutineScope(Dispatchers.IO).launch {

        try {
            caregiverCollectionRef.add(caregiver).await()

        } catch (e: Exception) {
            Log.d("CaregiverRegister3Fragment", e.toString())
        }
    }
}