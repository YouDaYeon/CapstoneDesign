package com.example.myapplication

import android.os.Bundle
import android.text.Layout
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.ChannelType

class ChannelDetail : AppCompatActivity() {

    private var channel: Channel? = null
    private var channelImageView: ImageView? = null
    private var isBusinessCardRegistered = false // 명함 등록 상태를 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_detail)

        val channelId = intent.getStringExtra("channel_id")

        val channelTitleTextView = findViewById<TextView>(R.id.channelTitleTextView)
        val channelDescriptionTextView = findViewById<TextView>(R.id.channelDescriptionTextView)
        val channelTypeTextView = findViewById<TextView>(R.id.channelTypeTextView)
        val channelAdditionalInfoTextView =
            findViewById<TextView>(R.id.channelAdditionalInfoTextView)
        channelImageView = findViewById(R.id.channelImageView)
        val registerBusinessCardButton = findViewById<Button>(R.id.registerBusinessCardButton)
        val unregisterBusinessCardButton = findViewById<Button>(R.id.unregisterBusinessCardButton)

        val channel = ChannelListManager.getChannelById(channelId)

        if (channel != null) {
            channelTitleTextView.text = channel!!.title
            channelDescriptionTextView.text = channel!!.description
            // 채널의 유형에 따라 텍스트 설정
            channelTypeTextView.text = when (channel?.type) {
                ChannelType.PROJECT -> "조별과제"
                ChannelType.MENTOR_MENTEE -> "멘토/멘티"
                ChannelType.COMPETITION -> "공모전"
                else -> "알 수 없음"
            }
            // 채널의 추가 정보 표시
            channelAdditionalInfoTextView.text = when (channel?.type) {
                ChannelType.PROJECT -> "강의: ${channel.lecture}, 분반: ${channel.className}"
                ChannelType.MENTOR_MENTEE -> "역할: ${channel.role}"
                ChannelType.COMPETITION -> "공모전 이름: ${channel.competitionName}"
                else -> "알 수 없음"
            }
        }

        // 명함 등록 버튼 클릭 이벤트
        registerBusinessCardButton.setOnClickListener {
            if (!isBusinessCardRegistered) {
                showConfirmationDialog()
            } else {
                // 이미 명함이 등록된 경우
                Toast.makeText(this, "명함이 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 명함 삭제 버튼 클릭 이벤트
        unregisterBusinessCardButton.setOnClickListener {
            if (isBusinessCardRegistered) {
                showUnregisterConfirmationDialog()
            } else {
                // 등록된 명함이 없는 경우
                Toast.makeText(this, "등록된 명함이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmationDialog() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.cardtemplate, null)

        val dialogImageViews = arrayOf(
            dialogView.findViewById<ImageView>(R.id.imageView1),
            dialogView.findViewById<ImageView>(R.id.imageView2),
            dialogView.findViewById<ImageView>(R.id.imageView3)
        )
        val dialogTextViews = arrayOf(
            dialogView.findViewById<TextView>(R.id.hongIk),
            dialogView.findViewById<TextView>(R.id.studentMajor),
            dialogView.findViewById<TextView>(R.id.studentGrade),
            dialogView.findViewById<TextView>(R.id.studentname),
            dialogView.findViewById<TextView>(R.id.studentID)
        )

        // 이미지뷰에 배경 이미지 설정
        for (i in dialogImageViews.indices) {
            dialogImageViews[i].setImageResource(R.drawable.green_skyblue)
        }

        // 텍스트뷰에 텍스트 설정
        dialogTextViews[0].text = "홍익대학교"
        dialogTextViews[1].text = "소프트웨어융합학과"
        dialogTextViews[2].text = "4학년"
        dialogTextViews[3].text = "허유진"
        dialogTextViews[4].text = "B893285"

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("예") { dialog, _ ->
                channelImageView?.visibility = View.VISIBLE

                for (i in dialogImageViews.indices) {
                    if (dialogImageViews[i].drawable != null) {
                        channelImageView?.setImageDrawable(dialogImageViews[i].drawable)
                    }
                }

                for (i in dialogTextViews.indices) {
                    val text = dialogTextViews[i].text.toString()
                    when (i) {
                        0 -> {
                            channelImageView?.contentDescription = text
                            dialogTextViews[i].text = text
                        }
                        1 -> dialogTextViews[i].text = text
                        2 -> dialogTextViews[i].text = text
                        3 -> dialogTextViews[i].text = text
                        4 -> dialogTextViews[i].text = text
                    }
                }
                isBusinessCardRegistered = true
                dialog.dismiss()
            }
            .setNegativeButton("아니요") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showUnregisterConfirmationDialog() {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.cardtemplate, null)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("예") { dialog, _ ->
                // 명함을 삭제하는 작업 수행
                channelImageView?.visibility = View.GONE

                // 명함 이미지뷰를 지우고 다시 그림
                channelImageView?.setImageDrawable(null)

                isBusinessCardRegistered = false
                dialog.dismiss()
            }
            .setNegativeButton("아니요") { dialog, _ ->
                // 삭제하지 않음
                dialog.dismiss()
            }
            .show()
    }
}
