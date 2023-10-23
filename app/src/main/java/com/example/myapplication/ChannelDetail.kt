package com.example.myapplication

import android.os.Bundle
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
        val channelAdditionalInfoTextView = findViewById<TextView>(R.id.channelAdditionalInfoTextView)
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
        AlertDialog.Builder(this)
            .setTitle("명함 등록")
            .setMessage("명함을 등록하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                // Drawable 리소스로 추가한 이미지 파일을 channelImageView에 표시
                channelImageView?.setImageResource(R.drawable.cardimage)
                channelImageView?.visibility = View.VISIBLE
                isBusinessCardRegistered = true // 명함 등록 상태 업데이트
                dialog.dismiss()
            }
            .setNegativeButton("아니요") { dialog, _ ->
                // 이미지를 선택만 하고 등록하지 않음.
                dialog.dismiss()
            }
            .show()
    }

    private fun showUnregisterConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("명함 삭제")
            .setMessage("명함을 삭제하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                // 명함 삭제
                channelImageView?.setImageResource(0)
                channelImageView?.visibility = View.GONE
                isBusinessCardRegistered = false // 명함 등록 상태 업데이트
                dialog.dismiss()
            }
            .setNegativeButton("아니요") { dialog, _ ->
                // 삭제하지 않음.
                dialog.dismiss()
            }
            .show()
    }
}