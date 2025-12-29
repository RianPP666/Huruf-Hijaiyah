package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


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

        // Wire menu clicks
        val btnBelajarHuruf = findViewById<Button>(R.id.btnBelajarHuruf)
        val btnTebakHuruf = findViewById<Button>(R.id.btnTebakHuruf)
        val btnRiwayatBelajar= findViewById<Button>(R.id.btnRiwayatBelajar)

        val btnProfil = findViewById<Button>(R.id.btnProfil)

        btnProfil.setOnClickListener {
            val i = Intent(this, ProfilActivity::class.java)
            startActivity(i)
        }

        btnBelajarHuruf.setOnClickListener {
            // Belajar Huruf
            val i = Intent(this, BelajarActivity::class.java)
            startActivity(i)
        }

        btnTebakHuruf.setOnClickListener {
            // Tebak Huruf
            DialogUtils.showConfirmationDialog(
                context = this,
                title = "Mulai Tebak Huruf",
                message = "Apakah kamu siap untuk memulai permainan?",
                positiveButtonText = "Mulai"
            ) { _, _ ->
                val i = Intent(this, TebakActivity::class.java)
                startActivity(i)
            }
        }

        btnRiwayatBelajar.setOnClickListener {
            // RiwayarBelajar
            val i = Intent(this, RiwayatBelajarHurufActivity::class.java)
            startActivity(i)
        }

        val btnSuratPendek = findViewById<Button>(R.id.btnSuratPendek)
        btnSuratPendek.setOnClickListener {
            // Hafalan Surat
            val i = Intent(this, DaftarSuratActivity::class.java)
            startActivity(i)
        }
    }
}