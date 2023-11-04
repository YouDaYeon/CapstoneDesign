package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class CardViewActivity : AppCompatActivity() {

    private lateinit var Name: String
    private lateinit var Uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        //넘어온 데이터 변수에 담기
        Name = intent.getStringExtra("name").toString()
        Uid = intent.getStringExtra("uId").toString()

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val titleText = "$Name 님의 명함"

        // 툴바의 TextView를 찾아서 텍스트 설정
        val titleTextView: TextView = toolbar.findViewById(R.id.name)
        titleTextView.text = titleText

        // 명함에 이름 설정
        val userName: TextView = findViewById(R.id.studentname)
        userName.text = Name


    }
}