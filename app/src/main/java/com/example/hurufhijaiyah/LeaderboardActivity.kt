package com.example.hurufhijaiyah

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class LeaderboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { finish() }

        val firestoreHelper = FirestoreHelper()

        val rvLeaderboard = findViewById<RecyclerView>(R.id.rvLeaderboard)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)

        firestoreHelper.getLeaderboard { leaderboard ->
            if (leaderboard.isEmpty()) {
                tvEmpty.visibility = View.VISIBLE
                rvLeaderboard.visibility = View.GONE
            } else {
                tvEmpty.visibility = View.GONE
                rvLeaderboard.visibility = View.VISIBLE
                rvLeaderboard.layoutManager = LinearLayoutManager(this)
                rvLeaderboard.adapter = LeaderboardAdapter(leaderboard)
            }
        }
    }
}
