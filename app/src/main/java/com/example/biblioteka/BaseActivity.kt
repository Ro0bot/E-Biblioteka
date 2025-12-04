package com.example.biblioteka
import android.content.Intent
import androidx.appcompat.widget.SearchView
import android.view.Menu
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

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
}