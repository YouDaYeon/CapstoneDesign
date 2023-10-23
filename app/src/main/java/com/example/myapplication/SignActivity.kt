package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySignBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database

class SignActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference

        binding.signUpBtn.setOnClickListener {
            val name = binding.nameEdit.text.toString().trim()
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEdit.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.endsWith("@g.hongik.ac.kr")) {
                Toast.makeText(this, "학교 이메일을 입력하셔야 합니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "비밀번호는 6자리 이상으로 설정해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val newUser = User(name, email, user?.uid ?: "")
                        // Firebase Realtime Database에 사용자 데이터 저장
                        mDbRef.child("user").child(user?.uid ?: "").setValue(newUser)
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { verificationTask ->
                                if (verificationTask.isSuccessful) {
                                    // 이메일 인증 메일 전송 성공 시
                                    Toast.makeText(
                                        this,
                                        "입력한 이메일로 인증 메일이 전송되었습니다. 이메일을 확인해주세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // 회원가입 성공 메시지 등을 표시하거나 추가 작업을 수행하려면 여기에 코드를 추가하세요.

                                    // 로그인 화면으로 돌아가기
                                    val intent = Intent(this, LogInActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "인증 메일 전송 실패",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("EmailVerification", "Error: ${verificationTask.exception}")
                                }
                            }
                    } else {
                        // 회원가입 실패 시
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        Log.d("Login", "Error:${task.exception}")
                    }
                }
        }
    }
}

