package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ChannelHostCardViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_host_card_view)

        val requestTeamMemberButton = findViewById<Button>(R.id.requestTeamMemberButton)

        // "팀원 요청하기" 버튼 클릭 이벤트 처리
        requestTeamMemberButton.setOnClickListener {
            // AlertDialog를 생성하여 메시지를 표시
            AlertDialog.Builder(this)
                .setTitle("팀원 요청")
                .setMessage("이 사용자에게 팀원 요청을 보내시겠습니까?")
                .setPositiveButton("예") { _, _ ->
                    Toast.makeText(this, "팀원 요청을 보냈습니다.", Toast.LENGTH_SHORT).show()

                }
                .setNegativeButton("아니요") { _, _ ->
                    // 사용자가 "아니요"를 선택한 경우 처리
                }
                .show()
        }
    }
}