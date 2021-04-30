package com.example.ml_kit_text_recognition_sample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class MainActivity : AppCompatActivity() {

    private val TAG = "ML_Kit_text_recognition"
    private val logik_id = R.drawable.logik
    private val screenshot_id = R.drawable.bananenbrot


    //view elemente
    private lateinit var btn:Button
    private lateinit var tvContent:TextView
    private lateinit var pb:ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner

    //Bitmap die der Algo benötigt
    private var selectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialisieren
        btn = findViewById(R.id.btn_start)
        tvContent = findViewById(R.id.tv_content)
        pb = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        spinner = findViewById(R.id.spinner)

        //Button
        btn.setOnClickListener {
            startTextRecognition()
            pb.visibility = View.VISIBLE
        }
        imageView.setImageResource(logik_id)
        selectedImage = BitmapFactory.decodeResource(resources, logik_id)

        //Spinner
        val content : Array<String> = arrayOf("logik","screenshot_5")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,content)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tvContent.text = ""
                if(position == 0 ){
                    imageView.setImageResource(logik_id)
                    selectedImage = BitmapFactory.decodeResource(resources, logik_id)




                }
                else
                {
                    imageView.setImageResource(screenshot_id)
                    selectedImage = BitmapFactory.decodeResource(resources, screenshot_id)


                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
    private fun startTextRecognition () //vllt liegt es an input image und recognizer
    {
        val inputImage = InputImage.fromBitmap (selectedImage!!,0)
        val recognizer = TextRecognition.getClient()
        recognizer.process(inputImage)
            .addOnSuccessListener {
                pb.visibility = View.GONE
                tvContent.text = it.text
                Log.d(TAG, "success!")

            }
            .addOnFailureListener{
                Log.d(TAG, "failed!")

            }


    }

}
