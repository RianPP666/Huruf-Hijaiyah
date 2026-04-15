package com.example.hurufhijaiyah

data class User(
    val username: String = "",
    val namaLengkap: String = "",
    val role: String = "murid", // "murid", "guru", "admin"
    val totalQuiz: Int = 0,
    val highestScore: Int = 0
)
