package com.example.mynewapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.widget.Button
import android.content.Intent

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_detail)

        // 🔥 Receive data from Adapter
        val itemName = intent.getStringExtra("name")
        val category = intent.getStringExtra("category")
        val location = intent.getStringExtra("location")
        val date = intent.getStringExtra("date")
        val itemId = intent.getStringExtra("itemId")

        // 🔥 Connect with UI
        val tvItemName = findViewById<TextView>(R.id.tvItemName)
        val tvCategory = findViewById<TextView>(R.id.tvCategory)
        val tvLocation = findViewById<TextView>(R.id.tvLocation)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val btnClaim = findViewById<Button>(R.id.btnClaim)


        // 🔥 Set data
        tvItemName.text = itemName ?: "No Name"
        tvCategory.text = category ?: "No Category"
        tvLocation.text = location ?: "No Location"
        tvDate.text = date ?: "No Date"

        btnClaim.setOnClickListener {

            val intent = Intent(this, ClaimActivity::class.java)

            intent.putExtra("itemName", itemName)
            intent.putExtra("category", category)
            intent.putExtra("itemId", itemId)

            startActivity(intent)
        }
    }

}