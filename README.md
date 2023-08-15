# SimpleAnalyticsHostApp
Simple android analytics library for recording ui interactions.

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
4. End the session When appropriate:

   ```
     analyticsSDK.endSession()
   ```

