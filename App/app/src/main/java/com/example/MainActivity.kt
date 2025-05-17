package com.example.blackboxinvaders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            val  greetingTextview = findViewById<TextView>(R.id.tvActivityRecognition)
            val  basicDescription = findViewById<TextView>(R.id.tvDiscription)
            val startButton = findViewById<Button>(R.id.btnStart)
            startButton.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)

            }
    }
}