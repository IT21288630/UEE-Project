package com.example.uee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uee.R
import com.example.uee.dataClasses.Caregiver

class careGiverListAdpater(var mList: List<Caregiver>) :RecyclerView.Adapter<careGiverListAdpater.careGiverViewHolder>() {












    inner class careGiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val logo: ImageView= itemView.findViewById(R.id.careGiverPhoto)
        val titleTv : TextView = itemView.findViewById(R.id.careGiverName)
        val location: TextView = itemView.findViewById(R.id.careGiverLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): careGiverViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_layout, parent ,false)
        return careGiverViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: careGiverViewHolder, position: Int) {
        //holder.logo.setImageResource(mList[position].image)
        holder.titleTv.text = mList[position].name
        holder.location.text = mList[position].location

    }

    fun setFilteredList(mList: List<Caregiver>){
        this.mList = mList
        notifyDataSetChanged()
    }
}