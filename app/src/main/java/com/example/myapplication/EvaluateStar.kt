package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EvaluateStar : AppCompatActivity() {

    private lateinit var ratingBars: List<RatingBar>
    private lateinit var submitButton: Button
    private lateinit var averageRatingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evaluate_star)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 별점 입력을 위한 RatingBar 초기화
        ratingBars = listOf(
            findViewById(R.id.ratingBarQ1),
            findViewById(R.id.ratingBarQ2),
            findViewById(R.id.ratingBarQ3),
            findViewById(R.id.ratingBarQ4),
            findViewById(R.id.ratingBarQ5)
        )
        averageRatingTextView = findViewById(R.id.averageRatingTextView)

        // 제출 버튼 초기화
        submitButton = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            calculateAverageRating()
        }
    }

    private fun calculateAverageRating() {
        var totalRating = 0.0

        for (ratingBar in ratingBars) {
            totalRating += ratingBar.rating
        }

        val averageRating = totalRating / ratingBars.size

        averageRatingTextView.text = "평균 점수: ${"%.1f".format(averageRating)}"

        averageRatingTextView.visibility = View.VISIBLE

        showConfirmationDialog(averageRating)

    }
    private fun showConfirmationDialog(averageRating: Double) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("평가를 저장하시겠습니까?")
        builder.setMessage("평균 점수: ${"%.1f".format(averageRating)}")
        builder.setPositiveButton("예") { _, _ ->

            // 저장이 성공적으로 완료되면 사용자에게 메시지를 표시합니다.
            val successDialog = AlertDialog.Builder(this)
            successDialog.setTitle("저장 완료")
            successDialog.setMessage("평가가 성공적으로 저장되었습니다.")
            successDialog.setPositiveButton("확인") { _, _ ->
                // 확인 버튼을 누르면 현재 액티비티를 종료하고 이전 화면으로 돌아갑니다.
                finish()
            }
            successDialog.show()
        }
        builder.setNegativeButton("아니요") { _, _ ->
            // 사용자가 "아니요"를 선택한 경우 아무 작업도 수행하지 않습니다.
        }
        builder.show()
    }

}