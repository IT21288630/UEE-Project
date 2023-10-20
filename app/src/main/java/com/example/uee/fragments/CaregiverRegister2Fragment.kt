package com.example.uee.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import com.example.uee.activities.ChatActivity
import com.example.uee.dataClasses.Caregiver
import com.example.uee.databinding.FragmentCaregiverRegister2Binding
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class CaregiverRegister2Fragment : Fragment() {

    private lateinit var binding: FragmentCaregiverRegister2Binding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var caregiver: Caregiver
    private lateinit var nameLayout: TextInputLayout
    private lateinit var ageLayout: TextInputLayout
    private lateinit var locationLayout: TextInputLayout
    private lateinit var aboutLayout: TextInputLayout
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward = */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward = */ false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCaregiverRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseStorage = FirebaseStorage.getInstance()
        storageRef = firebaseStorage.reference
        val args = arguments
        caregiver = args?.getParcelable("caregiver")!!

        nameLayout = binding.nameLayout
        ageLayout = binding.ageLayout
        locationLayout = binding.locationLayout
        aboutLayout = binding.aboutLayout

        nameLayout.error = null
        ageLayout.error = null
        locationLayout.error = null
        aboutLayout.error = null

        nameLayout.editText?.setOnFocusChangeListener { _, _ ->
            nameLayout.error = null
        }

        ageLayout.editText?.setOnFocusChangeListener { _, _ ->
            ageLayout.error = null
        }

        locationLayout.editText?.setOnFocusChangeListener { _, _ ->
            locationLayout.error = null
        }

        aboutLayout.editText?.setOnFocusChangeListener { _, _ ->
            aboutLayout.error = null
        }

        binding.ivImageIcon.setOnClickListener {
            choosePicture()
        }

        binding.btnNext.setOnClickListener {
            uploadImage()

            if (nameLayout.editText?.text.toString().isEmpty()) {
                nameLayout.error = "Name is empty"
                return@setOnClickListener
            }
            if (ageLayout.editText?.text.toString().isEmpty()) {
                ageLayout.error = "Age is empty"
                return@setOnClickListener
            }
            if (locationLayout.editText?.text.toString().isEmpty()) {
                locationLayout.error = "Location is empty"
                return@setOnClickListener
            }
            if (aboutLayout.editText?.text.toString().isEmpty()) {
                aboutLayout.error = "About is empty"
                return@setOnClickListener
            }

            caregiver.name = nameLayout.editText?.text.toString()
            caregiver.age = ageLayout.editText?.text.toString()
            caregiver.location = locationLayout.editText?.text.toString()
            caregiver.about = aboutLayout.editText?.text.toString()

            val caregiverRegister3Fragment = CaregiverRegister3Fragment()
            val args = Bundle()
            args.putParcelable("caregiver", caregiver)
            caregiverRegister3Fragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction().replace(
                ((view as ViewGroup).parent as View).id,
                caregiverRegister3Fragment,
                "CaregiverRegister3Fragment"
            ).addToBackStack(null).commit()
        }
    }

    private fun choosePicture() {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.ivImageIcon.isGone = true
            binding.ivProfilePic.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        val key = UUID.randomUUID().toString()

        val profilePicRef = storageRef.child("profile_pics/${caregiver.username}")

        imageUri?.let { uri ->
            profilePicRef.putFile(uri).addOnSuccessListener {
                Log.d("CaregiverRegister2Fragment", "Success uploaded profile pic")
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            }.addOnCompleteListener {
                profilePicRef.downloadUrl.addOnSuccessListener { download ->
                    val imageUrl = download.toString()
                    caregiver.image = imageUrl
                }
            }
        }
    }

}