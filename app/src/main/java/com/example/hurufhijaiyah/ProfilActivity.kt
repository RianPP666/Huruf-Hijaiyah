package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfilActivity : AppCompatActivity() {

    private fun dialogKeluar() {
        DialogUtils.showConfirmationDialog(
            context = this,
            title = "Keluar Aplikasi?",
            message = "Kamu akan kembali ke halaman login. Apakah kamu yakin?",
            negativeButtonText = "Tidak"
        ) { _, _ ->
            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            prefs.edit().remove("username").apply()

            // Kembali ke login setelah logout
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener {
            finish()
        }

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = prefs.getString("username", "User")
        val txtUsername = findViewById<TextView>(R.id.txtUsername)
        txtUsername.text = username

        val totalKuis = findViewById<TextView>(R.id.tvTotalKuis)
        val skorTertinggi = findViewById<TextView>(R.id.tvSkorTertinggi)

        val dbHelper = DatabaseHelper(this)
        val (totalQuiz, highestScore) = dbHelper.getQuizStats(username!!)

        totalKuis.text = "Total quiz: $totalQuiz kali"
        skorTertinggi.text = "Skor tertinggi: $highestScore / 10"


        val btnKeluar = findViewById<Button>(R.id.btnKeluar)
        btnKeluar.setOnClickListener {
            dialogKeluar()
        }
    }

}