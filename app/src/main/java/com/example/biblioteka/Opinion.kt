package com.example.biblioteka

data class Opinion(
    val text: String,
    val date: Long = System.currentTimeMillis()
)
