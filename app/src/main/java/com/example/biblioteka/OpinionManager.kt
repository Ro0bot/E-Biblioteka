package com.example.biblioteka

import android.content.Context
import com.google.gson.Gson

object OpinionManager {
    private const val PREFS_NAME = "opinie_prefs"
    fun getOpinie(context: Context, title: String?): MutableList<Opinion> {
        if (title == null) return mutableListOf()

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(title, null) ?: return mutableListOf()

        val type = object : com.google.gson.reflect.TypeToken<MutableList<Opinion>>() {}.type
        return com.google.gson.Gson().fromJson(json, type)
    }
    fun addOpinia(context: Context, title: String?, opinia: Opinion) {
        if (title == null) return

        val list = getOpinie(context, title)
        list.add(opinia)

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(title, com.google.gson.Gson().toJson(list))
            .apply()
    }
    fun removeOpinia(context: Context, title: String?, index: Int) {
        if (title == null) return

        val list = getOpinie(context, title)
        if (index < 0 || index >= list.size) return

        list.removeAt(index)

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(title, Gson().toJson(list))
            .apply()
    }
}