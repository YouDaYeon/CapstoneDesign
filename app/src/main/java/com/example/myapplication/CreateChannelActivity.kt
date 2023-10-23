package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateChannelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_channel)

        val channelTitleEditText = findViewById<EditText>(R.id.channelTitleEditText)
        val channelDescriptionEditText = findViewById<EditText>(R.id.channelDescriptionEditText)
        val createChannelButton = findViewById<Button>(R.id.createChannelButton)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val layoutAssignmentQuestion = findViewById<View>(R.id.layoutAssignmentQuestion)
        val lectureNameEditText = findViewById<EditText>(R.id.lectureNameEditText)
        val classSectionEditText = findViewById<EditText>(R.id.classSectionEditText)
        val layoutMentorMenteeQuestion = findViewById<View>(R.id.layoutMentorMenteeQuestion)
        val radioMentor = findViewById<RadioButton>(R.id.radioMentor)
        val radioMentee = findViewById<RadioButton>(R.id.radioMentee)
        val layoutCompetitionQuestion = findViewById<View>(R.id.layoutCompetitionQuestion)
        val competitionNameEditText = findViewById<EditText>(R.id.competitionNameEditText)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioGroupAssignment -> {
                    layoutAssignmentQuestion.visibility = View.VISIBLE
                    layoutMentorMenteeQuestion.visibility = View.GONE
                    layoutCompetitionQuestion.visibility = View.GONE
                }

                R.id.radioGroupMentorMentee -> {
                    layoutAssignmentQuestion.visibility = View.GONE
                    layoutMentorMenteeQuestion.visibility = View.VISIBLE
                    layoutCompetitionQuestion.visibility = View.GONE
                }

                R.id.radioGroupCompetition -> {
                    layoutAssignmentQuestion.visibility = View.GONE
                    layoutMentorMenteeQuestion.visibility = View.GONE
                    layoutCompetitionQuestion.visibility = View.VISIBLE
                }
            }
        }

        createChannelButton.setOnClickListener {
            val channelTitle = channelTitleEditText.text.toString()
            val channelDescription = channelDescriptionEditText.text.toString()

            val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

            if (channelTitle.isNotEmpty() && selectedRadioButton != null) {
                val channelType = when (selectedRadioButton.id) {
                    R.id.radioGroupAssignment -> ChannelType.PROJECT
                    R.id.radioGroupMentorMentee -> ChannelType.MENTOR_MENTEE
                    R.id.radioGroupCompetition -> ChannelType.COMPETITION
                    else -> ChannelType.PROJECT // Default to PROJECT if none is selected
                }

                    var channel = Channel(
                        title = channelTitle,
                        description = channelDescription,
                        type = channelType
                    )

                    if (channelType == ChannelType.PROJECT) {
                        val lectureName = lectureNameEditText.text.toString()
                        val classSection = classSectionEditText.text.toString()
                        channel.lecture = lectureName
                        channel.className = classSection
                    } else if (channelType == ChannelType.MENTOR_MENTEE) {
                        val mentorOrMentee = when {
                            radioMentor.isChecked -> "멘토"
                            radioMentee.isChecked -> "멘티"
                            else -> ""
                        }
                        channel.role = mentorOrMentee
                    } else if (channelType == ChannelType.COMPETITION) {
                        val competitionName = competitionNameEditText.text.toString()
                        channel.competitionName = competitionName
                    }

                    val database = Firebase.database
                    val channelRef = database.getReference("channels")

                    val channelKey = channelRef.push().key
                    val channelData = channel.toMap()
                    if (channelKey != null) {
                        channelRef.child(channelKey).setValue(channelData)
                    }

                    ChannelListManager.addChannel(channel)

                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this, "채널 제목과 채널 유형을 선택하세요.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}



