package com.example.myapplicationfinal

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*


private lateinit var imageButtonID1: ImageButton
private lateinit var buttonID1: Button
private lateinit var editTextID1: EditText
private lateinit var editTextID2: EditText
private lateinit var editTextID3: EditText
private lateinit var editTextID4: EditText
private lateinit var editTextID5: EditText
private lateinit var textViewID7: TextView

class Inputdogdata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inputdogdata)

        imageButtonID1 = findViewById<ImageButton>(R.id.imageButtonID1)
        buttonID1 = findViewById<Button>(R.id.buttonID1)
        editTextID1 = findViewById<EditText>(R.id.editTextID1)
        editTextID2 = findViewById<EditText>(R.id.editTextID2)
        editTextID3 = findViewById<EditText>(R.id.editTextID3)
        editTextID4 = findViewById<EditText>(R.id.editTextID4)
        editTextID5 = findViewById<EditText>(R.id.editTextID5)
        textViewID7 = findViewById<TextView>(R.id.textViewID7)

        imageButtonID1.setOnClickListener(main)
        buttonID1.setOnClickListener(input)



    }
    var main = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Mydog::class.java)
        startActivity(intent)
    }

    var input = View.OnClickListener {
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
        val newRow = ContentValues()
        newRow.put("id", editTextID1.text.toString())
        newRow.put("name", editTextID2.text.toString())
        newRow.put("birth", editTextID3.text.toString())
        newRow.put("sex", editTextID4.text.toString())
        newRow.put("type", editTextID5.text.toString())
        MyDB.insert(DB_TABLE, null, newRow)

        val c = MyDB.query(
            true, DB_TABLE, arrayOf("id", "name", "birth", "sex", "type"),
            " id='" + editTextID1.getText().toString() + "'", null,
            null, null, null, null, null
        ) ?: return@OnClickListener

        if (c.count === 0) {
            textViewID7.text = ""
            Toast.makeText(this, "沒有資料", Toast.LENGTH_LONG).show()
        }
        else {
            c.moveToFirst();
            textViewID7.text = "狗狗名字: " + c.getString(1) + "\n" +"狗狗生日: "+ c.getString(2)+ "\n" + "狗狗性別: "+c.getString(3)+ "\n" + "狗狗品種: "+c.getString(4)
            while (c.moveToNext()) {
                textViewID7.append("\n" +  c.getString(1) + "\n" + c.getString(2)+ "\n" + c.getString(3)+ "\n" + c.getString(4))
            }
        }

    }
}