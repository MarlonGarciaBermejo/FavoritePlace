package com.firstapp.favoriteplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacesRecyclerAdapter(context: Context, private val places: MutableList<Places>) :
    RecyclerView.Adapter<PlacesRecyclerAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = layoutInflater.inflate(R.layout.list_places, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val place = places[position]

        holder.nameOfPlaces.text = place.nameOfPlaces
        holder.placeInfo.text = place.placeInfo
    }

    override fun getItemCount(): Int {
        return places.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameOfPlaces = itemView.findViewById<TextView>(R.id.nameOfPlaceTextView)
        var palceImage = itemView.findViewById<ImageView>(R.id.placeImageView)
        val placeInfo = itemView.findViewById<TextView>(R.id.infoTextView)
    }
}