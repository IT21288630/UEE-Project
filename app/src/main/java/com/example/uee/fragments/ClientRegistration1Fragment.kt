package com.example.uee.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

import com.example.uee.R
import com.example.uee.activities.ClientRegistrationActivity
import com.example.uee.activities.MainActivity
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass.
 * Use the [ClientRegistration1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientRegistration1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var userName : String? = null
    private var psw : String? = null

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
        return inflater.inflate(R.layout.fragment_client_registration1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ClientRegistration1Fragment", "runs ok")

        // Element initialization
        val nextBtns = view.findViewById<Button>(R.id.BtnClientRegNext1)
        val regClientName = view.findViewById<TextInputLayout>(R.id.regClientUserNameField)
        val regClientPsw = view.findViewById<TextInputLayout>(R.id.regClientPswField)
        val regClientRePsw = view.findViewById<TextInputLayout>(R.id.regClientRePswField)

        nextBtns.setOnClickListener{

            Log.d("ClientRegistration1Fragment", "Next button clicked")
            userName = regClientName?.editText?.text.toString()
            val tempPsw = regClientPsw?.editText?.text.toString()
            val tempRePsw = regClientRePsw?.editText?.text.toString()

            // Validate password length
            if (tempPsw.length < 8) {
                regClientPsw.error = "Password must be at least 8 characters long"
                return@setOnClickListener
            }

            // Check if passwords match
            if (tempPsw == tempRePsw) {
                // Passwords match, proceed with the flow
                psw = tempPsw

                //Sending data to save
                (activity as ClientRegistrationActivity).updateClientDataReg1(userName,psw)

                val fragment = ClientRegistration2Fragment()
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.RegFragContainer, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            } else {
                // Passwords don't match, display error message
                regClientRePsw.error = "Passwords do not match"
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
         * @return A new instance of fragment ClientRegistration1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientRegistration1Fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}