package com.example.analyticssdk.internal.model

import java.util.UUID

data class Session(
    val sessionId: UUID = UUID.randomUUID(),
    val sessionStartTime: Long = System.currentTimeMillis(),
    var sessionEndTime: Long? = null,
    val events: MutableList<Event> = mutableListOf()
) {
    fun addEvent(event: Event) {
        events.add(event)
    }
}
