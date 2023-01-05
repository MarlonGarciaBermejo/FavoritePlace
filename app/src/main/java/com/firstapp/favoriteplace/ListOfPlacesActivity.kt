package com.firstapp.favoriteplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ListOfPlacesActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val differentPlaces = mutableListOf<Places>()

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference
    lateinit var addPlace: ImageView
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_places)

        // The recyclerView Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PlacesRecyclerAdapter(this, differentPlaces)
        recyclerView.adapter = adapter

        // Get more information when click on a cell item
        adapter.onItemClick = {
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("places", it)
            startActivity(intent)
        }

        auth = Firebase.auth
        val user = auth.currentUser

        // Views the collection of places in a recyclerView list and listens to updated data
        db.collection("users").document(user!!.uid).collection("personalPlace")
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

        db.collection("places")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val place = document.toObject<Places>()
                        if (place != null) {
                            Log.d("!!!", "$place")
                            differentPlaces.add(place)
                        }
                    }
                }
            }

        /* findViewById<ImageView>(R.id.favoriteImageView).setOnClickListener {
             db.collection("users").document(user.uid)
                 .set(
                     mapOf(
                         "favorite" to "afrika"
                     )
                 ).addOnSuccessListener {
                     Log.d("!!!", "set")
                 }
         }
         */

        // Goes to AddPlaceActivity and user adds a favorite place
        addPlace = findViewById(R.id.addPlaceView)
        addPlace.setOnClickListener {
            val intent = Intent(this, AddPlaceActivity::class.java)
            startActivity(intent)

        }
        findViewById<ImageView>(R.id.favoriteImageView).setOnClickListener{
            val intent = Intent(this, UserFavoriteActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getImage() {

        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFirestore = FirebaseFirestore.getInstance()

    }
}