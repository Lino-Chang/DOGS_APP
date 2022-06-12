package com.example.myapplicationfinal

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationfinal.ml.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.lang.Math.abs
import java.net.URI


private lateinit var imageButtonP1: ImageButton
private lateinit var imageViewP1: ImageView
private lateinit var textViewP2: TextView
private lateinit var buttonP1: Button
private lateinit var buttonP2: Button


class Prediction : AppCompatActivity() {


    lateinit var bitmap:Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        val labels = application.assets.open("labels0611.txt").bufferedReader().use { it.readText() }.split("\n")
        imageButtonP1 = findViewById<ImageButton>(R.id.imageButtonP1)
        imageViewP1 = findViewById<ImageView>(R.id.imageViewP1)
        buttonP1 = findViewById<Button>(R.id.buttonP1)
        buttonP2 = findViewById<Button>(R.id.buttonP2)
        textViewP2 = findViewById<TextView>(R.id.textViewP2)

        imageButtonP1.setOnClickListener(faetures)
        buttonP1.setOnClickListener {select()}

        /*以下程式片段參考來源:https://www.youtube.com/watch?v=6ErbFQb8QS8&t=880s*/
        buttonP2.setOnClickListener {

            var resized = Bitmap.createScaledBitmap(bitmap,224,224,true)
            val model = Model0611.newInstance(this)
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)

            var tbuffrt =TensorImage.fromBitmap(resized)
            var byteBuffer = tbuffrt.buffer
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max =getMax(outputFeature0.floatArray)
            textViewP2.setText(labels[max])
            model.close()
        }
    }

    var faetures = View.OnClickListener {
        val intent = Intent()
        intent.setClass(this, Features::class.java)
        startActivity(intent)
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent , 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            imageViewP1.setImageURI(data?.data)
            var uri : Uri?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)

        }
    }

    fun getMax(arr:FloatArray) : Int{
        var ind = 0;
        var min = 0.0f;

        for(i in 0..4)
        {
            if(arr[i] > min)
            {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }
}