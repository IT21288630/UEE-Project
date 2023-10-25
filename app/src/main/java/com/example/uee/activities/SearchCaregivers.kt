package com.example.uee.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uee.R
import com.example.uee.adapters.careGiverListAdpater
import com.example.uee.dataClasses.Caregiver


class SearchCaregivers : AppCompatActivity() {
    private lateinit var searchRecyclerView: RecyclerView

    private lateinit var searchView: SearchView

    private var mList = ArrayList<Caregiver>()
    private lateinit var adapter: careGiverListAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_caregivers)

        searchRecyclerView = findViewById(R.id.searchRecyclerView)

        searchView = findViewById(R.id.searchView)
        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize mList before using it

        addDataToList()

        // Initialize the adapter with mList
        adapter = careGiverListAdpater(mList)
        searchRecyclerView.adapter = adapter
    }

    private fun addDataToList() {
        mList.add(Caregiver("goerge russel"))
        mList.add(Caregiver("goerge russel"))

    }
}
