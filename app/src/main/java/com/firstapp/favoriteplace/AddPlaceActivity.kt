package com.firstapp.favoriteplace

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firstapp.favoriteplace.databinding.ActivityAddPlaceBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddPlaceActivity : AppCompatActivity() {

    lateinit var addPlaceName: EditText
    lateinit var addInfo: EditText

    lateinit var binding: ActivityAddPlaceBinding
    lateinit var imageUri: Uri

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addButton = findViewById<Button>(R.id.addPlaceButton)


        addPlaceName = findViewById(R.id.addPlaceText)
        addInfo = findViewById(R.id.addInfoText)

        addButton.setOnClickListener {
            addPlace()
        }

        binding.selectButton.setOnClickListener {
            selectImage()
        }
    }

    // Selects a image to placeImage
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.addPlaceImageView.setImageURI(imageUri)
            getImage()
        }
    }


    fun addPlace() {

        // Adding Place, PlaceInfo and and PlaceImage to FireBase
        val places = hashMapOf(
            "nameOfPlaces" to addPlaceName.text.toString(),
            "placeInfo" to addInfo.text.toString(),
            "placeImage" to binding.addPlaceImageView.toString()
        )

        //Adding Place and Info to Cloud Firebase
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

    private fun getImage() {

        //Adding Place image to Firebase Storage
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File... ")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("imagesToAdd/$fileName")

        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.addPlaceImageView.setImageURI(null)
            Toast.makeText(this@AddPlaceActivity, "Successfuly uploaded", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {
            if (progressDialog.isShowing) {
                Toast.makeText(this@AddPlaceActivity, "Failed to upload", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

