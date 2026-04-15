package com.example.hurufhijaiyah

import android.content.Intent
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

class DaftarMuridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar_murid)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { finish() }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val firestoreHelper = FirestoreHelper()

        val rvMurid = findViewById<RecyclerView>(R.id.rvMurid)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)

        firestoreHelper.getAllMurid { muridList ->
            if (muridList.isEmpty()) {
                tvEmpty.visibility = View.VISIBLE
                rvMurid.visibility = View.GONE
            } else {
                tvEmpty.visibility = View.GONE
                rvMurid.visibility = View.VISIBLE
                rvMurid.layoutManager = LinearLayoutManager(this)
                rvMurid.adapter = MuridAdapter(muridList) { user ->
                    val intent = Intent(this, DetailProgresMuridActivity::class.java)
                    intent.putExtra("USERNAME", user.username)
                    startActivity(intent)
                }
            }
        }
    }
}
