package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TimetableShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable_show)

        val timeSlots = listOf(
            "9_10", "10_11", "11_12", "12_13", "13_14",
            "14_15", "15_16", "16_17", "17_18", "18_19"
        )

        val days = listOf("monday", "tuesday", "wednesday", "thursday", "friday")

        for (day in days) {
            for (slot in timeSlots) {
                val key = "${day}_${slot}"
                val textViewId = resources.getIdentifier(key, "id", packageName) // Use the same ID
                val textView = findViewById<TextView>(textViewId)
                val savedValue = getSavedValue(key)
                textView.text = savedValue
            }
        }
    }

    private fun getSavedValue(key: String): String {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "") ?: ""
    }
}
