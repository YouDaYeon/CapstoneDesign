package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

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


    }
}