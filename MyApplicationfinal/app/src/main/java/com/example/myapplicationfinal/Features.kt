package com.example.myapplicationfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.cardview.widget.CardView

private lateinit var imageButtonF1: ImageButton
private lateinit var CardViewF1:CardView
private lateinit var CardViewF2:CardView
private lateinit var CardViewF3:CardView

class Features : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_features)

        imageButtonF1 = findViewById<ImageButton>(R.id.imageButtonF1)
        CardViewF1 = findViewById<CardView>(R.id.CardViewF1)
        CardViewF2 = findViewById<CardView>(R.id.CardViewF2)
        CardViewF3 = findViewById<CardView>(R.id.CardViewF3)

        imageButtonF1.setOnClickListener(main)
        CardViewF1.setOnClickListener(prediction)
        CardViewF2.setOnClickListener(mydog)
        CardViewF3.setOnClickListener(settings)

    }
    var main = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }

    var prediction = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Prediction::class.java)
        startActivity(intent)
    }
    var mydog = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Mydog::class.java)
        startActivity(intent)
    }

    var settings = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Settings::class.java)
        startActivity(intent)
    }

}