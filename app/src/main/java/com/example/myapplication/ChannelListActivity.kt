package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Channel
import com.example.myapplication.ChannelAdapter

class ChannelListActivity : AppCompatActivity() {

    private lateinit var channelRecyclerView: RecyclerView

    companion object {
        const val CREATE_CHANNEL_REQUEST = 1
        const val CHANNEL_JOIN_REQUEST = 2
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_channel_list)

        channelRecyclerView = findViewById(R.id.channelRecyclerView)

        // 데이터를 다시 로드하고 RecyclerView를 업데이트
        val updatedChannelList = ChannelListManager.getChannels()

        val adapter = ChannelAdapter(updatedChannelList) { channelId ->
            val channel = ChannelListManager.getChannelById(channelId)
            if (channel != null) {
                // 채널 클릭 이벤트 처리
                showJoinChannelDialog(channel)
            }
        }

        channelRecyclerView.adapter = adapter
        channelRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

    private fun showJoinChannelDialog(channel: Channel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("채널 참가")
            .setMessage("${channel.title}에 참가하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                // TODO: Implement channel joining logic
                Toast.makeText(this, "${channel.title}에 참가합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ChannelDetail::class.java)
                intent.putExtra("channel_id", channel.id) // 채널의 고유 ID를 전달
                startActivityForResult(intent, CHANNEL_JOIN_REQUEST)
                dialog.dismiss()
            }
            .setNegativeButton("아니오") { dialog, _ ->
                Toast.makeText(this, "${channel.title} 참가를 취소합니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .show()
    }

    fun onCreateChannelButtonClick(view: View) {
        val intent = Intent(this, CreateChannelActivity::class.java)
        startActivityForResult(intent, CREATE_CHANNEL_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_CHANNEL_REQUEST && resultCode == RESULT_OK) {
            // CreateChannelActivity에서 돌아왔고, 결과가 OK인 경우
            val updatedChannelList = ChannelListManager.getChannels()
            val adapter = ChannelAdapter(updatedChannelList) { channelId ->
                val channel = ChannelListManager.getChannelById(channelId)
                if (channel != null) {
                    // 채널 클릭 이벤트 처리
                    showJoinChannelDialog(channel)
                }
            }
            channelRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}




