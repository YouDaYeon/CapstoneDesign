package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.CardSettingsBinding
import com.example.myapplication.databinding.FragmentStCardDesignBinding

class StCardDesignFragment : Fragment() {

    private lateinit var settingsBtn: ImageView
    private lateinit var studentMajorBtn: TextView
    private lateinit var studentIDBtn: TextView
    private lateinit var studentMajorName: String
    private lateinit var studentIDName: String

    val binding by lazy { FragmentStCardDesignBinding.inflate(layoutInflater) }

    var count = 1


    // private var _binding: FragmentStCardDesignBinding? = null
    // private val binding get() = _binding!!



    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        val binding = FragmentStCardDesignBinding.inflate(layoutInflater)

        // val studentID = binding.studentID.getText()


        val studentID = binding.studentID
        val studentMajor = binding.studentMajor
        val studentGrade = binding.studentGrade
        if(count==2) {
            Log.d("messi", "before : "+ studentGrade.getText() + ", " + studentMajor.getText() + ", " + studentID.getText())

            /*
            Log.d("messi", "getting : "+ CardSettings().writtenStudentID + ", " + CardSettings().selectedMajorSave + ", " + CardSettings().selectedGradeSave)
            studentID.setText(CardSettings().writtenStudentID)
            studentMajor.setText(CardSettings().selectedMajorSave)
            studentGrade.setText(CardSettings().selectedGradeSave)

            */
            Log.d("messi", "after : "+ studentGrade.getText() + ", " + studentMajor.getText() + ", " + studentID.getText())

        }

        // val selectedGradeSave = sharedPreferences.getString("selectedGradeSave", "nuull")
        // val selectedMajorSave = sharedPreferences.getString("selectedMajorSave", "nuull")
        // val writtenStudentID = sharedPreferences.getString("writtenStudentID", "nuull")
        // val selectedButtonNum = sharedPreferences.getString("selectedButtonNum", "nuull")
        // Log.d("messi", "getString : $selectedGradeSave, $selectedMajorSave, $writtenStudentID, $selectedButtonNum")
    }

    lateinit var navController : NavController


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_st_card_design, null)
        val settingsBtn = view.findViewById<ImageView>(R.id.settingsBtn)
        val studentMajorBtn = view.findViewById<TextView>(R.id.studentMajor)
        val studentIDBtn = view.findViewById<TextView>(R.id.studentID)


        // navController = Navigation.findNavController(view)
        /*
        settingsBtn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("messi", "button clicked")
                // val intent = Intent(context, CardSettings::class.java)
                // startActivity(intent)
                // 다른 액티비티에서 전환할 때
                // activity?.finish()
                // navController.navigate(R.id.action_stCardDesignFragment2_to_cardSettings)
                Log.d("messi", "activated")
                count = 2
            }
        })
         */
        settingsBtn.setOnClickListener {
            // Log.d("messi", "student name : "+studentMajorName)
            // val intent = Intent(context, CardSettings::class.java)
            // startActivity(intent)

            // val builder = AlertDialog.Builder(requireContext())
            // val inflater = requireActivity().layoutInflater
            // val dialogView = inflater.inflate(R.layout.card_settings, null)
            // builder.setView(dialogView)
            // val dialog = builder.create()
            // dialog.show()

            val dialog = CardSettingsDialog()
            dialog.show(childFragmentManager, "CardSettingsDialog")
            Log.d("messi", "Card Settings shown")
        }

        studentMajorBtn.setOnClickListener {
            // Log.d("messi", "student name : "+studentMajorName)
            val builder = AlertDialog.Builder(requireContext())
            // majorinfomajor = findViewById(R.id.majorinfomajor)
            // Log.d("messi", "majorinfo : "+ majorinfomajor)
            // Log.d("messi", "major : "+ major)
            // Log.d("messi", "majorinfo : "+ majorinfomajor)
            // Log.d("messi", "major : "+ major)
            val dialogView = layoutInflater.inflate(R.layout.major_informations, null)
            builder.setView(dialogView).show()
        }
        studentIDBtn.setOnClickListener {
            // Log.d("messi", "student name : "+studentMajorName)

            val builder = AlertDialog.Builder(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.graduation_requirements, null)
            builder.setView(dialogView).show()
        }

        /*
        Log.d("messi", "studentGrade will get : "+ arguments?.getString("selectedGradeSave"))
        val studentGrade: String? = arguments?.getString("selectedGradeSave")
        Log.d("messi", "studentGrade got : $studentGrade")
         */
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs",Context.MODE_PRIVATE)
    }
}