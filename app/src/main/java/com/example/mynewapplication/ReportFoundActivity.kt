package com.example.mynewapplication
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
class ReportFoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_found)

        val btnSubmitFound = findViewById<Button>(R.id.btnSubmitFound)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etItemName= findViewById<EditText>(R.id.etItemName)
        val etLocation=  findViewById<EditText>(R.id.etLocation)



        val spinnerCategory=findViewById<Spinner>(R.id.spinnerCategory)

        val tvWordCount = findViewById<TextView>(R.id.tvWordCount)


        val categories = arrayOf(
            "Select Category",
            "Electronics",
            "Books",
            "Clothes",
            "ID Card",
            "Accessories",
            "Wallet",
            "Keys",
            "Bottle",
            "Others"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        spinnerCategory.adapter = adapter
        etDescription.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                val text = s.toString()

                val count = text.length

                tvWordCount.text = "$count / 300 characters"

                if(count > 300){
                    etDescription.error =
                        "Maximum 300 characters allowed"
                }
            }


            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {}
        })

        btnSubmitFound.setOnClickListener {

            val itemName = etItemName.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val category = spinnerCategory.selectedItem.toString()

            if(itemName.isEmpty()){
                etItemName.error = "Item Name is required"
                return@setOnClickListener
            }

            if(category == "Select Category"){
                Toast.makeText(
                    this,
                    "Please select a category",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if(location.isEmpty()){
                etLocation.error = "Found Location is required"
                return@setOnClickListener
            }

            if(description.isEmpty()){
                etDescription.error = "Description is required"
                return@setOnClickListener
            }

            Toast.makeText(
                this,
                "Found Item Submitted Successfully",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}