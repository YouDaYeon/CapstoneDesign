package com.example.myapplication

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Channel
import com.example.myapplication.ChannelListManager
import com.example.myapplication.ChannelAdapter
import com.example.myapplication.CreateChannelActivity
import com.example.myapplication.ChannelDetail
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ChannelListFragment : Fragment() {

    private lateinit var recyclerViewAssignment: RecyclerView
    private lateinit var recyclerViewMentorMentee: RecyclerView
    private lateinit var recyclerViewCompetition: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var tabtext_group:TextView
    private lateinit var tabtext_mentor:TextView
    private lateinit var tabtext_contest:TextView

    companion object {
        const val CREATE_CHANNEL_REQUEST = 1
        const val CHANNEL_JOIN_REQUEST = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_channel_list, container, false)

        // 각각의 RecyclerView 초기화 및 데이터 표시
        recyclerViewAssignment = view.findViewById(R.id.recyclerViewAssignment)
        recyclerViewAssignment.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewMentorMentee = view.findViewById(R.id.recyclerViewMentorMentee)
        recyclerViewMentorMentee.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewCompetition = view.findViewById(R.id.recyclerViewCompetition)
        recyclerViewCompetition.layoutManager = LinearLayoutManager(requireContext())

        tabtext_group = view.findViewById(R.id.tabtext_group)
        tabtext_mentor = view.findViewById(R.id.tabtext_mentor)
        tabtext_contest = view.findViewById(R.id.tabtext_contest)

        tabtext_group.setOnClickListener {
            tabtext_group.setTextColor(Color.parseColor("#00397E"))
            tabtext_mentor.setTextColor(Color.parseColor("#99B0CB"))
            tabtext_contest.setTextColor(Color.parseColor("#99B0CB"))
            Log.d("messi", "group selected")
            recyclerViewAssignment.setVisibility(View.VISIBLE)
            recyclerViewMentorMentee.setVisibility(View.INVISIBLE)
            recyclerViewCompetition.setVisibility(View.INVISIBLE)
            Log.d("messi", "visible/invisible")
        }
        tabtext_mentor.setOnClickListener {
            tabtext_group.setTextColor(Color.parseColor("#99B0CB"))
            tabtext_mentor.setTextColor(Color.parseColor("#00397E"))
            tabtext_contest.setTextColor(Color.parseColor("#99B0CB"))
            Log.d("messi", "mentor selected")
            recyclerViewAssignment.setVisibility(View.INVISIBLE)
            recyclerViewMentorMentee.setVisibility(View.VISIBLE)
            recyclerViewCompetition.setVisibility(View.INVISIBLE)
            Log.d("messi", "visible/invisible")
        }
        tabtext_contest.setOnClickListener {
            tabtext_group.setTextColor(Color.parseColor("#99B0CB"))
            tabtext_mentor.setTextColor(Color.parseColor("#99B0CB"))
            tabtext_contest.setTextColor(Color.parseColor("#00397E"))
            Log.d("messi", "contest selected")
            recyclerViewAssignment.setVisibility(View.INVISIBLE)
            recyclerViewMentorMentee.setVisibility(View.INVISIBLE)
            recyclerViewCompetition.setVisibility(View.VISIBLE)
        }


        searchEditText = view.findViewById(R.id.searchEditText)
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch() // 검색을 수행하는 함수 호출
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        // "채널 만들기" 버튼 클릭 이벤트 처리
        val createChannelButton = view.findViewById<TextView>(R.id.createChannelButton)
        createChannelButton.setOnClickListener {
            val intent = Intent(requireContext(), CreateChannelActivity::class.java)
            startActivityForResult(intent, CREATE_CHANNEL_REQUEST)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        fetchChannelDataFromFirebase()
    }
    private fun fetchChannelDataFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val channelRef = database.getReference("channels")

        // 나머지 데이터 로딩 및 RecyclerView 업데이트 코드를 여기에 작성

        channelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val channelList = mutableListOf<Channel>()
                for (postSnapshot in snapshot.children) {
                    val channel = postSnapshot.getValue(Channel::class.java)
                    if (channel != null) {
                        channelList.add(channel)
                    }
                }
                ChannelListManager.setChannels(channelList)


                val assignmentList = getChannelsByType("PROJECT")
                val mentorMenteeList = getChannelsByType("MENTOR_MENTEE")
                val competitionList = getChannelsByType("COMPETITION")

                // RecyclerView 업데이트 코드
                val adapterAssignment = ChannelAdapter(assignmentList) { channelId ->
                    val channel = ChannelListManager.getChannelById(channelId)
                    if (channel != null) {
                        // 채널 클릭 이벤트 처리
                        showJoinChannelDialog(channel)
                    }
                }
                recyclerViewAssignment.adapter = adapterAssignment

                val adapterMentorMentee = ChannelAdapter(mentorMenteeList) { channelId ->
                    val channel = ChannelListManager.getChannelById(channelId)
                    if (channel != null) {
                        // 채널 클릭 이벤트 처리
                        showJoinChannelDialog(channel)
                    }
                }
                recyclerViewMentorMentee.adapter = adapterMentorMentee

                val adapterCompetition = ChannelAdapter(competitionList) { channelId ->
                    val channel = ChannelListManager.getChannelById(channelId)
                    if (channel != null) {
                        // 채널 클릭 이벤트 처리
                        showJoinChannelDialog(channel)
                    }
                }
                recyclerViewCompetition.adapter = adapterCompetition
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터 가져오기 실패 시 처리
                // 에러 처리 로직 추가
            }
        })
    }
    private fun performSearch() {
        val searchText = searchEditText.text.toString().toLowerCase()
        val channelList = ChannelListManager.getChannels()

        if (searchText.isNotBlank()) {
            val filteredChannels = channelList.filter { channel ->
                val titleMatches = channel.title.toLowerCase().contains(searchText)
                val lectureMatches = channel.lecture?.toLowerCase()?.contains(searchText) ?: false
                val competitionMatches =
                    channel.competitionName?.toLowerCase()?.contains(searchText) ?: false
                val descriptionMatches =
                    channel.description?.toLowerCase()?.contains(searchText) ?: false

                titleMatches || lectureMatches || competitionMatches || descriptionMatches
            }
            val assignmentList = filteredChannels.filter { it.type == ChannelType.PROJECT }
            val mentorMenteeList = filteredChannels.filter { it.type == ChannelType.MENTOR_MENTEE }
            val competitionList = filteredChannels.filter { it.type == ChannelType.COMPETITION }

            // 각 유형에 맞게 RecyclerView를 업데이트합니다
            updateAssignmentRecyclerView(assignmentList)
            updateMentorMenteeRecyclerView(mentorMenteeList)
            updateCompetitionRecyclerView(competitionList)
        } else {
            // 검색어가 비어 있을 때는 전체 채널 목록을 표시
            val allChannels = ChannelListManager.getChannels()
            updateAssignmentRecyclerView(allChannels.filter { it.type == ChannelType.PROJECT })
            updateMentorMenteeRecyclerView(allChannels.filter { it.type == ChannelType.MENTOR_MENTEE })
            updateCompetitionRecyclerView(allChannels.filter { it.type == ChannelType.COMPETITION })
        }
    }
    private fun updateAssignmentRecyclerView(list: List<Channel>) {
        val adapterAssignment = ChannelAdapter(list) { channelId ->
            val channel = ChannelListManager.getChannelById(channelId)
            if (channel != null) {
                showJoinChannelDialog(channel)
            }
        }
        recyclerViewAssignment.adapter = adapterAssignment
        adapterAssignment.notifyDataSetChanged()
    }

    private fun updateMentorMenteeRecyclerView(list: List<Channel>) {
        val adapterMentorMentee = ChannelAdapter(list) { channelId ->
            val channel = ChannelListManager.getChannelById(channelId)
            if (channel != null) {
                showJoinChannelDialog(channel)
            }
        }
        recyclerViewMentorMentee.adapter = adapterMentorMentee
        adapterMentorMentee.notifyDataSetChanged()
    }

    private fun updateCompetitionRecyclerView(list: List<Channel>) {
        val adapterCompetition = ChannelAdapter(list) { channelId ->
            val channel = ChannelListManager.getChannelById(channelId)
            if (channel != null) {
                showJoinChannelDialog(channel)
            }
        }
        recyclerViewCompetition.adapter = adapterCompetition
        adapterCompetition.notifyDataSetChanged()
    }


    private fun getChannelsByType(channelType: String): List<Channel> {
        return ChannelListManager.getChannels().filter { it.type.name == channelType }
    }

    private fun showJoinChannelDialog(channel: Channel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("채널 참가")
            .setMessage("${channel.title}에 참가하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                // TODO: Implement channel joining logic
                Toast.makeText(requireContext(), "${channel.title}에 참가합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), ChannelDetail::class.java)
                intent.putExtra("channel_id", channel.id)
                startActivityForResult(intent, CHANNEL_JOIN_REQUEST)
                dialog.dismiss()
            }
            .setNegativeButton("아니오") { dialog, _ ->
                Toast.makeText(requireContext(), "${channel.title} 참가를 취소합니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_CHANNEL_REQUEST && resultCode == RESULT_OK) {

            // CreateChannelActivity에서 돌아왔고, 결과가 OK인 경우
            val updatedAssignmentList = getChannelsByType("PROJECT")
            val adapterAssignment = ChannelAdapter(updatedAssignmentList) { channelId ->
                val channel = ChannelListManager.getChannelById(channelId)
                if (channel != null) {
                    // 채널 클릭 이벤트 처리
                    showJoinChannelDialog(channel)
                }
            }
            recyclerViewAssignment.adapter = adapterAssignment
            adapterAssignment.notifyDataSetChanged()

            val updatedMentorMenteeList = getChannelsByType("MENTOR_MENTEE")
            val adapterMentorMentee = ChannelAdapter(updatedMentorMenteeList) { channelId ->
                val channel = ChannelListManager.getChannelById(channelId)
                if (channel != null) {
                    // 채널 클릭 이벤트 처리
                    showJoinChannelDialog(channel)
                }
            }
            recyclerViewMentorMentee.adapter = adapterMentorMentee
            adapterMentorMentee.notifyDataSetChanged()

            val updatedCompetitionList = getChannelsByType("COMPETITION")
            val adapterCompetition = ChannelAdapter(updatedCompetitionList) { channelId ->
                val channel = ChannelListManager.getChannelById(channelId)
                if (channel != null) {
                    // 채널 클릭 이벤트 처리
                    showJoinChannelDialog(channel)
                }
            }

            recyclerViewCompetition.adapter = adapterCompetition
            adapterCompetition.notifyDataSetChanged()
        }
    }
}
