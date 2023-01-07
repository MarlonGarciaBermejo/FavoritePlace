package com.firstapp.favoriteplace

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val getPlaces = intent.getParcelableExtra<Places>("places")
        val getImage = intent.getParcelableExtra<Places>("places")

        if (getPlaces != null) {
            val textView: TextView = findViewById(R.id.placeInfoTextView)
            textView.text = getPlaces.placeInfo
        }

        if (getImage != null) {
            val imageView: ImageView = findViewById(R.id.placeImageView)
            imageView.setImageResource(R.drawable.loginbutton)
        }
    }
}