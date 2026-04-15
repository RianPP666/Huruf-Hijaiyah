package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class MenuGuruActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_guru)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<MaterialCardView>(R.id.cardDaftarMurid).setOnClickListener {
            startActivity(Intent(this, DaftarMuridActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardLeaderboard).setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardProfil).setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }
    }
}
