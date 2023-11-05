package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CardViewActivity : AppCompatActivity() {

    private lateinit var Name: String
    private lateinit var Uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)


        //넘어온 데이터 변수에 담기
        Name = intent.getStringExtra("name").toString()
        Uid = intent.getStringExtra("uId").toString()


        // 명함에 이름 설정
        val userName: TextView = findViewById(R.id.studentname)
        userName.text = Name

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