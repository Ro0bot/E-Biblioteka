package com.example.biblioteka
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioteka.FavoritesManager.getFavorites
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class BaseActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var timeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        timeTextView = findViewById(R.id.timeTextView)
        startClock()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                loadBooks(query)
            } else {
                Toast.makeText(this, "wpisz autora lub tytul", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.favoritesButton).setOnClickListener {
            val favorites = getFavorites(this)

            if (favorites.isEmpty()) {
                Toast.makeText(this, "Brak ulubionych", Toast.LENGTH_SHORT).show()
            } else {
                recyclerView.adapter = BooksAdapter(favorites) { ksiazka ->
                    val intent = Intent(this, BookDetailsActivity::class.java)
                    intent.putExtra("title", ksiazka.title)
                    intent.putExtra("author", ksiazka.author_name?.joinToString())
                    intent.putExtra("year", ksiazka.first_publish_year?.toString())
                    startActivity(intent)
                }
            }
        }
    }
    private fun loadBooks(query: String) {
        ApiClient.api.searchBooks(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val body = response.body()
                if (body != null) {
                    recyclerView.adapter = BooksAdapter(body.docs) {
                        ksiazka -> val intent = Intent(this@BaseActivity, BookDetailsActivity::class.java)
                        intent.putExtra("title", ksiazka.title)
                        intent.putExtra("author", ksiazka.author_name?.joinToString())
                        intent.putExtra("year", ksiazka.first_publish_year?.toString())
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@BaseActivity, "Brak danych", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(this@BaseActivity, "Błąd: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun startClock() {
        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            override fun run() {
                val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                sdf.timeZone = TimeZone.getDefault()
                timeTextView.text = sdf.format(Date())
                handler.postDelayed(this, 1000)
            }
        })
    }
}