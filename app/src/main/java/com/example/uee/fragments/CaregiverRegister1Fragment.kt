package com.example.uee.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.uee.dataClasses.Caregiver
import com.example.uee.dataClasses.ChatUser
import com.example.uee.databinding.FragmentCaregiverRegister1Binding
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CaregiverRegister1Fragment : Fragment() {

    private lateinit var binding: FragmentCaregiverRegister1Binding
    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")
    private lateinit var userNameLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward = */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward = */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCaregiverRegister1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameLayout = binding.userNameLayout
        passwordLayout = binding.passwordLayout
        confirmPasswordLayout = binding.confirmPasswordLayout

        userNameLayout.error = null
        passwordLayout.error = null
        confirmPasswordLayout.error = null

        userNameLayout.editText?.setOnFocusChangeListener { _, _ ->
            userNameLayout.error = null
            if (userNameLayout.editText?.text.toString().isNotEmpty()) {
                checkUsername(userNameLayout.editText?.text.toString())
            }
        }

        passwordLayout.editText?.setOnFocusChangeListener { _, _ ->
            passwordLayout.error = null
        }

        confirmPasswordLayout.editText?.setOnFocusChangeListener { _, _ ->
            confirmPasswordLayout.error = null
            if (confirmPasswordLayout.editText?.text.toString().isNotEmpty()) {
                checkPasswords(
                    passwordLayout.editText?.text.toString(),
                    confirmPasswordLayout.editText?.text.toString()
                )
            }
        }

        binding.btnNext.setOnClickListener {
            if (userNameLayout.editText?.text.toString().isEmpty()) {
                userNameLayout.error = "Username is empty"
                return@setOnClickListener
            }
            if (passwordLayout.editText?.text.toString().isEmpty()) {
                passwordLayout.error = "Password is empty"
                return@setOnClickListener
            }
            if (confirmPasswordLayout.editText?.text.toString().isEmpty()) {
                confirmPasswordLayout.error = "Confirm password is empty"
                return@setOnClickListener
            }
            if (!checkPasswords(
                    passwordLayout.editText?.text.toString(),
                    confirmPasswordLayout.editText?.text.toString()
                )
            ) {
                return@setOnClickListener
            }

            val caregiver = Caregiver(
                username = userNameLayout.editText?.text.toString(),
                password = passwordLayout.editText?.text.toString()
            )

            val caregiverRegister2Fragment = CaregiverRegister2Fragment()
            val args = Bundle()
            args.putParcelable("caregiver", caregiver)
            caregiverRegister2Fragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction().replace(
                ((view as ViewGroup).parent as View).id,
                caregiverRegister2Fragment,
                "CaregiverRegister2Fragment"
            ).addToBackStack(null).commit()
        }
    }

    private fun checkUsername(username: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot =
                caregiverCollectionRef.whereEqualTo("username", username).get().await()

            if (querySnapshot.size() != 0) {
                withContext(Dispatchers.Main) {
                    userNameLayout.error = "Username already exists"
                }
            }

        } catch (e: Exception) {
            Log.d("CaregiverRegister1Fragment", e.toString())
        }
    }

    private fun checkPasswords(password: String, confirmPassword: String): Boolean {
        if (password != confirmPassword) {
            confirmPasswordLayout.error = "Password and Confirm password should match"
            return false
        }
        return true
    }
}