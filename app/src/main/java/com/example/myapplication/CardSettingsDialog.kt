package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.myapplication.databinding.CardSettingsBinding

class CardSettingsDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.card_settings, null)

        Log.d("messi", "DialogFragment appeared")

        // card_settings 바인딩
        lateinit var binding: CardSettingsBinding
        binding = CardSettingsBinding.inflate(layoutInflater)
        Log.d("messi", "binding complete")


        // 버튼 1, 2, 3 정의
        var button1:Button = view.findViewById(R.id.button1)
        var button2:Button = view.findViewById(R.id.button2)
        var button3:Button = view.findViewById(R.id.button3)

        // 학년, 학과, 버튼클릭 변수 초기화
        var selectedGradeSave:String = ""
        var selectedMajorSave:String = ""
        var clickedbutton:String = "1"
        var studentIDSave:String = "B893285"

        // 이모지 변경 함수
        fun getEmoji(unicode: Int): String {
            return String(Character.toChars(unicode))
        }

        // 버튼 1, 2, 3 클릭헀을 때
        button1.setOnClickListener {
            Log.d("messi", "button1 clicked")
            button1.setText(getEmoji(0x2714))
            button1.setTextColor(Color.RED)
            button2.setText("2")
            button2.setTextColor(Color.BLACK)
            button3.setText("3")
            button3.setTextColor(Color.BLACK)
            clickedbutton = "1"
        }
        button2.setOnClickListener {
            Log.d("messi", "button2 clicked")
            button1.setText("1")
            button1.setTextColor(Color.BLACK)
            button2.setText(getEmoji(0x2714))
            button2.setTextColor(Color.RED)
            button3.setText("3")
            button3.setTextColor(Color.BLACK)
            clickedbutton = "2"
        }
        button3.setOnClickListener {
            Log.d("messi", "button3 clicked")
            button1.setText("1")
            button1.setTextColor(Color.BLACK)
            button2.setText("2")
            button2.setTextColor(Color.BLACK)
            button3.setText(getEmoji(0x2714))
            button3.setTextColor(Color.RED)
            clickedbutton = "3"
        }


        // 학번 입력 EditText
        val studentIDinput:EditText = view.findViewById(R.id.studentIDinput)


        // 학년 선택 스피너
        val studentgradelist = resources.getStringArray(R.array.studentgradelist)
        val spinner1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, studentgradelist)
        spinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var list1:Spinner = view.findViewById(R.id.studentgradelist)
        list1.adapter = spinner1
        list1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedGradeSave = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedGradeSave = ""
            }
        }


        // 학과 선택 스피너
        val studentmajorlist = resources.getStringArray(R.array.majorlist)
        val spinner2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, studentmajorlist)
        spinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var list2:Spinner = view.findViewById(R.id.studentmajorlist)
        list2.adapter = spinner2
        list2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedMajorSave = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedMajorSave = ""
            }
        }

        var string1 = ArrayList<String>()

        val intent = Intent()
        intent.putExtra("selectedGradeSave", selectedGradeSave)
        intent.putExtra("selectedMajorSave", selectedMajorSave)
        intent.putExtra("studentIDSave", studentIDSave)
        intent.putExtra("clickedbutton", clickedbutton)

        // 저장 버튼 클릭
        val studentSaveBtn:TextView = view.findViewById(R.id.studentSaveBtn)
        Log.d("messi", "studentSaveBtn : "+ studentSaveBtn.toString())
        studentSaveBtn.setOnClickListener {
            studentIDSave = if (studentIDinput.text.isNotEmpty()) studentIDinput.text.toString() else ""
            Log.d("messi", "studentSaveBtn clicked")
            Log.d("messi", "selection : " + studentIDSave + ", " + selectedGradeSave + ", " + selectedMajorSave + ", " + clickedbutton)
        }
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}