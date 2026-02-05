package com.example.biblioteka

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OpinionAdapter(
    private val opinie: List<Opinion>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<OpinionAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.opiniaText)
        val deleteButton: Button = view.findViewById(R.id.usunOpinieButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opinion, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = opinie[position].text

        holder.deleteButton.setOnClickListener {
            onDelete(position)
        }
    }
    override fun getItemCount() = opinie.size
}