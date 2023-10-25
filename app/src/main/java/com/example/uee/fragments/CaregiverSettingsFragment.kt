package com.example.uee.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.uee.dataClasses.Caregiver
import com.example.uee.databinding.FragmentCaregiverSettingsBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.util.*

class CaregiverSettingsFragment : Fragment() {

    private lateinit var binding: FragmentCaregiverSettingsBinding
    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var caregiverUsername: String
    private lateinit var caregiver: Caregiver
    private lateinit var nameLayout: TextInputLayout
    private lateinit var ageLayout: TextInputLayout
    private lateinit var locationLayout: TextInputLayout
    private lateinit var aboutLayout: TextInputLayout
    private lateinit var hourlyRateLayout: TextInputLayout
    private lateinit var shortNoticeLayout: TextInputLayout
    private lateinit var sleepingLayout: TextInputLayout
    private lateinit var wakingLayout: TextInputLayout
    private lateinit var partTimeLayout: TextInputLayout
    private lateinit var fullTimeLayout: TextInputLayout
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaregiverSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        caregiverUsername = args?.getString("caregiverUsername")!!

        nameLayout = binding.nameLayout
        ageLayout = binding.ageLayout
        locationLayout = binding.locationLayout
        aboutLayout = binding.aboutLayout
        hourlyRateLayout = binding.hourlyRateLayout
        shortNoticeLayout = binding.shortNoticeLayout
        sleepingLayout = binding.sleepingLayout
        wakingLayout = binding.wakingLayout
        partTimeLayout = binding.partTimeLayout
        fullTimeLayout = binding.fullTimeLayout

        getCaregiver()

        var layoutList = listOf(
            nameLayout,
            ageLayout,
            locationLayout,
            aboutLayout,
            hourlyRateLayout,
            shortNoticeLayout,
            sleepingLayout,
            wakingLayout,
            partTimeLayout,
            fullTimeLayout
        )

        for (layout in layoutList) {
            layout.isEndIconVisible = false
            layout.editText?.doOnTextChanged { text, start, before, count ->
                makeEndIconVisible(layout)
            }
            layout.editText?.setOnFocusChangeListener { _, _ ->
                layout.isEndIconVisible = false
            }
            layout.setEndIconOnClickListener {
                updateField(layout)
            }
        }

        binding.ivImageIcon.setOnClickListener {
            choosePicture()
        }

        binding.uploadBtn.setOnClickListener {
            uploadImage()
            binding.uploadBtn.isGone = true
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun makeEndIconVisible(layout: TextInputLayout) {
        layout.isEndIconVisible = true
    }

    private fun updateField(layout: TextInputLayout) = CoroutineScope(Dispatchers.IO).launch {

        try {
            val querySnapshot =
                caregiverCollectionRef.whereEqualTo("username", caregiver.username).get().await()

            for (document in querySnapshot.documents) {
                when (layout.tag.toString()) {
                    "nameLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("name", layout.editText?.text.toString())
                        caregiver.name = layout.editText?.text.toString()
                    }
                    "ageLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("age", layout.editText?.text.toString())
                        caregiver.age = layout.editText?.text.toString()
                    }
                    "locationLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("location", layout.editText?.text.toString())
                        caregiver.location = layout.editText?.text.toString()
                    }
                    "aboutLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("about", layout.editText?.text.toString())
                        caregiver.about = layout.editText?.text.toString()
                    }
                    "hourlyRateLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("daily", layout.editText?.text.toString())
                        caregiver.daily = layout.editText?.text.toString()
                    }
                    "shortNoticeLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("shortNotice", layout.editText?.text.toString())
                        caregiver.shortNotice = layout.editText?.text.toString()
                    }
                    "sleepingLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("sleeping", layout.editText?.text.toString())
                        caregiver.sleeping = layout.editText?.text.toString()
                    }
                    "wakingLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("waking", layout.editText?.text.toString())
                        caregiver.waking = layout.editText?.text.toString()
                    }
                    "partTimeLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("partTime", layout.editText?.text.toString())
                        caregiver.partTime = layout.editText?.text.toString()
                    }
                    "fullTimeLayout" -> {
                        caregiverCollectionRef.document(document.id)
                            .update("fullTime", layout.editText?.text.toString())
                        caregiver.fullTime = layout.editText?.text.toString()
                    }
                }
            }

            withContext(Dispatchers.Main) {
                updateHint(layout, layout.editText?.text.toString())
                if (layout.tag.toString() != "aboutLayout") layout.editText?.text = null
            }

        } catch (e: Exception) {
            Log.d("CaregiverSettingsFragment", e.toString())
        }

    }

    private fun updateHint(layout: TextInputLayout, hint: String) {
        layout.hint = hint
    }

    private fun getCaregiver() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot =
                caregiverCollectionRef.whereEqualTo("username", caregiverUsername).get().await()

            for (document in querySnapshot.documents) {
                caregiver = document.toObject<Caregiver>()!!
            }

            withContext(Dispatchers.Main) {
                setData()
            }

        } catch (e: Exception) {
            Log.d("CaregiverRegister1Fragment", e.toString())
        }
    }

    private fun setData() {
        nameLayout.hint = caregiver.name
        ageLayout.hint = caregiver.age
        locationLayout.hint = caregiver.location
        aboutLayout.editText?.setText(caregiver.about)
        hourlyRateLayout.hint = "Rs${caregiver.daily} per hour"
        shortNoticeLayout.hint = "Rs${caregiver.shortNotice} per hour"
        sleepingLayout.hint = "Rs${caregiver.sleeping} per hour"
        wakingLayout.hint = "Rs${caregiver.waking} per hour"
        partTimeLayout.hint = "Rs${caregiver.partTime} per hour"
        fullTimeLayout.hint = "Rs${caregiver.fullTime} per hour"

        var user = User(id = caregiver.username!!, name = caregiver.name!!)

        if (caregiver.image != null) {
            user.image = caregiver.image!!
        }

        binding.avatarView.setUserData(user)
    }

    private fun choosePicture() {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.uploadBtn.isGone = false
        }
    }

    private fun uploadImage() {
        val key = UUID.randomUUID().toString()

        firebaseStorage = FirebaseStorage.getInstance()
        storageRef = firebaseStorage.reference

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

                    val user = User(image = caregiver.image!!)
                    binding.avatarView.setUserData(user)

                    CoroutineScope(Dispatchers.IO).launch {
                        val querySnapshot =
                            caregiverCollectionRef.whereEqualTo("username", caregiver.username)
                                .get().await()

                        for (document in querySnapshot.documents) {
                            caregiverCollectionRef.document(document.id)
                                .update("image", caregiver.image)
                        }
                    }
                }
            }
        }
    }
}