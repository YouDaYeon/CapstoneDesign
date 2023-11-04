package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityStCardDesignBinding
import com.example.myapplication.databinding.MajorInformationsBinding

class StCardDesignActivity : AppCompatActivity() {

    private lateinit var settingsBtn: ImageView
    private lateinit var studentMajorBtn: TextView
    private lateinit var studentIDBtn : TextView
    private lateinit var studentMajorName: String
    private lateinit var studentIDName : String
    val binding by lazy{ActivityStCardDesignBinding.inflate(layoutInflater)}

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(binding.root)

        // var grade = intent.getStringExtra("grade1111")
        // var major = intent.getStringExtra("major1111")
        // Log.e("messi","메인에서 받아온 id : $grade, pw : $major")

        var string2: ArrayList<String> = intent.getStringArrayListExtra("array")!!
        Log.d("messi", "selectedGrade : " + string2)
        var grade: String = string2[0]
        var major: String = string2[1]
        var studentID: String = string2[2]
        var selectedButton: String = string2[3]
        var pickedImg: String = string2[4]
        Log.d(
            "messi",
            "메인에서 받아온 grade : $grade, major : $major, id : $studentID, button : $selectedButton"
        )

        var pickedImgURI: Uri = Uri.parse(pickedImg)


        binding.studentGrade.setText(grade)
        binding.studentMajor.setText(major)
        binding.studentID.setText(studentID)
        binding.profileimageCard.setImageURI(pickedImgURI)
        if (selectedButton == "1") {
            binding.imageView1.visibility = View.VISIBLE
            binding.imageView2.visibility = View.GONE
            binding.imageView3.visibility = View.GONE
        } else if (selectedButton == "2") {
            binding.imageView1.visibility = View.GONE
            binding.imageView2.visibility = View.VISIBLE
            binding.imageView3.visibility = View.GONE
        } else if (selectedButton == "3") {
            binding.imageView1.visibility = View.GONE
            binding.imageView2.visibility = View.GONE
            binding.imageView3.visibility = View.VISIBLE
        }
        settingsBtn = findViewById(R.id.settingsBtn)
        settingsBtn.setOnClickListener {
            val intent1 = Intent(this, CardSettings::class.java)
            startActivity(intent1)
        }
        studentMajorBtn = findViewById(R.id.studentMajor)
        studentIDBtn = findViewById(R.id.studentID)
        studentMajorName = studentMajorBtn.getText().toString()
        studentIDName = studentIDBtn.getText().toString()

        var string1 = ArrayList<String>()

        studentMajorBtn.setOnClickListener {
            Log.d("messi", "student name : "+studentMajorName)
            val builder = AlertDialog.Builder(this)
            // var majorinfomajor:TextView?
            // majorinfomajor = findViewById(R.id.majorinfomajor)
            // Log.d("messi", "majorinfo : "+ majorinfomajor)
            // Log.d("messi", "major : "+ major)
            // majorinfomajor.text = major
            // Log.d("messi", "majorinfo : "+ majorinfomajor)
            // Log.d("messi", "major : "+ major)
            val dialogView = layoutInflater.inflate(R.layout.major_informations, null)
            builder.setView(dialogView).show()
        }


        studentIDBtn.setOnClickListener {
            Log.d("messi", "student name : "+studentMajorName)

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.graduation_requirements, null)
            builder.setView(dialogView).show()
        }
    }

    // var pickedImg: ImageView = findViewById(R.id.profileimageCard)
    // var imgUri: Uri? = CardSettings().pickedImgUriSave
    // Log.d("messi", "imgUri : "+ imgUri)
    // pickedImg.setImageURI(imgUri)

    // var pickedImg: ImageView = findViewById(R.id.profileimageCard)
    // pickedImg = CardSettings().pickedImg!!
    // binding.profileimageCard.setIm

    // pickedImg.setImageURI(CardSettings().pickedImgUriSave)
    // binding.profileimageCard.setImageURI(CardSettings().pickedImgUriSave)

    // val selectedGrade = CardSettings().selectedGradeSave
    // val selectedMajor = CardSettings().selectedMajorSave

    // Log.d("messi", "selectedGrade : "+ selectedGrade)
    // Log.d("messi", "selectedMajor : "+ selectedMajor)

    /*
    var grade: String? = intent.getStringExtra("grade")
    Log.d("messi", "selectedGrade : "+ grade)
    var major: String? = intent.getStringExtra("major")
    Log.d("messi", "selectedMajor : "+ major)
    */
    /*
    if (intent.hasExtra("grade1111") && intent.hasExtra("major1111"))
    {
        var grade = intent.getStringExtra("grade1111")
        var major = intent.getStringExtra("major1111")
        Log.e("messi","메인에서 받아온 id : $grade, pw : $major")
    }
    else
    {
        Log.e("messi", "가져온 데이터 없음")
    }
     */
    // val grade = intent.getStringExtra("grade")
    //binding.studentGrade.setText(intent.getStringExtra("grade"))
    // Log.d("messi", "selectedGrade : "+ grade)
    // binding.studentMajor.setText(intent.getStringExtra("major"))
    // Log.d("messi", "selectedMajor : "+ intent.getStringExtra("major"))
}
