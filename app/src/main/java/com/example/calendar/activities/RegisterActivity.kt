package com.example.calendar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.LoginActivity
import com.example.calendar.R
import com.example.calendar.database.AppDatabaseHelper
import com.example.calendar.models.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var pwEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.editTextEmail)
        pwEditText = findViewById(R.id.editTextPassword)
        registerButton = findViewById(R.id.btnRegister)
        backToLogin = findViewById(R.id.textBackToLogin)

        val db = AppDatabaseHelper.getDatabase(this)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = pwEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val existingUser = db.userDao().login(email, password)
            if (existingUser != null) {
                Toast.makeText(this, "이미 존재하는 사용자입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val newUser = User(email, password)
                db.userDao().insertUser(newUser)
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        backToLogin.setOnClickListener {
            finish() // 로그인 화면으로 돌아가기
        }
    }
}
