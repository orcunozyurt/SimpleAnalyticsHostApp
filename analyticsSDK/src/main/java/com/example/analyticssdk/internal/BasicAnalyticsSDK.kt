package com.example.analyticssdk.internal

import android.content.Context
import android.util.Log
import com.example.analyticssdk.AnalyticsSDK
import com.example.analyticssdk.internal.model.Event
import com.example.analyticssdk.internal.model.EventType
import com.example.analyticssdk.internal.model.Session
import com.example.analyticssdk.internal.storage.AnalyticsDatabase
import com.example.analyticssdk.internal.storage.DataManager

class BasicAnalyticsSDK(context: Context) : AnalyticsSDK {
    private var dataManager: DataManager
    init {
        AnalyticsDatabase.init(context)
        dataManager = DataManager()
    }

    /**
     * Starts the session by recording current session to AnalyticsDatabase
     * If there is no current session, creates a new session. Otherwise uses the persisted session
     */
    override fun startSession() {
        if (AnalyticsDatabase.session == null) {
            val session = Session()
            AnalyticsDatabase.session = session
        }
        dataManager.startUploading()
    }

    /**
     * Ends the session by
     * 1. setting the end time of session
     * 2. add ended session to graveyard for keeping old sessions
     * 3. set current session to null
     */
    override fun endSession() {
        val session = AnalyticsDatabase.session
        session?.let {
            it.sessionEndTime = System.currentTimeMillis()
            AnalyticsDatabase.addToGraveyard(session)
        }
        AnalyticsDatabase.session = null
        dataManager.killAll()
    }

    /**
     * Tracks given events.
     * @param eventName is the type of tracked event
     * @param properties is additional properties
     * Creates the Event object and adds it to events list of current session.
     */
    override fun trackEvent(eventName: EventType, properties: Map<String, Any>?) {
        val event = Event(
            name = eventName,
            properties = properties
        )
        val currentSession = AnalyticsDatabase.session
        currentSession?.addEvent(event)
        AnalyticsDatabase.session = currentSession
    }

    companion object {
        private var TAG = BasicAnalyticsSDK::class.java.simpleName
    }
}