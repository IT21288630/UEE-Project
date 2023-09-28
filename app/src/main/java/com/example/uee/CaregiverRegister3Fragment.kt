package com.example.uee

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uee.activities.CaregiverActivity
import com.example.uee.databinding.FragmentCaregiverRegister2Binding
import com.example.uee.databinding.FragmentCaregiverRegister3Binding
import com.google.android.material.transition.platform.MaterialSharedAxis

class CaregiverRegister3Fragment : Fragment() {

    lateinit var binding: FragmentCaregiverRegister3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCaregiverRegister3Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDone.setOnClickListener {
            val intent = Intent(requireContext(), CaregiverActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}