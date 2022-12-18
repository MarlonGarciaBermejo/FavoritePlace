package com.firstapp.favoriteplace

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListOfPlacesActivity : AppCompatActivity() {

    val db = Firebase.firestore

    val differentPlaces = mutableListOf<Places>(
        Places(R.drawable.loginbutton, "Gothenburg", "Gothenburg is the city with the longest summers in Sweden, 144 days to be precise"),
        Places(R.drawable.loginbutton, "Stockholm", "The second best place to be!"),

        )

    lateinit var addPlace: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_places)

        val recyclerView = findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PlacesRecyclerAdapter(this, differentPlaces)

        recyclerView.adapter = adapter


        addPlace = findViewById(R.id.addPlaceView)

        addPlace.setOnClickListener {
            val intent = Intent(this, addPlaceActivity::class.java)
            startActivity(intent)

        }


        /*     val data = Places("123",R.drawable.loginbutton)

         db.collection("places")
             .add(data)
             .addOnSuccessListener { documentReference ->
                 Log.d("!!!", "DocumentSnapshot written with ID: ${documentReference.id}")
             }
             .addOnFailureListener { e ->
                 Log.w("!!!", "Error adding document", e)
             }

         */


    }


}
