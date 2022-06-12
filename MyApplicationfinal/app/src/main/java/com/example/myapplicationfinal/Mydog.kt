package com.example.myapplicationfinal

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

private lateinit var imageButtonMD1: ImageButton
private lateinit var buttonMD1: Button
private lateinit var buttonMD2: Button
private lateinit var textViewMD3: TextView
private lateinit var editTextMD1: EditText

class Mydog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mydog)

        imageButtonMD1 = findViewById<ImageButton>(R.id.imageButtonMD1)
        buttonMD1 = findViewById<Button>(R.id.buttonMD1)
        buttonMD2 = findViewById<Button>(R.id.buttonMD2)
        textViewMD3 = findViewById<TextView>(R.id.textViewMD3)
        editTextMD1 = findViewById<EditText>(R.id.editTextMD1)

        imageButtonMD1.setOnClickListener(main)
        buttonMD1.setOnClickListener(search)
        buttonMD2.setOnClickListener(input)



    }
    var main = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Features::class.java)
        startActivity(intent)
    }

    var input = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Inputdogdata::class.java)
        startActivity(intent)
    }

    var search = View.OnClickListener {
        val DB_FILE = "mydog.db"
        val DB_TABLE = "mydog"
        val MyDB: SQLiteDatabase
        val mydogDbHp = MyDBHelper(applicationContext, DB_FILE, null, 1)
        mydogDbHp.sCreateTableCommand = "CREATE TABLE " + DB_TABLE + "(" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL," +
                "birth TEXT NOT NULL," +
                "sex TEXT NOT NULL," +
                "type TEXT)"
        MyDB = mydogDbHp.writableDatabase

        val c = MyDB.query(
            true, DB_TABLE, arrayOf("id", "name", "birth", "sex", "type"),
            " id='" + editTextMD1.getText().toString() + "'", null,
            null, null, null, null, null
        ) ?: return@OnClickListener

        if (c.count === 0) {
            textViewMD3.text = ""
            Toast.makeText(this, "沒有資料", Toast.LENGTH_LONG).show()
        }
        else {
            c.moveToFirst();
            textViewMD3.text = "狗狗名字: " + c.getString(1) + "\n" +"狗狗生日: "+ c.getString(2)+ "\n" + "狗狗性別: "+c.getString(3)+ "\n" + "狗狗品種: "+c.getString(4)
            while (c.moveToNext()) {
                textViewMD3.append("\n" +  c.getString(1) + "\n" + c.getString(2)+ "\n" + c.getString(3)+ "\n" + c.getString(4))
            }
        }
    }
}