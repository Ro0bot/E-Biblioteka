package com.example.biblioteka

import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val year = intent.getStringExtra("year")

        findViewById<TextView>(R.id.detailTitle).text = "Tytul: ${title ?: "Brak"}"
        findViewById<TextView>(R.id.detailAuthor).text = "Autor: ${author ?: "Brak"}"
        findViewById<TextView>(R.id.detailYear).text = "Rok: ${year ?: "Brak"}"
    }
}
