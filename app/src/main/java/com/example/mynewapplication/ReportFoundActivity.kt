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
import android.net.Uri
import android.widget.ImageView
import android.content.Intent
import android.view.View
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.firestore.FirebaseFirestore

class ReportFoundActivity : AppCompatActivity() {
    private var selectedImageUri: Uri? = null
    private lateinit var imageView: ImageView
    private lateinit var btnUploadImage: Button
    private lateinit var tvFoundImageCount: TextView

    private val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_found)
        CloudinaryConfig.init(this)
        val db = FirebaseFirestore.getInstance()
        val btnSubmitFound = findViewById<Button>(R.id.btnSubmitFound)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etItemName= findViewById<EditText>(R.id.etItemName)
        val etLocation=  findViewById<EditText>(R.id.etLocation)
        imageView = findViewById(R.id.imageView)
        btnUploadImage = findViewById(R.id.FoundItemUploadImage)
        tvFoundImageCount = findViewById(R.id.tvFoundImage)

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

        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

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
            if (selectedImageUri == null) {
                Toast.makeText(
                    this,
                    "Please upload an image",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            selectedImageUri?.let { uri ->

                MediaManager.get().upload(uri)
                    .callback(object : UploadCallback {

                        override fun onStart(requestId: String?) {

                        }

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                        }

                        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {

                            val imageUrl = resultData?.get("secure_url").toString()
                            val docRef = db.collection("found_items").document()
                            val item = hashMapOf(
                                "id" to docRef.id,
                                "itemName" to itemName,
                                "category" to category,
                                "location" to location,
                                "description" to description,
                                "imageUrl" to imageUrl,
                                "timestamp" to System.currentTimeMillis()
                            )
                            docRef.set(item)
                                .addOnSuccessListener {

                                    Toast.makeText(
                                        this@ReportFoundActivity,
                                        "Found Item Submitted Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()
                                }
                                .addOnFailureListener {

                                    Toast.makeText(
                                        this@ReportFoundActivity,
                                        "Error saving found item",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            // We will save this imageUrl to Firestore in the next step.
                        }

                        override fun onError(requestId: String?, error: com.cloudinary.android.callback.ErrorInfo?) {

                            Toast.makeText(
                                this@ReportFoundActivity,
                                "Upload Failed",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        override fun onReschedule(requestId: String?, error: com.cloudinary.android.callback.ErrorInfo?) {

                        }

                    }).dispatch()

            }
        }
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE &&
            resultCode == RESULT_OK &&
            data != null
        ) {
            selectedImageUri = data.data

            imageView.setImageURI(selectedImageUri)
            imageView.visibility = View.VISIBLE
            tvFoundImageCount.text = "1 image selected"
        }
    }
}