package com.example.uee.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment

import com.example.uee.R
import com.example.uee.activities.ClientRegistrationActivity
import com.example.uee.activities.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [ClientRegistration2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientRegistration2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var uName : String? = null
    private var uAge : String? = null
    private var uLocation : String? = null
    private var uContact : String? = null
    private var uAbout : String? = null
    private var proPic : String? = null
    private lateinit var proPicView: ImageView
    val phonePattern = Regex("^\\d{10}\$")
    val PICK_IMAGE_PERMISSION = 71


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_registration2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doneBtn = view.findViewById<Button>(R.id.BtnClientRegDone)
        val name = view.findViewById<TextInputLayout>(R.id.regClientNameField)
        val age = view.findViewById<TextInputLayout>(R.id.regClientAgeField)
        val location = view.findViewById<TextInputLayout>(R.id.regClientLocationField)
        val contactNo = view.findViewById<TextInputLayout>(R.id.regClientContactNoField)
        val about = view.findViewById<TextInputLayout>(R.id.regClientAboutField)
        proPicView = view.findViewById(R.id.imgViewRegClientDP)


        // Image selection logic
        proPicView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                } else {
                    val gallery = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI
                    )
                    startActivityForResult(gallery, PICK_IMAGE_PERMISSION)
                }
            } else {
                val gallery = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                )
                startActivityForResult(gallery, PICK_IMAGE_PERMISSION)
            }
        }





        //Done button action
         doneBtn.setOnClickListener(){

             uName = name?.editText?.text.toString()
             uAge = age?.editText?.text.toString()
             uLocation = location?.editText?.text.toString()
            val contc = contactNo?.editText?.text.toString()
             uAbout = about?.editText?.text.toString()

             Log.d("ClientRegistration1Fragment", "doneBtn clicked")

             if (contc.matches(phonePattern)) {
                 // Valid phone number
                 uContact = contc

                 (activity as ClientRegistrationActivity).updateClientDataReg2(uName,uAge,uLocation,uContact,uAbout, proPic)



             } else {
                 // Invalid phone number
                 contactNo?.error = "Please enter a valid 10-digit phone number"
             }




         }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_PERMISSION) {
            val imageUri = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    imageUri
                )
                // Set the bitmap to your ImageView
                proPicView.setImageBitmap(bitmap)

                // Upload the selected image to Firebase Storage
                uploadImageToFirebase(imageUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri?) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imagesRef = storageRef.child("images/${imageUri?.lastPathSegment}")

        val uploadTask = imagesRef.putFile(imageUri!!)

        uploadTask.addOnSuccessListener {
            // Get the download URL of the uploaded image
            imagesRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                // Now you can use this URL to update the ImageView
                proPic = downloadUrl
            }.addOnFailureListener {
                // Handle any errors
                Toast.makeText(activity, "Failed to retrieve download URL.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(activity, "Image upload failed.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientRegistration2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientRegistration2Fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}