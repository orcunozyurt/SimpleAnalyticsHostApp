package com.example.analyticssdk.internal.model

data class Event(
    val name: EventType,
    val time: Long = System.currentTimeMillis(),
    val properties: Map<String, Any>?
)
