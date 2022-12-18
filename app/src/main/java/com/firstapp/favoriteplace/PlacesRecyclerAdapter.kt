package com.firstapp.favoriteplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacesRecyclerAdapter(val context: Context, val places: List<Places>) :
    RecyclerView.Adapter<PlacesRecyclerAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = layoutInflater.inflate(R.layout.list_places, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val places = places[position]

        holder.palceImage.setImageDrawable(holder.palceImage.context.getDrawable(places.placeImage))
        holder.placeName.text = places.placeName
        holder.placeInfo.text = places.placeInfo

    }

    override fun getItemCount(): Int {
        return places.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var placeName = itemView.findViewById<TextView>(R.id.nameOfPlaceTextView)
        var palceImage = itemView.findViewById<ImageView>(R.id.placeImageView)
        var placeInfo = itemView.findViewById<TextView>(R.id.infoTextView)

    }
}