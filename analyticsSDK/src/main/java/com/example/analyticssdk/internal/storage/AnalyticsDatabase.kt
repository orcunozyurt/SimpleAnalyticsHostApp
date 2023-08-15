package com.example.analyticssdk.internal.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.analyticssdk.internal.model.Session
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AnalyticsDatabase {
    private lateinit var preferences: SharedPreferences
    private val gson: Gson = Gson()
    private const val MODE = Context.MODE_PRIVATE
    private const val NAME = "session_analytics"

    private val SESSION = Pair("session_key", null)
    private val GRAVE = Pair("grave_key", null)

    /**
     * initialization of preferences. Used for persisting the data. SharedPreferences are used
     * for simplicity. DataStore or Room is the suggested way of storing these.
     * @param context: Context
     */
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * Clear everything persisted.
     */
    fun clearAll() {
        preferences.edit().clear().apply()
    }

    /**
     * used for editing the persisted data.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    /**
     * Keep active session and events
     */
    var session: Session?
        get() {
            val jsonString = preferences.getString(SESSION.first, SESSION.second)
            return gson.fromJson(jsonString, Session::class.java)
        }
        set(value) {
            preferences.edit {
                val jsonString = gson.toJson(value)
                it.putString(SESSION.first, jsonString)
            }
        }

    /**
     * Keep old sessions and events
     */
    var sessionGraveyard: MutableList<Session>
        get() {
            val jsonString = preferences.getString(GRAVE.first, GRAVE.second)
            val objType = object : TypeToken<List<Session>>() {}.type
            return gson.fromJson(jsonString, objType) ?: mutableListOf()
        }
        set(value) {
            preferences.edit {
                val jsonString = gson.toJson(value)
                it.putString(GRAVE.first, jsonString)
            }
        }

    /**
     * Adds given session to graveyard
     * @param session ended Session object to be added to graveyard.
     * Eventually, there should be a mechanism to batch the records in graveyard and share it
     * with a remote server. When the data is successfully shared with a remote server
     * only then graveyard should be cleaned.
     */
    fun addToGraveyard(session: Session) {
        val graveyard = sessionGraveyard
        graveyard.add(session)
        sessionGraveyard = graveyard
    }

    /**
     * Cleans all sessions recorded.
     */
    fun cleanGraveyard() {
        sessionGraveyard = mutableListOf()
    }

}