package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TimetableActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val timeSlots = listOf(
        "9_10", "10_11", "11_12", "12_13", "13_14",
        "14_15", "15_16", "16_17", "17_18", "18_19"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        for (day in listOf("monday", "tuesday", "wednesday", "thursday", "friday")) {
            for (slot in timeSlots) {
                val editTextId = resources.getIdentifier("${day}_${slot}", "id", packageName)
                val editText = findViewById<EditText>(editTextId)
                editText.setText(sharedPreferences.getString("${day}_${slot}", ""))
            }
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val editor = sharedPreferences.edit()

            for (day in listOf("monday", "tuesday", "wednesday", "thursday", "friday")) {
                for (slot in timeSlots) {
                    val editTextId = resources.getIdentifier("${day}_${slot}", "id", packageName)
                    val editText = findViewById<EditText>(editTextId)
                    val key = "${day}_${slot}"
                    editor.putString(key, editText.text.toString())
                }
            }

            // 변경 내용을 동기적으로 저장
            editor.commit()

            Toast.makeText(this, "시간표가 저장되었습니다!", Toast.LENGTH_SHORT).show()

            // MyPage 화면으로 이동
            val intent = Intent(this, MypageFragment::class.java)
            startActivity(intent)
        }
    }

    // onSaveInstanceState 메서드를 오버라이드하여 상태 저장을 구현
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val editor = sharedPreferences.edit()

        for (day in listOf("monday", "tuesday", "wednesday", "thursday", "friday")) {
            for (slot in timeSlots) {
                val editTextId = resources.getIdentifier("${day}_${slot}", "id", packageName)
                val editText = findViewById<EditText>(editTextId)
                val key = "${day}_${slot}"
                editor.putString(key, editText.text.toString())
            }
        }

        editor.apply()
    }
}
