package com.example.biblioteka

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesManager {
    private const val PREFS_NAME = "favorites_prefs"
    private const val KEY_FAVORITES = "favorites"
    fun getFavorites(context: Context): MutableList<Ksiazka> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_FAVORITES, null) ?: return mutableListOf()

        val type = object : TypeToken<MutableList<Ksiazka>>() {}.type
        return Gson().fromJson(json, type)
    }
    fun addFavorite(context: Context, book: Ksiazka) {
        val favorites = getFavorites(context)
        if (favorites.any { it.title == book.title }) return

        favorites.add(book)
        save(context, favorites)
    }
    fun isFavorite(context: Context, title: String?): Boolean {
        if (title == null) return false
        return getFavorites(context).any { it.title == title }
    }

    fun removeFavorite(context: Context, title: String?) {
        if (title == null) return
        val favorites = getFavorites(context)
        val updated = favorites.filterNot { it.title == title }
        save(context, updated)
    }
    fun save(context: Context, list: List<Ksiazka>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_FAVORITES, Gson().toJson(list))
            .apply()
    }
}