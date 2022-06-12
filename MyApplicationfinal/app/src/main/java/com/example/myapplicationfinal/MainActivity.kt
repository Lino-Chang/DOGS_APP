package com.example.myapplicationfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


private lateinit var textViewM1: TextView
private lateinit var textViewM2:TextView
private lateinit var buttonM1: Button
private lateinit var buttonM2: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewM1 = findViewById<TextView>(R.id.textViewM1)
        textViewM2 = findViewById<TextView>(R.id.textViewM2)
        buttonM1 = findViewById<Button>(R.id.buttonM1)
        buttonM2 = findViewById<Button>(R.id.buttonM2)


        buttonM1.setOnClickListener (login)
        buttonM2.setOnClickListener (register)
        textViewM2.setOnClickListener(faetures)

    }

    var login = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Login::class.java)
        startActivity(intent)
    }

    var faetures = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Features::class.java)
        startActivity(intent)
    }

    var register = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Register::class.java)
        startActivity(intent)
    }

}