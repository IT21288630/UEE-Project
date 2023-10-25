package com.example.uee.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uee.R
import com.example.uee.activities.LoginActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientSettingsPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientSettingsPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_settings_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("appPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val backNav = view.findViewById<TextView>(R.id.viewBackToProfile)
        val yourFav = view.findViewById<Button>(R.id.btnYourFavorites)
        val mypastHirings = view.findViewById<Button>(R.id.btnPastHirings)
        val rateCaregivers = view.findViewById<Button>(R.id.btnRateCareGivers)
        val settingsBtn = view.findViewById<Button>(R.id.btnSettings)
        val logoutBtn = view.findViewById<Button>(R.id.btnLogOut)


        backNav.setOnClickListener(){
            parentFragmentManager.popBackStack()

        }

        yourFav.setOnClickListener(){

            val fragment = MyFavoritesFragment()
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.ClientUIFrag, fragment)
            fragmentTransaction.addToBackStack(null) //
            fragmentTransaction.commit()
        }

        logoutBtn.setOnClickListener(){
            editor.clear()
            editor.commit()
            Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show()

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
         * @return A new instance of fragment ClientSettingsPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientSettingsPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}