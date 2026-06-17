package com.example.mynewapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load correct layout (buttons screen)
        setContentView(R.layout.activity_main)

        // Buttons from XML
        val btnLost = findViewById<Button>(R.id.btnLost)
        val btnFound = findViewById<Button>(R.id.btnFound)
        val btnReportFound=findViewById<Button>(R.id.btnReportFound)

        // Navigate to Lost Item screen
        btnLost.setOnClickListener {
            val intent = Intent(this, LostItemActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Found Items screen
        btnFound.setOnClickListener {
            val intent = Intent(this, FoundItemActivity::class.java)
            startActivity(intent)
        }
        //report found item
        btnReportFound.setOnClickListener{
            val intent=Intent(this,ReportFoundActivity::class.java)
            startActivity(intent)
        }
    }
}