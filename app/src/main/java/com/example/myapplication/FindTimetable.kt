package com.example.myapplication

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class FindTimetable : AppCompatActivity() {
    private lateinit var resultDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tableimage)

        val findFreeTimeButton = findViewById<Button>(R.id.findFreeTimeButton)

        findFreeTimeButton.setOnClickListener {
            // Toast 메시지 표시
            Toast.makeText(this, "겹치는 공강 시간을 탐색 중 입니다...", Toast.LENGTH_SHORT).show()

            // 3초 뒤에 결과 다이얼로그 표시
            Handler(Looper.getMainLooper()).postDelayed({
                showResultDialog()
            }, 3000)
        }
    }

    private fun showResultDialog() {
        resultDialog = Dialog(this)
        resultDialog.setContentView(R.layout.result_dialog)

        val resultTextView = resultDialog.findViewById<TextView>(R.id.resultTextView)
        val closeButton = resultDialog.findViewById<Button>(R.id.closeButton)

        val resultMessage = "[겹치는 공강 시간]\n" +
                "월요일 : 9시-10시, 15시-19시\n" +
                "화요일 : 9시-19시\n" +
                "수요일 : 9시-11시, 16시-19시\n" +
                "목요일 : 14시-15시, 18시-19시\n" +
                "금요일 : 9시-10시, 15시-19시"

        resultTextView.text = resultMessage

        closeButton.setOnClickListener {
            resultDialog.dismiss()
        }

        resultDialog.show()
    }
}
