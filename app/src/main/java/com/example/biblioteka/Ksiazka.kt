package com.example.biblioteka

data class Ksiazka(
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?
)

data class SearchResponse(
    val docs: List<Ksiazka>
)