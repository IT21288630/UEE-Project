package com.example.uee.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uee.R
import com.example.uee.adapters.careGiverListAdpater
import com.example.uee.dataClasses.Caregiver
import com.example.uee.fragments.ClientProfileFragment
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


class SearchCaregivers : AppCompatActivity() {
    private lateinit var searchRecyclerView: RecyclerView

    private lateinit var searchView: SearchView
    private lateinit var caregiver: Caregiver

    private var mList = ArrayList<Caregiver>()
    private lateinit var adapter: careGiverListAdpater
    // Initialize Firebase Firestore reference
    private val caregiverCollectionRef = FirebaseFirestore.getInstance().collection("Caregivers")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_caregivers)







        searchRecyclerView = findViewById(R.id.searchRecyclerView)

        searchView = findViewById(R.id.searchView)
        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)

        loadCaregiversData()



        // Initialize the adapter with mList
        adapter = careGiverListAdpater(mList)
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

        val backToMainButton = findViewById<Button>(R.id.backtoMain)
        backToMainButton.setOnClickListener {
            // Start the ClientProfileFragment activity or fragment here
            val intent = Intent(this, ClientProfileFragment::class.java)
            startActivity(intent)
        }



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
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }



}









