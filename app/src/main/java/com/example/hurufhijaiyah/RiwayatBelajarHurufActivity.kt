package com.example.hurufhijaiyah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RiwayatBelajarHurufActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riwayat_belajar_huruf)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener {
            finish()
        }

        // Setup RecyclerView
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvHuruf)
        // Use Grid or Linear? "learning history" might look good as grid similar to Belajar
        rv.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 4)
        rv.layoutDirection = android.view.View.LAYOUT_DIRECTION_RTL // Arabic style

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = prefs.getString("username", null)

        if (username != null) {
            val dbHelper = DatabaseHelper(this)
            val wrongAnswers = dbHelper.getWrongAnswers(username)
            rv.adapter = HurufAdapter(wrongAnswers)
        }
    }
}