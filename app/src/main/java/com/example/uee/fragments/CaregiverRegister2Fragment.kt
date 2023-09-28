package com.example.uee.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uee.CaregiverRegister3Fragment
import com.example.uee.databinding.FragmentCaregiverRegister1Binding
import com.example.uee.databinding.FragmentCaregiverRegister2Binding
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis

class CaregiverRegister2Fragment : Fragment() {

    lateinit var binding: FragmentCaregiverRegister2Binding

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
        binding = FragmentCaregiverRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    ((view as ViewGroup).parent as View).id,
                    CaregiverRegister3Fragment(),
                    "CaregiverRegister3Fragment"
                )
                .addToBackStack(null)
                .commit()
        }
    }
}