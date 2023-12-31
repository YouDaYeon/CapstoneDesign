package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    interface OnEvaluateClickListener {
        fun onEvaluateClick(user: User)
    }

    private var evaluateClickListener: OnEvaluateClickListener? = null

    fun setOnEvaluateClickListener(listener: OnEvaluateClickListener) {
        evaluateClickListener = listener
    }

    interface OnTimeClickListener {
        fun onTimeClick(user: User)
    }

    private var timeClickListener: OnTimeClickListener? = null

    fun setOnTimeClickListener(listener: OnTimeClickListener) {
        timeClickListener = listener
    }

    /**
     * 화면 설정
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)

        return UserViewHolder(view)
    }
    /**
     * 데이터 설정
     */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        //데이터 담기
        val currentUser = userList[position]

        //화면에 데이터 보여주기
        holder.nameText.text=currentUser.name

        holder.evaluateButton.visibility = View.VISIBLE
        holder.evaluateButton.setOnClickListener {
            evaluateClickListener?.onEvaluateClick(currentUser)
        }

        holder.timetableButton.visibility = View.VISIBLE
        holder.timetableButton.setOnClickListener {
            timeClickListener?.onTimeClick(currentUser)
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(context, CardViewActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uId", currentUser.uId)
            context.startActivity(intent)
        }

        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener{
            val intent = Intent(context, CardViewActivity::class.java)

            //넘길 데이터
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uId", currentUser.uId)

            context.startActivity(intent)
        }
    }

    /**
     * 데이터 갯수 가져오기
     */
    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameText: TextView = itemView.findViewById(R.id.name_text)
        val evaluateButton: Button = itemView.findViewById(R.id.evaluate_Button)
        val timetableButton: Button = itemView.findViewById(R.id.timetable_Button)
    }
}