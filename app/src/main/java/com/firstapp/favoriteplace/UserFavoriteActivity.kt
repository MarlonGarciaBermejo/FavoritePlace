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
import com.firstapp.favoriteplace.databinding.ActivityUserFavoriteBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var addFavoritePlace: EditText
    private lateinit var addFavoriteInfo: EditText

    lateinit var binding: ActivityUserFavoriteBinding
    lateinit var imageUri: Uri

    val db = Firebase.firestore
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addFavoriteButton = findViewById<Button>(R.id.addFavoriteButton)

        addFavoritePlace = findViewById(R.id.addFavoritePlace)
        addFavoriteInfo = findViewById(R.id.addFavoriteInfo)

        addFavoriteButton.setOnClickListener {
            addFavoriteButton()
            getImage()
        }

        binding.userSelectImage.setOnClickListener {
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
            binding.selectImageView.setImageURI(imageUri)
            getImage()
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
            binding.selectImageView.setImageURI(null)
            Toast.makeText(this@UserFavoriteActivity, "Successfuly uploaded", Toast.LENGTH_SHORT)
                .show()
            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {
            if (progressDialog.isShowing) {
                Toast.makeText(this@UserFavoriteActivity, "Failed to upload", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}




