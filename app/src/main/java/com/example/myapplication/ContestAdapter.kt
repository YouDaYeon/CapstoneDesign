package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContestAdapter(private val contestList: List<Contest>) : RecyclerView.Adapter<ContestAdapter.ContestViewHolder>() {
    // 아이템 클릭 리스너를 정의합니다.
    private var onItemClick: ((Contest) -> Unit)? = null

    // 클릭 리스너 설정 메서드
    fun setOnItemClickListener(listener: (Contest) -> Unit) {
        onItemClick = listener
    }

    class ContestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contest_item, parent, false)
        return ContestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val contest = contestList[position]
        holder.nameTextView.text = contest.name // 공모전 제목 설정
        holder.dayTextView.text = contest.day // 날짜 설정

        // 아이템을 클릭할 때의 동작을 정의합니다.
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(contest)
        }
    }

    override fun getItemCount(): Int {
        return contestList.size
    }
}