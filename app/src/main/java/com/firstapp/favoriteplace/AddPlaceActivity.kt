package com.firstapp.favoriteplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddPlaceActivity : AppCompatActivity() {

    lateinit var addPlaceName: EditText
    lateinit var addInfo: EditText
    lateinit var selectButton: Button
    lateinit var uploadButton: Button

    private lateinit var storageRef : StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    val db = Firebase.firestore
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        val addButton = findViewById<Button>(R.id.addPlaceButton)
        val selectButton = findViewById<Button>(R.id.selectButton)
        val uploadButton = findViewById<Button>(R.id.uploadButton)

        addPlaceName = findViewById(R.id.addPlaceText)
        addInfo = findViewById(R.id.addInfoText)



        addButton.setOnClickListener {
            addPlaceButton()
        }
    }

    fun addPlaceButton() {

        val user = auth.currentUser
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
