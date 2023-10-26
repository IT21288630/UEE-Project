package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.uee.R
import com.example.uee.adapters.filterRecyclerAdapter
import com.example.uee.dataClasses.Caregiver
import com.example.uee.dataClasses.Client
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FilteredCaregiverActivity : AppCompatActivity() {

    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_caregiver)

        val FilterValue = intent.getStringExtra("selectedItem")
        val clientLocation = intent.getStringExtra("clientLocation")
        val txtCatFiltered = findViewById<TextView>(R.id.txtCatFilterType)
        val filteredRecycler = findViewById<RecyclerView>(R.id.filteredRecyclerView)

        txtCatFiltered.text = FilterValue.toString()



        CoroutineScope(Dispatchers.Main).launch {
            val caregiverList = getDataList(FilterValue, clientLocation)
            val layoutManager = LinearLayoutManager(this@FilteredCaregiverActivity)
            filteredRecycler.layoutManager = layoutManager
            val adapter = filterRecyclerAdapter(caregiverList)
            filteredRecycler.adapter = adapter
        }

    }

    private suspend fun getDataList(filter: String?, clientData: String?): ArrayList<Caregiver> {
        val adapterData: ArrayList<Caregiver> = ArrayList() // Initialize the list properly

        val clientLocation = clientData
        if (filter == "Near to you") {
            val querySnapshot = caregiverCollectionRef.get().await()
            for (document in querySnapshot) {
                var temp = document.toObject<Caregiver>()
                if (temp.location == clientLocation) {
                    adapterData.add(temp)
                }
            }
        }
        return adapterData // Return the populated list
    }
}