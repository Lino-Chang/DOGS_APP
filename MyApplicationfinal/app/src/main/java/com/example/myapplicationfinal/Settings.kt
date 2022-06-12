package com.example.myapplicationfinal

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*


private lateinit var imageButtonS1: ImageButton
private lateinit var editTextS1: EditText
private lateinit var editTextS2: EditText
private lateinit var editTextS3: EditText
private lateinit var buttonS1: Button



class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        imageButtonS1 = findViewById<ImageButton>(R.id.imageButtonS1)
        editTextS1 = findViewById<EditText>(R.id.editTextS1)
        editTextS2 = findViewById<EditText>(R.id.editTextS2)
        editTextS3 = findViewById<EditText>(R.id.editTextS3)
        buttonS1 = findViewById<Button>(R.id.buttonS1)

        imageButtonS1.setOnClickListener(faetures)
        buttonS1.setOnClickListener(change)

    }

    var faetures = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Features::class.java)
        startActivity(intent)
    }

    var change = View.OnClickListener {
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
                " id='" + editTextS1.getText().toString() + "'", null,
                null, null, null, null, null
        ) ?: return@OnClickListener

        if (c.moveToNext()) {
            var dbPassword: String = c.getString(c.getColumnIndex("password"))
            c.close();
            if (editTextS2.getText().toString() == dbPassword) {
                val newRow = ContentValues()
                newRow.put("password", editTextS3.text.toString())
                MyDB.update(DB_TABLE, newRow, "id='" + editTextS1.text.toString() + "'and password='" + editTextS2.text.toString() + "'", null)
                Toast.makeText(this, "修改完成", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "密碼錯誤", Toast.LENGTH_LONG).show()
            }
        }
    }
}
