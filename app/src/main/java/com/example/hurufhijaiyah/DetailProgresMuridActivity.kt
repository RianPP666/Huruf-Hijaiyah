package com.example.hurufhijaiyah

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class DetailProgresMuridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_progres_murid)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { finish() }

        val username = intent.getStringExtra("USERNAME") ?: return

        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvTotalKuis = findViewById<TextView>(R.id.tvTotalKuis)
        val tvSkorTertinggi = findViewById<TextView>(R.id.tvSkorTertinggi)
        val tvHurufSalah = findViewById<TextView>(R.id.tvHurufSalah)

        val firestoreHelper = FirestoreHelper()

        // Tampilkan nama lengkap
        firestoreHelper.getNamaLengkap(username) { namaLengkap ->
            tvUsername.text = "$namaLengkap ($username)"
        }

        // Ambil quiz stats
        firestoreHelper.getQuizStats(username) { (totalQuiz, highestScore) ->
            tvTotalKuis.text = "Total quiz: $totalQuiz kali"
            tvSkorTertinggi.text = "Skor tertinggi: $highestScore / 10"
        }

        // Ambil huruf salah
        firestoreHelper.getWrongAnswers(username) { wrongAnswers ->
            if (wrongAnswers.isEmpty()) {
                tvHurufSalah.text = "Tidak ada huruf yang salah 🎉"
            } else {
                val hurufText = wrongAnswers.joinToString(", ") { "${it.arab} (${it.latin})" }
                tvHurufSalah.text = hurufText
            }
        }
    }
}
