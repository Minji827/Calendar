package com.example.calendar

data class Schedule(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val userId: String = ""  // 로그인한 사용자 UID
)
