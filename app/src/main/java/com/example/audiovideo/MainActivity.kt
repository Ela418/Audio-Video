package com.example.audiovideo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Navigate to MediaRecorderActivity to test its layout
        startActivity(Intent(this, MediaRecorderActivity::class.java))
    }
}
