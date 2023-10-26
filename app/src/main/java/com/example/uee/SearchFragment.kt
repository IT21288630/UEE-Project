package com.example.uee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uee.adapters.careGiverListAdpater
import com.example.uee.dataClasses.Caregiver
import com.example.uee.dataClasses.Client
import com.example.uee.databinding.FragmentCaregiverProfileBinding
import com.example.uee.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchRecyclerView: RecyclerView

    private lateinit var searchView: SearchView
    private lateinit var caregiver: Caregiver
    private lateinit var client: Client

    private var mList = ArrayList<Caregiver>()
    private lateinit var adapter: careGiverListAdpater
    // Initialize Firebase Firestore reference
    private val caregiverCollectionRef = FirebaseFirestore.getInstance().collection("Caregivers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = requireArguments().getParcelable("clientData")!!

        searchRecyclerView = view.findViewById(R.id.searchRecyclerView)

        searchView = view.findViewById(R.id.searchView)
        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadCaregiversData()

        // Initialize the adapter with mList
        adapter = careGiverListAdpater(mList, client)
        searchRecyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun loadCaregiversData() {
        // Retrieve data from the "Caregivers" collection
        caregiverCollectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val caregiver = document.toObject(Caregiver::class.java)
                    mList.add(caregiver)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Caregiver>()
            for (i in mList) {
                if (i.name?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }
}