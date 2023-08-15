package com.example.analyticssdk.internal

import android.content.Context
import android.content.SharedPreferences
import com.example.analyticssdk.internal.model.EventType
import com.example.analyticssdk.internal.model.Session
import com.google.gson.Gson
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class BasicAnalyticsSDKTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    private lateinit var analyticsSDK: BasicAnalyticsSDK

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(mockContext.getSharedPreferences("session_analytics", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)

        `when`(mockSharedPreferences.edit())
            .thenReturn(mockSharedPreferencesEditor)

        `when`(mockSharedPreferencesEditor.putString(anyString(), anyString()))
            .thenReturn(mockSharedPreferencesEditor)

        analyticsSDK = BasicAnalyticsSDK(mockContext)
    }

    @After
    fun tearDown() {
        clearSharedPreferences(mockSharedPreferences)
    }

    @Test
    fun testStartSession() {
        analyticsSDK.startSession()

        verify(mockSharedPreferences.edit()).putString(eq("session_key"), any())
        verify(mockSharedPreferences.edit()).apply()
    }

    @Test
    fun testEndSession() {
        val session = Session()
        session.sessionEndTime = System.currentTimeMillis()

        `when`(mockSharedPreferences.getString(eq("session_key"), any())).thenReturn(Gson().toJson(session))

        analyticsSDK.endSession()

        verify(mockSharedPreferences.edit()).putString(eq("grave_key"), any())
        verify(mockSharedPreferences.edit()).putString(eq("session_key"), any())
        verify(mockSharedPreferences.edit(), times(2)).apply()

    }

    @Test
    fun testTrackEvent() {
        val eventProperties = mapOf("key1" to "value1", "key2" to 42)

        val session = Session()
        `when`(mockSharedPreferences.getString(eq("session_key"), any())).thenReturn(Gson().toJson(session))

        analyticsSDK.trackEvent(EventType.BUTTON_CLICK, eventProperties)

        verify(mockSharedPreferences.edit()).putString(eq("session_key"), any())
        verify(mockSharedPreferences.edit()).apply()
    }

    private fun clearSharedPreferences(prefs: SharedPreferences) {
        `when`(prefs.edit()).thenReturn(mock(SharedPreferences.Editor::class.java))
        `when`(prefs.edit().clear()).thenReturn(mock(SharedPreferences.Editor::class.java))
        prefs.edit().clear().apply()
    }
}
