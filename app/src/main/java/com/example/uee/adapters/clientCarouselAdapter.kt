package com.example.uee.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uee.R
import com.example.uee.models.clientCarouselDataModel
import com.jackandphantom.carouselrecyclerview.view.ReflectionImageView

class clientCarouselAdapter (private var list : List<clientCarouselDataModel>): RecyclerView.Adapter<clientCarouselAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ReflectionImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.clientcarouselitem, parent,false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image).load(list.get(position).img).into(holder.image)
    }

    fun updateData(list: List<clientCarouselDataModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}
