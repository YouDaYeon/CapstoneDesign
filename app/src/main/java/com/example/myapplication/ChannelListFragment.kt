package com.example.myapplication

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        // "채널 만들기" 버튼 클릭 이벤트 처리
        val createChannelButton = view.findViewById<Button>(R.id.createChannelButton)
        createChannelButton.setOnClickListener {
            val intent = Intent(requireContext(), CreateChannelActivity::class.java)
            startActivityForResult(intent, CREATE_CHANNEL_REQUEST)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        // Firebase Realtime Database에서 채널 데이터를 가져오는 코드를 이 위치에 추가
        fetchChannelDataFromFirebase()
    }
    private fun fetchChannelDataFromFirebase() {
        val auth = Firebase.auth
        val user = auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("user").child(user?.uid.toString())
        val channelRef = userRef.child("channels")

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
                for (channel in channelList) {
                    ChannelListManager.initialize(channelList)
                }

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


