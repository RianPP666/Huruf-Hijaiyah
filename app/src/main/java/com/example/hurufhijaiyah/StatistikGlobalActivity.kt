package com.example.hurufhijaiyah

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class StatistikGlobalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_statistik_global)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { finish() }

        val firestoreHelper = FirestoreHelper()

        val tvTotalMurid = findViewById<TextView>(R.id.tvTotalMurid)
        val tvTotalGuru = findViewById<TextView>(R.id.tvTotalGuru)
        val tvTotalQuiz = findViewById<TextView>(R.id.tvTotalQuiz)
        val tvRataRata = findViewById<TextView>(R.id.tvRataRata)

        firestoreHelper.getTotalMurid { total ->
            tvTotalMurid.text = total.toString()
        }

        firestoreHelper.getTotalGuru { total ->
            tvTotalGuru.text = total.toString()
        }

        firestoreHelper.getTotalQuizDikerjakan { total ->
            tvTotalQuiz.text = "$total kali"
        }

        firestoreHelper.getRataRataSkor { avg ->
            tvRataRata.text = String.format("%.1f / 10", avg)
        }
    }
}
