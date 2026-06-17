package com.example.mynewapplication

import com.google.firebase.Timestamp

data class FoundItem(
    val itemName: String = "",
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val location: String = "",
    val timestamp: Timestamp? = null,
    val claimed: Boolean = false,
    val id:String=""
)