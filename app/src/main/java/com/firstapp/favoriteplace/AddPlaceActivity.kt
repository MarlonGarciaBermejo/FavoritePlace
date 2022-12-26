package com.firstapp.favoriteplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPlaceActivity : AppCompatActivity() {

    lateinit var addPlaceName: EditText
    lateinit var addInfo: EditText

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        val addButton = findViewById<Button>(R.id.addButton)

        addPlaceName = findViewById(R.id.addPlaceNameText)
        addInfo = findViewById(R.id.addInfoText)


        addButton.setOnClickListener {
            addPlaceButton()
        }
    }

    fun addPlaceButton() {

        val places = hashMapOf(
            "nameOfPlaces" to addPlaceName.text.toString(),
            "placeInfo" to addInfo.text.toString()
        )

        db.collection("places")
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
