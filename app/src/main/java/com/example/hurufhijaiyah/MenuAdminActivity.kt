package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class MenuAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<MaterialCardView>(R.id.cardKelolaUser).setOnClickListener {
            startActivity(Intent(this, KelolaUserActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardStatistik).setOnClickListener {
            startActivity(Intent(this, StatistikGlobalActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardProfil).setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }
    }
}
