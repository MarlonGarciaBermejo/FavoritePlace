package com.firstapp.favoriteplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class addPlaceActivity : AppCompatActivity() {

    lateinit var addPlaceName : EditText
    lateinit var addInfo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)
    }
}