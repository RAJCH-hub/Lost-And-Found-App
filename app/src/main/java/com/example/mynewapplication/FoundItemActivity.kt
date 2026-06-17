package com.example.mynewapplication
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
class FoundItemActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<FoundItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_found_item)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchItems()
    }

    private fun fetchItems() {
        val db = FirebaseFirestore.getInstance()

        db.collection("found_items")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->

                itemList.clear()

                for (document in result) {
                    val item = document.toObject(FoundItem::class.java) ?: continue
                    itemList.add(item)

                }
                val adapter = FoundItemAdapter(itemList)
                recyclerView.adapter = adapter
            }
    }
}