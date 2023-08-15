package com.example.analyticssdk

import com.example.analyticssdk.internal.model.EventType

internal interface AnalyticsSDK {
    fun startSession()
    fun endSession()
    fun trackEvent(eventName: EventType, properties: Map<String, Any>?)
}