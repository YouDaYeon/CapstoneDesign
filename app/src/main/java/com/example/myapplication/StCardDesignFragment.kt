package com.example.myapplication


import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentStCardDesignBinding

class StCardDesignFragment : Fragment() {

    val binding by lazy { FragmentStCardDesignBinding.inflate(layoutInflater) }

    var checkNum = 1

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("messi", "onCreate turned")
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        val binding = FragmentStCardDesignBinding.inflate(layoutInflater)
    }



    var selectedGradeSave:String = "4학년"
    var selectedMajorSave:String = "소프트웨어융합학과"
    var studentIDSave:String = "B893285"

    lateinit var selectedGrade: String
    lateinit var selectedMajor: String
    lateinit var studentID: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("messi", "onCreateView turned")
        val view = inflater.inflate(R.layout.fragment_st_card_design, null)
        val settingsBtn = view.findViewById<ImageView>(R.id.settingsBtn)
        val profileImageCard = view.findViewById<ImageView>(R.id.profileimageCard)
        val studentMajorBtn = view.findViewById<TextView>(R.id.studentMajor)
        val studentIDBtn = view.findViewById<TextView>(R.id.studentID)
        val studentGradeBtn = view.findViewById<TextView>(R.id.studentGrade)

        /// val selectedGradeSave = arguments?.getString("selectedGradeSave")
        // val selectedMajorSave = arguments?.getString("selectedMajorSave")
        // val studentIDSave = arguments?.getString("studentIDSave")
        // val clickedbutton = arguments?.getString("clickedbutton")
        selectedGradeSave = arguments?.getString("selectedGradeSave").toString()
        selectedMajorSave = arguments?.getString("selectedMajorSave").toString()
        studentIDSave = arguments?.getString("studentIDSave").toString()
        val clickedbutton = arguments?.getString("clickedbutton")
        Log.d("messi", "argumentsgot : " + studentIDSave + ", " + selectedGradeSave + ", " + selectedMajorSave + ", " + clickedbutton)



        selectedGrade = selectedGradeSave
        selectedMajor = selectedMajorSave
        studentID = studentIDSave
        Log.d("messi", "argumentsgot : " + studentID + ", " + selectedGrade + ", " + selectedMajor)

        profileImageCard.setOnClickListener {
            val studentMajorBtn = view.findViewById<TextView>(R.id.studentMajor)
            val studentGradeBtn = view.findViewById<TextView>(R.id.studentGrade)
            val studentIDBtn = view.findViewById<TextView>(R.id.studentID)

            Log.d("messi", "argumentsgot : $studentID, $selectedGrade, $selectedMajor")
            studentMajorBtn.text = "소프트웨어융합학과"
            studentGradeBtn.text = "4학년"
            studentIDBtn.text = "B893285"
        }

        settingsBtn.setOnClickListener {
            val dialog = CardSettingsDialog()
            dialog.show(childFragmentManager, "CardSettingsDialog")
            Log.d("messi", "Card Settings shown")
        }

        studentMajorBtn.setOnClickListener {
            // Log.d("messi", "student name : "+studentMajorName)
            val builder = AlertDialog.Builder(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.major_informations, null)
            builder.setView(dialogView).show()
        }
        studentIDBtn.setOnClickListener {
            // Log.d("messi", "student name : "+studentMajorName)

            val builder = AlertDialog.Builder(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.graduation_requirements, null)
            builder.setView(dialogView).show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("messi", "onViewCreated turned")
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("my_prefs",Context.MODE_PRIVATE)

        /*
        val studentMajorBtn = view.findViewById<TextView>(R.id.studentMajor)
        val studentIDBtn = view.findViewById<TextView>(R.id.studentID)
        val studentGradeBtn = view.findViewById<TextView>(R.id.studentGrade)

        val checkNum = arguments?.getInt("checkNum")

        if(checkNum==2) {
            studentMajorBtn.setText(selectedMajorSave)
            studentGradeBtn.setText(selectedGradeSave)
            studentIDBtn.setText(studentIDSave)
            Log.d("messi", "setText turned")

            Handler(Looper.getMainLooper()).postDelayed( {
                studentMajorBtn.invalidate()
                studentGradeBtn.invalidate()
                studentIDBtn.invalidate()

                studentMajorBtn.requestLayout()
                studentGradeBtn.requestLayout()
                studentIDBtn.requestLayout()
                Log.d("messi", "selectiongetafter : " + studentMajorBtn.text.toString() + ", " +  studentGradeBtn.text.toString() + ", " +  studentIDBtn.text.toString())
            },1000)
        }
         */


    }
}