package com.example.mynewapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class LostItemActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val PICK_IMAGE = 1
    private lateinit var tvImageCount: TextView
    private lateinit var tvWordCount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lost_item)

        // ✅ FIXED (Material EditTexts)
        val etItemName = findViewById<TextInputEditText>(R.id.etItemName)
        val etLocation = findViewById<TextInputEditText>(R.id.etLocation)
        val etDate = findViewById<TextInputEditText>(R.id.etDate)
        val etContact = findViewById<TextInputEditText>(R.id.etContact)
        val etDescription = findViewById<TextInputEditText>(R.id.etDescription)

        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val imagePreview = findViewById<ImageView>(R.id.imagePreview)
        tvImageCount = findViewById(R.id.tvImageCount)
        tvWordCount = findViewById(R.id.tvWordCount)

        val db = FirebaseFirestore.getInstance()

        // ✅ Spinner
        val categories = arrayOf(
            "Select Category",
            "Electronics",
            "Clothes",
            "ID Card",
            "Books",
            "Accessories",
            "Others"
        )

        spinnerCategory.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        // ✅ Date Picker
        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d -> etDate.setText("$d/${m + 1}/$y") },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // ✅ Image Picker
        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // ✅ Word Count
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                val count = if (text.isEmpty()) 0 else text.split("\\s+".toRegex()).size
                tvWordCount.text = "$count / 300 words"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ✅ Submit
        btnSubmit.setOnClickListener {

            val name = etItemName.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val date = etDate.text.toString().trim()
            val category = spinnerCategory.selectedItem.toString()
            val contact = etContact.text.toString().trim()
            val description = etDescription.text.toString().trim()

            if (name.isEmpty() || location.isEmpty() || date.isEmpty()
                || contact.isEmpty() || description.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (category == "Select Category") {
                Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contact.length != 10) {
                Toast.makeText(this, "Enter valid 10-digit number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSubmit.isEnabled = false
            btnSubmit.text = "Submitting..."

            val docRef = db.collection("lost_items").document()

            val item = hashMapOf(
                "id" to docRef.id,
                "name" to name,
                "location" to location,
                "date" to date,
                "category" to category,
                "contact" to contact,
                "description" to description,
                "timestamp" to System.currentTimeMillis(),
                "imageUri" to (imageUri?.toString() ?: "")
            )

            docRef.set(item)
                .addOnSuccessListener {

                    Toast.makeText(this, "Item reported!", Toast.LENGTH_SHORT).show()
                finish()

                    // ✅ CLEAR ALL FIELDS
                    etItemName.text?.clear()
                    etLocation.text?.clear()
                    etDate.text?.clear()
                    etContact.text?.clear()
                    etDescription.text?.clear()

                    // ✅ RESET SPINNER
                    spinnerCategory.setSelection(0)

                    // ✅ RESET IMAGE
                    imagePreview.setImageResource(android.R.drawable.ic_menu_gallery)
                    imageUri = null

                    // ✅ RESET WORD COUNT
                    tvWordCount.text = "0 / 300 words"

                    // ✅ RESET BUTTON
                    btnSubmit.isEnabled = true
                    btnSubmit.text = "Submit"
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show()
                    btnSubmit.isEnabled = true
                    btnSubmit.text = "Submit"
                }
        }
    }

    // ✅ Image result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data?.data

            findViewById<ImageView>(R.id.imagePreview)
                .setImageURI(imageUri)

            tvImageCount.text = "1 image selected"
        }
    }
}