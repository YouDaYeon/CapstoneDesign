package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.example.myapplication.LogInActivity
import android.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.database.FirebaseDatabase


class MypageFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        mAuth = FirebaseAuth.getInstance()


        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        val signoutButton = view.findViewById<Button>(R.id.signoutButton)
        signoutButton.setOnClickListener {
            showSignoutConfirmationDialog()
        }

        val userNameTextView = view.findViewById<TextView>(R.id.user_name)
        val userUid = mAuth.currentUser?.uid
        if (userUid != null) {
            val databaseReference = FirebaseDatabase.getInstance().reference
            val userRef = databaseReference.child("user").child(userUid)

            userRef.child("name").get()
                .addOnSuccessListener { dataSnapshot ->
                    val userName = dataSnapshot.value as String?
                    if (!userName.isNullOrBlank()) {
                        // 사용자 이름이 유효한 경우, TextView에 설정
                        userNameTextView.text = userName
                    }
                }
                .addOnFailureListener { e ->
                    // 사용자 이름을 가져오는 데 실패한 경우에 대한 처리
                    Log.e("TAG", "Error getting user name", e)
                }
        }

        return view
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("로그아웃")
            .setMessage("정말로 로그아웃을 진행하시겠습니까?")
            .setPositiveButton("예") { dialog, which ->
                // "예"를 누른 경우 로그아웃 처리
                mAuth.signOut()
                val intent = Intent(requireContext(), LogInActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            .setNegativeButton("아니요") { dialog, which ->
                // "아니요"를 누른 경우 다이얼로그 닫기
                dialog.dismiss()
            }
            .show()
    }

    private fun showSignoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("회원탈퇴")
            .setMessage("정말로 회원탈퇴를 진행하시겠습니까? 모든 정보가 삭제됩니다.")
            .setPositiveButton("예") { dialog, which ->
                val userUid = mAuth.currentUser?.uid
                if (userUid != null) {
                    val databaseReference = FirebaseDatabase.getInstance().reference
                    val userRef = databaseReference.child("user").child(userUid)

                    userRef.removeValue()
                        .addOnCompleteListener { databaseTask ->
                            if (databaseTask.isSuccessful) {
                                // 사용자 데이터 삭제 성공
                                mAuth.currentUser?.delete()
                                    ?.addOnCompleteListener { authTask ->
                                        if (authTask.isSuccessful) {
                                            // 사용자 계정 삭제 성공
                                            val intent = Intent(requireContext(), LogInActivity::class.java)
                                            startActivity(intent)
                                            activity?.finish()
                                        } else {
                                            // 사용자 계정 삭제 실패
                                            showError("사용자 계정 삭제 실패")
                                        }
                                    }
                            } else {
                                // 사용자 데이터 삭제 실패
                                showError("데이터 삭제 실패")
                            }
                        }
                }
            }
            .setNegativeButton("아니요") { dialog, which ->
                // "아니요"를 누른 경우 다이얼로그 닫기
                dialog.dismiss()
            }
            .show()
    }

    private fun showError(message: String) {
        // 오류 메시지를 사용자에게 표시하는 코드를 추가합니다. 예를 들어 Toast 메시지나 AlertDialog를 사용할 수 있습니다.
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}