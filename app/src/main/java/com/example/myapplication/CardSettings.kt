package com.example.myapplication

import android.app.Activity
import android.bluetooth.BluetoothClass.Device.Major
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.CardSettingsBinding
import com.example.myapplication.databinding.ActivityStCardDesignBinding
import com.example.myapplication.databinding.MajorInformationsBinding

class CardSettings : AppCompatActivity() {
    private var cBinding : CardSettingsBinding? = null
    private lateinit var sBinding : ActivityStCardDesignBinding
    private lateinit var mBinding : MajorInformationsBinding
    private val binding get() = cBinding!!

    var selectedGrade: String? = null
    var selectedMajor: String? = null
    var pickedImg: ImageView? = null

    // lateinit var pickedImgSave: ImageView
    var selectedGradeSave: String = ""
    var selectedMajorSave: String = ""

    private lateinit var selectedImage: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var studentSaveButton: TextView
    private lateinit var expendleftButton: ImageButton
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    var selectedButtonNum: String = "1"

    private fun setupSpinnerGrade() {
        val studentgradelist = resources.getStringArray(R.array.studentgradelist)
        val spinner1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, studentgradelist)
        binding.studentgradelist.adapter = spinner1
    }
    private fun setupSpinnerMajor() {
        val studentmajorlist = resources.getStringArray(R.array.majorlist)
        val spinner2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, studentmajorlist)
        binding.studentmajorlist.adapter = spinner2
    }
    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    var imgUriSave: Uri? = null
    var pickedImgUriSave: String = ""
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                selectedImage.setImageURI(imgUri)
                imgUriSave = imgUri
            }
        }

    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(R.layout.card_settings)
        cBinding = CardSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinnerGrade()
        setupSpinnerMajor()

        selectedImage = findViewById(R.id.profileimage)
        selectedImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
            var pickedImgSave:ImageView = selectedImage
        }

        // 학년 스피너 선택
        binding.studentgradelist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedGradeSave = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        // 전공 스피너 선택
        binding.studentmajorlist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMajorSave = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //
        sBinding = ActivityStCardDesignBinding.inflate(layoutInflater)
        val viewS = binding.root
        setContentView(viewS)
        pickedImg = sBinding.profileimageCard

        studentSaveButton = findViewById(R.id.studentSaveBtn)
        expendleftButton = findViewById(R.id.expendleft)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)


        var string1 = ArrayList<String>()

        expendleftButton.setOnClickListener {
            val intent1 = Intent(this, StCardDesignActivity::class.java)
            startActivity(intent1)
        }

        button1.setOnClickListener {
            selectedButtonNum = "1"
            button1.setText(getEmoji(0x2714))
            button1.setTextColor(Color.RED)
            button2.setText("2")
            button2.setTextColor(Color.BLACK)
            button3.setText("3")
            button3.setTextColor(Color.BLACK)
            Log.d("messi", "button1 selected")
        }
        button2.setOnClickListener {
            selectedButtonNum = "2"
            button1.setText("1")
            button1.setTextColor(Color.BLACK)
            button2.setText(getEmoji(0x2714))
            button2.setTextColor(Color.RED)
            button3.setText("3")
            button3.setTextColor(Color.BLACK)
            Log.d("messi", "button2 selected")
        }
        button3.setOnClickListener {
            selectedButtonNum = "3"
            button1.setText("1")
            button1.setTextColor(Color.BLACK)
            button2.setText("2")
            button2.setTextColor(Color.BLACK)
            button3.setText(getEmoji(0x2714))
            button3.setTextColor(Color.RED)
            Log.d("messi", "button3 selected")
        }

        studentSaveButton.setOnClickListener {
            val intent1 = Intent(this, StCardDesignActivity::class.java)
            var writtenStudentID:String = binding.studentIDinput.getText().toString()
            string1.add(selectedGradeSave)
            string1.add(selectedMajorSave)
            string1.add(writtenStudentID)
            string1.add(selectedButtonNum)
            pickedImgUriSave = imgUriSave.toString()
            string1.add(pickedImgUriSave)


            binding.profileimage.setImageURI(imgUriSave)
            Log.d("messi", "selectedGrade : "+ selectedGradeSave)
            Log.d("messi", "selectedMajor : "+ selectedMajorSave)
            Log.d("messi", "writtenStudentID : "+ writtenStudentID)
            Log.d("messi", "selectedButtonNum : "+ selectedButtonNum)
            // Log.d("messi", "pickedImgSave : "+ pickedImgSave)
            Log.d("messi", "string : "+ string1)

            intent1.putStringArrayListExtra("array", string1)
            startActivity(intent1)
        }
    }
}