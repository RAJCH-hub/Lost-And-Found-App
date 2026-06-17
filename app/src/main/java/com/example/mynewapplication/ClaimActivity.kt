package com.example.mynewapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ClaimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_claim)

        val etColor = findViewById<EditText>(R.id.etColor)
        val etBrand = findViewById<EditText>(R.id.etBrand)
        val etUniqueFeature = findViewById<EditText>(R.id.etUniqueFeature)
        val btnSubmitClaim = findViewById<Button>(R.id.btnSubmitClaim)

        btnSubmitClaim.setOnClickListener {

            val color = etColor.text.toString()
            val brand = etBrand.text.toString()
            val feature = etUniqueFeature.text.toString()

            if (color.isEmpty() || brand.isEmpty() || feature.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Claim Submitted Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}