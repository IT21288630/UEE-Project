package com.example.uee.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import com.example.uee.R
import com.example.uee.activities.LoginActivity

/**
 * A simple [Fragment] subclass.
 * Use the [ClientRegistration2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientRegistration2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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


        //Done button action
         doneBtn.setOnClickListener(){

             val intent = Intent(requireContext(), LoginActivity::class.java)
             startActivity(intent)
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