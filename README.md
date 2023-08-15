# SimpleAnalyticsHostApp
Simple android analytics library for recording ui interactions.

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
4. End the session When appropriate:

   ```
     analyticsSDK.endSession()
   ```

