package com.example.biblioteka
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter(private val books: List<Ksiazka>, private val onItemClick: (Ksiazka) -> Unit) :
    RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {
    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(android.R.id.text1)
        val author: TextView = view.findViewById(android.R.id.text2)
        //val year: TextView = view.findViewById(android.R.id.text3)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return BookViewHolder(v)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title ?: "Brak tytułu"
        holder.author.text = book.author_name?.joinToString() ?: "Brak autora"
        //holder.year.text = "Rok: ${book.first_publish_year ?: "Brak"}"
        holder.itemView.setOnClickListener {
            onItemClick(book)
        }
    }
    override fun getItemCount(): Int = books.size
}