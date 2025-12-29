package com.example.hurufhijaiyah

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class DetailSuratActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_surat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener {
            finish()
        }

        val nama = intent.getStringExtra("NAMA_SURAT") ?: ""
        val arti = intent.getStringExtra("ARTI_SURAT") ?: ""
        val ayat = intent.getStringExtra("AYAT_SURAT") ?: ""
        val terjemahan = intent.getStringExtra("TERJEMAHAN_SURAT") ?: ""

        val tvNama = findViewById<TextView>(R.id.tvNamaSuratDetail)
        val tvArti = findViewById<TextView>(R.id.tvArtiSuratDetail)
        val tvAyat = findViewById<TextView>(R.id.tvAyat)
        val tvTerjemahan = findViewById<TextView>(R.id.tvTerjemahan)

        tvNama.text = nama
        tvArti.text = arti
        tvAyat.text = ayat
        tvTerjemahan.text = terjemahan
    }
}
