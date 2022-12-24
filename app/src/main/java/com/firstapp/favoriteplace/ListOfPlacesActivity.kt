package com.firstapp.favoriteplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ListOfPlacesActivity : AppCompatActivity() {

    private val db = Firebase.firestore

    private val differentPlaces = mutableListOf<Places>()

    lateinit var addPlace: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_places)

        val recyclerView = findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PlacesRecyclerAdapter(this, differentPlaces)
        recyclerView.adapter = adapter

        db.collection("places")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    differentPlaces.clear()
                    for (document in snapshot.documents) {
                        val place = document.toObject<Places>()
                        if (place != null) {
                            Log.d("!!!", "$place")
                            differentPlaces.add(place)
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

        addPlace = findViewById(R.id.addPlaceView)
        addPlace.setOnClickListener {
            val intent = Intent(this, AddPlaceActivity::class.java)
            startActivity(intent)

        }
    }
}
