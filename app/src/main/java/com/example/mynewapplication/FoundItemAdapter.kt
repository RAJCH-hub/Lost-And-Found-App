package com.example.mynewapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoundItemAdapter(private val itemList: List<FoundItem>) :
    RecyclerView.Adapter<FoundItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemLocation: TextView = itemView.findViewById(R.id.itemLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_found, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.itemName.text = item.itemName
        holder.itemLocation.text = item.location

        // 🔥 CLICK LOGIC ADDED HERE
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ItemDetailActivity::class.java)

            intent.putExtra("name", item.itemName)
            intent.putExtra("category", item.category)
            intent.putExtra("location", item.location)
            intent.putExtra("itemId", item.id)

            // Convert timestamp to string
            val date = item.timestamp?.toDate().toString()
            intent.putExtra("date", date)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}