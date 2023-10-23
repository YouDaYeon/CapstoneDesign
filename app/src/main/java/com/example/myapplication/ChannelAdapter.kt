package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Channel
import com.example.myapplication.R

class ChannelAdapter(
    private val channelList: List<Channel>,
    private val joinChannelClickListener: (String) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channel = channelList[position]

        // 뷰 홀더의 TextView에 채널 정보를 설정
        holder.channelNameTextView.text = channel.title
        holder.channelDescriptionTextView.text = channel.description

        // 채널 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            joinChannelClickListener(channel.id)
        }
    }

    override fun getItemCount(): Int {
        return channelList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val channelNameTextView: TextView = itemView.findViewById(R.id.channelNameTextView)
        val channelDescriptionTextView: TextView = itemView.findViewById(R.id.channelDescriptionTextView)
    }
}
