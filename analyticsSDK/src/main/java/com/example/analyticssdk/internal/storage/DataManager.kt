package com.example.analyticssdk.internal.storage

import com.example.analyticssdk.internal.model.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DataManager {
    private var uploaderJob: Job? = null

    fun startUploading() {
        // Schedule the uploader to run periodically
        uploaderJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                kotlinx.coroutines.delay(30000L) // Upload every 30 seconds
                uploadAnalytics()
            }
        }
    }

    fun killAll() {
        uploaderJob?.cancel()
        uploaderJob = null
    }

    private fun uploadAnalytics() {
        val graveyard = AnalyticsDatabase.sessionGraveyard

        if (graveyard.isNotEmpty()) {
            // Simulate fake network call to remote server
            simulateNetworkCall(graveyard) { success ->
                if (success) {
                    // Clear the graveyard after successful upload
                    AnalyticsDatabase.cleanGraveyard()
                }
            }
        } else {
            println("No sessions in graveyard")
            println("CurrentSession: ${AnalyticsDatabase.session}")
        }

    }

    private fun simulateNetworkCall(sessions: List<Session>, success: (Boolean) -> Unit) {
        // Simulate network call and print analytics data
        println("Simulating network call...")
        for (data in sessions) {
            println("Uploading analytics: $data")
        }
        println("Network call simulation complete.")
        success(true)
    }

}