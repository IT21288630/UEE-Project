package com.example.uee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uee.R
import com.example.uee.dataClasses.Caregiver


class filterRecyclerAdapter(private val dataList: ArrayList<Caregiver>) : RecyclerView.Adapter<filterRecyclerAdapter.CaregiverViewHolder>() {

    class CaregiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your view items here
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaregiverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_favorites_item, parent, false)
        return CaregiverViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaregiverViewHolder, position: Int) {
        // Bind data to your view items here
        val data = dataList[position]
        // Set data to your view items here
        holder.itemView.findViewById<TextView>(R.id.txtFavoriteCaregiverName).text = data.name
        holder.itemView.findViewById<TextView>(R.id.txtFavoriteCaregiverLocation).text = data.location

        Glide.with(holder.itemView.context)
            .load(data?.image)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.itemView.findViewById<ImageView>(R.id.imageView2))

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
