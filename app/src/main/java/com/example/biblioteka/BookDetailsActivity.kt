package com.example.biblioteka

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val year = intent.getStringExtra("year")

        findViewById<TextView>(R.id.detailTitle).textSize = 20f
        findViewById<TextView>(R.id.detailTitle).text = "Tytul: ${title ?: "Brak"}"
        findViewById<TextView>(R.id.detailAuthor).textSize = 20f
        findViewById<TextView>(R.id.detailAuthor).text = "Autor: ${author ?: "Brak"}"
        findViewById<TextView>(R.id.detailYear).textSize = 20f
        findViewById<TextView>(R.id.detailYear).text = "Rok: ${year ?: "Brak"}"

        val favButton = findViewById<Button>(R.id.ulubione)

        fun updateButton() {
            if (FavoritesManager.isFavorite(this, title)) {
                favButton.text = "Usuń z ulubionych"
            } else {
                favButton.text = "Dodaj do ulubionych"
            }
        }

        updateButton()

        favButton.setOnClickListener {
            if (FavoritesManager.isFavorite(this, title)) {
                FavoritesManager.removeFavorite(this, title)
                Toast.makeText(this, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show()
            } else {
                val book = Ksiazka(
                    title = title,
                    author_name = author?.split(", "),
                    first_publish_year = year?.toIntOrNull()
                )
                FavoritesManager.addFavorite(this, book)
                Toast.makeText(this, "Dodano do ulubionych", Toast.LENGTH_SHORT).show()
            }
            updateButton()
        }

        findViewById<Button>(R.id.powrot).setOnClickListener {
            finish()
        }

        val opiniaEditText = findViewById<EditText>(R.id.opiniaEditText)
        val dodajOpinieButton = findViewById<Button>(R.id.dodajOpinieButton)
        val opinieRecyclerView = findViewById<RecyclerView>(R.id.opinieRecyclerView)

        opinieRecyclerView.layoutManager = LinearLayoutManager(this)

        fun loadOpinie() {
            val opinie = OpinionManager.getOpinie(this, title)

            opinieRecyclerView.adapter = OpinionAdapter(opinie) { index ->
                OpinionManager.removeOpinia(this, title, index)
                loadOpinie()
                Toast.makeText(this, "Opinia usunięta", Toast.LENGTH_SHORT).show()
            }
        }
        loadOpinie()

        dodajOpinieButton.setOnClickListener {
            val text = opiniaEditText.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Opinia nie może być pusta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            OpinionManager.addOpinia(
                this,
                title,
                Opinion(text)
            )
            opiniaEditText.text.clear()
            loadOpinie()
        }

    }
}