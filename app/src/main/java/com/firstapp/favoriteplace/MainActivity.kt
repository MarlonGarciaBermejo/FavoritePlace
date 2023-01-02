package com.firstapp.favoriteplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        auth = Firebase.auth
        emailView = findViewById(R.id.emailTextView)
        passwordView = findViewById(R.id.passwordTextView)



        val createButton = findViewById<ImageButton>(R.id.createButton)
        createButton.setOnClickListener {
            createUser()
        }
        val loginButton = findViewById<ImageButton>(R.id.loginButton)
        loginButton.setOnClickListener {
            loginUser()
        }
    }
    fun goToAddActivity(){
        val intent = Intent(this, ListOfPlacesActivity::class.java)
        startActivity(intent)

    }

    fun loginUser() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty())
            return

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "Sign in succeeded")
                    goToAddActivity()
                } else {
                    Log.d("!!!", "user not signed in ${task.exception}")
                }
            }
    }


    fun createUser() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                Log.d("!!!", "Created user succeeded")
                goToAddActivity()
            } else {
                Log.d("!!!", "user not created ${task.exception}")
            }
        }

    }

}
