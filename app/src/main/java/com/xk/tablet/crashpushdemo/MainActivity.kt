package com.xk.tablet.crashpushdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.xk.tablet.ubug.UBug
import com.xk.tablet.ubug.bugTest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            bugTest()
        }
    }
}