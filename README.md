# SimpleAnalyticsHostApp
Simple android analytics library for recording ui interactions.

## Overview
`analyticsSDK` library works with a very simple logic. It keeps a session that has start and end times. Apart from that it keeps list of events.

All sessions are persisted to Shared Preferences for simplicity but for a more scalable solution this would need to be changed to a proper data storage mechanism like Room or DataStore.

A `DataManager` class periodically checks the recorded sessions(every 30 seconds) and simulates a network upload operation. And cleans the old sessions amid a successful (fake) network upload operation.

Run the tests:
`./gradlew test`

Generate Library Artifact:
`./gradlew assemble`

## Installation
1. Add the JitPack repository to your build file
   ```
    allprojects {
  		repositories {
  			...
  			maven { url 'https://jitpack.io' }
  		}
  	}
   ```
2. Add the dependency
   ```
    dependencies {
          implementation 'com.github.orcunozyurt:SimpleAnalyticsHostApp:0.5.10'
  	}
   ```

Alternatively, if you would like to have the AAR file directly, Check **Releases**. Github workflow is triggered at every tag push and builds the library, creates the release and uploads the artifacts to it.

## Example Usage

1. In the app initialize the SDK:
  
  ``` 
    val analyticsSDK = BasicAnalyticsSDK()
  ```

2. Start a session
  ```
    analyticsSDK.startSession()
  
  ```
3. Track event with properties:
   ```
   val eventProperties = mapOf("key1" to "value1", "key2" to 42)
   analyticsSDK.trackEvent(EventType.BUTTON_CLICK, eventProperties)
   ```

   Event Types:
   ```
   enum class EventType {
      BUTTON_CLICK, SCREEN_VIEW, SCROLL, ITEM_SELECT
   }
   ```
4. End the session When appropriate:

   ```
     analyticsSDK.endSession()
   ```

## TODO:
1. Replace Shared Preferences Data Cache mechanism with Room
2. Implement an actual remote data storage mechanism
3. Add Integration tests and simulate button clicks / scrolls
4. Add more event types to record.
