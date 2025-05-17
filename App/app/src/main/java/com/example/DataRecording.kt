package com.example.blackboxinvaders

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.FileOutputStream
import java.io.PrintWriter

class DataRecording : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_recording)
        val  getbackbitch = findViewById<TextView>(R.id.goBackInfo)
        val startButton = findViewById<Button>(R.id.goBack)
        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}

//    //Output Initialization
//    private lateinit var fileOutputStream: FileOutputStream
//    private lateinit var printWriter: PrintWriter
//    //private val data = mutableListOf<Triple<Float, Float, Long>>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_recording)
//
//        val getData = findViewById<Button>(R.id.btn)
//        getData.setOnClickListener {
//            val intent = Intent(this, DataRecording::class.java)
//            startActivity(intent)
//
//
//
//            //FileOutputStream = openFileOutput("accelerometer.csv", Context.MODE_PRIVATE)
//        //PrintWriter = PrintWriter(fileOutputStream)
//    }
//    }
////
//    }
//}
   // private fun writeToCSV() {
        //val file = File(getExternalFilesDir(null), "accelerometer_data.csv")
        //val printWriter = PrintWriter(file)
        //SensorData.forEach {
            //printWriter.println("${it[0]}, ${it[1]}, ${it[2]}")
        //}
        //printWriter.close()
    //}
