package com.example.uee.fragments

import android.content.Intent
import android.os.Bundle
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
    val phonePattern = Regex("^\\d{10}\$")


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
        val proPicView = view.findViewById<ImageView>(R.id.imgViewRegClientDP)

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