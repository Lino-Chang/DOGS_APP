package com.example.myapplicationfinal

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

private lateinit var imageButtonL1: ImageButton
private lateinit var buttonL1: Button
private lateinit var editTextL1: EditText
private lateinit var editTextL2: EditText


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imageButtonL1 = findViewById<ImageButton>(R.id.imageButtonL1)
        buttonL1 = findViewById<Button>(R.id.buttonL1)
        editTextL1 = findViewById<EditText>(R.id.editTextL1)
        editTextL2 = findViewById<EditText>(R.id.editTextL2)

        imageButtonL1.setOnClickListener(main)
        buttonL1.setOnClickListener(login)
    }

    var main = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }

    var login = View.OnClickListener {

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
            " id='" + editTextL1.getText().toString() + "'", null,
            null, null, null, null, null
        ) ?: return@OnClickListener

        /*以下程式片段參考來源:https://reurl.cc/NrqeMe*/
        if (c.moveToNext()) {
            var dbPassword: String = c.getString(c.getColumnIndex("password"))
            c.close();
            if (editTextL2.getText().toString() == dbPassword) {
                Toast.makeText(this, "密碼正確，登入成功", Toast.LENGTH_LONG).show()
                val intent = Intent()
                intent.setClass(this, Features::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "密碼錯誤，請重新輸入", Toast.LENGTH_LONG).show()
            }
        }
    }
}