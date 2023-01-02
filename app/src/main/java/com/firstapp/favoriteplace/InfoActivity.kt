package com.firstapp.favoriteplace

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        val getPlaces = intent.getParcelableExtra<Places>("places")

        if (getPlaces != null) {
            val textView: TextView = findViewById(R.id.placeInfoTextView)
            textView.text = getPlaces.placeInfo
        }

       /* val goBackButton = findViewById<Button>(R.id.goBackButton)
        goBackButton.setOnClickListener {
            val intent = Intent(this, ListOfPlacesActivity::class.java)
            startActivity(intent)
        }

        */
    }
}
