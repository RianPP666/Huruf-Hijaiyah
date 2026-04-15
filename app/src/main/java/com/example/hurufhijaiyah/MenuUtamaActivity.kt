package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView


class MenuUtamaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_utama)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Card clicks
        findViewById<MaterialCardView>(R.id.cardBelajar).setOnClickListener {
            startActivity(Intent(this, BelajarActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardTebak).setOnClickListener {
            DialogUtils.showConfirmationDialog(
                context = this,
                title = "Mulai Tebak Huruf",
                message = "Apakah kamu siap untuk memulai permainan?",
                positiveButtonText = "Mulai"
            ) { _, _ ->
                startActivity(Intent(this, TebakActivity::class.java))
            }
        }

        findViewById<MaterialCardView>(R.id.cardRiwayat).setOnClickListener {
            startActivity(Intent(this, RiwayatBelajarHurufActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardSurat).setOnClickListener {
            startActivity(Intent(this, DaftarSuratActivity::class.java))
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnProfil).setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }
    }
}