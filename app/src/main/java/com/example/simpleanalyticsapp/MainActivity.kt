package com.example.simpleanalyticsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import com.example.analyticssdk.internal.BasicAnalyticsSDK
import com.example.analyticssdk.internal.model.EventType

class MainActivity : AppCompatActivity() {
    private var analyticsSDK: BasicAnalyticsSDK? = null
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var rootView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.root_view)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)

        analyticsSDK = BasicAnalyticsSDK(this)
        analyticsSDK?.startSession()
        val props = mapOf("name" to this.javaClass.simpleName)
        analyticsSDK?.trackEvent(EventType.SCREEN_VIEW, props)
        configureUI()
    }

    private fun configureUI() {
        button1.setOnClickListener {
            val props = mapOf("text" to button1.text)
            analyticsSDK?.trackEvent(EventType.BUTTON_CLICK, props)
        }

        button2.setOnClickListener {
            val props = mapOf("text" to button2.text)
            analyticsSDK?.trackEvent(EventType.BUTTON_CLICK, props)
        }

        button3.setOnClickListener {
            val props = mapOf("text" to button3.text)
            analyticsSDK?.trackEvent(EventType.BUTTON_CLICK, props)
        }

        button4.setOnClickListener {
            val props = mapOf("text" to button4.text)
            analyticsSDK?.trackEvent(EventType.BUTTON_CLICK, props)
        }

        button5.setOnClickListener {
            val props = mapOf("text" to button5.text)
            analyticsSDK?.trackEvent(EventType.BUTTON_CLICK, props)
        }

        button6.setOnClickListener {
            val props = mapOf("text" to button6.text)
            analyticsSDK?.trackEvent(EventType.BUTTON_CLICK, props)
        }

        rootView.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            val props = mapOf(
                "text" to view::class.java.name,
                "scrollX" to i,
                "scrollY" to i2
            )
            analyticsSDK?.trackEvent(EventType.SCROLL, props)
        }
    }

    override fun onStop() {
        super.onStop()
        analyticsSDK?.endSession()
    }
}