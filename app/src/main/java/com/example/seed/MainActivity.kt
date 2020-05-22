package com.example.seed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
lateinit var button:Button
    lateinit var button3:Button
    lateinit var button4:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button2) as Button
        button3 = findViewById(R.id.button3) as Button
        button4 = findViewById(R.id.button4) as Button
        button.setOnClickListener {
            val intent = Intent(this,DeviceList::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener {
            val intent2 = Intent(this,Automatic::class.java)
            startActivity(intent2)
        }
        button4.setOnClickListener {

        }
    }
}
