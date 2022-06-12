package com.example.myapplicationfinal

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

private lateinit var imageButtonR1: ImageButton
private lateinit var editTextR1: EditText
private lateinit var editTextR2: EditText
private lateinit var editTextR3: EditText
private lateinit var buttonR1: Button
private lateinit var buttonR2: Button
private lateinit var textViewR5: TextView

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageButtonR1 = findViewById<ImageButton>(R.id.imageButtonR1)
        editTextR1 = findViewById<EditText>(R.id.editTextR1)
        editTextR2 = findViewById<EditText>(R.id.editTextR2)
        editTextR3 = findViewById<EditText>(R.id.editTextR3)
        buttonR1 = findViewById<Button>(R.id.buttonR1)
        buttonR2 = findViewById<Button>(R.id.buttonR2)
        textViewR5 = findViewById<TextView>(R.id.textViewR5)

        imageButtonR1.setOnClickListener(main)
        buttonR1.setOnClickListener(input)
        buttonR2.setOnClickListener(output)

    }

    var main = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }

    var input = View.OnClickListener {
        val DB_FILE = "registers.db"
        val DB_TABLE = "registers"
        val MyDB: SQLiteDatabase
        val regDbHp = MyDBHelper(applicationContext, DB_FILE, null, 1)
        regDbHp.sCreateTableCommand = "CREATE TABLE " + DB_TABLE + "(" +
                "name TEXT NOT NULL," +
                "id INTEGER PRIMARY KEY," +
                "password TEXT)"
        MyDB = regDbHp.writableDatabase

        val newRow = ContentValues()
        newRow.put("name", editTextR1.text.toString())
        newRow.put("id", editTextR2.text.toString())
        newRow.put("password", editTextR3.text.toString())
        MyDB.insert(DB_TABLE, null, newRow)
    }

    var output = View.OnClickListener {
        val DB_FILE = "registers.db"
        val DB_TABLE = "registers"
        val MyDB: SQLiteDatabase
        val regDbHp = MyDBHelper(applicationContext, DB_FILE, null, 1)

        regDbHp.sCreateTableCommand = "CREATE TABLE " + DB_TABLE + "(" +
                "name TEXT NOT NULL," +
                "id INTEGER PRIMARY KEY," +
                "password TEXT)"
        MyDB = regDbHp.writableDatabase

        val c = MyDB.query(
                true, DB_TABLE, arrayOf("name", "id", "password"),
                " id='" + editTextR2.getText().toString() + "'", null,
                null, null, null, null, null
        ) ?: return@OnClickListener

        if (c.moveToNext()) {
            textViewR5.text = "姓名:"+c.getString(0) + "\n" + "學號:"+ c.getString(1) + "\n" + "密碼:"+c.getString(2)
            c.close();

        }
    }
}
