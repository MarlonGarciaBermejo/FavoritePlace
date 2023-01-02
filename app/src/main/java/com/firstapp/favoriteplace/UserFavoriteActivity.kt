package com.firstapp.favoriteplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFavoriteActivity : AppCompatActivity() {

    lateinit var addFavoritePlace: EditText
    lateinit var addFavoriteInfo: EditText

    val db = Firebase.firestore
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_favorite)

        val addFavoriteButton = findViewById<Button>(R.id.addFavoriteButton)

        addFavoritePlace = findViewById(R.id.addFavoritePlace)
        addFavoriteInfo = findViewById(R.id.addFavoriteInfo)


        addFavoriteButton.setOnClickListener {
            addFavoriteButton()
        }
    }
        fun addFavoriteButton() {
            val user = auth.currentUser
            val places = hashMapOf(
                "nameOfPlaces" to addFavoritePlace.text.toString(),
                "placeInfo" to addFavoriteInfo.text.toString()
            )
            db.collection("users").document(user!!.uid).collection("personalPlace")
                .add(places)
                .addOnCompleteListener { documentReference ->
                    Log.d("!!!", "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.d("!!!", "Error adding document", e)
                }
            val intent = Intent(this, ListOfPlacesActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Place is added!", Toast.LENGTH_SHORT).show()
        }
}



