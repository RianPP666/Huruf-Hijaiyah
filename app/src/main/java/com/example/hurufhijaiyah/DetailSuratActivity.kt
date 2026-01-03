package com.example.hurufhijaiyah

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val ayatStr = intent.getStringExtra("AYAT_SURAT") ?: ""
        val terjemahanStr = intent.getStringExtra("TERJEMAHAN_SURAT") ?: ""

        val tvNama = findViewById<TextView>(R.id.tvNamaSuratDetail)
        val tvArti = findViewById<TextView>(R.id.tvArtiSuratDetail)
        
        tvNama.text = nama
        tvArti.text = arti

        // Setup RecyclerView
        val rvAyat = findViewById<RecyclerView>(R.id.rvAyat)
        rvAyat.layoutManager = LinearLayoutManager(this)
        
        val listAyat = parseSurahData(ayatStr, terjemahanStr)
        rvAyat.adapter = AyatAdapter(listAyat)
    }

    private fun parseSurahData(arabStr: String, indoStr: String): List<Ayat> {
        val list = mutableListOf<Ayat>()
        
        val arabLines = arabStr.trim().split("\n")
        val indoLines = indoStr.trim().split("\n")

        // Simple alignment logic
        // Checks if the first line of Arabic is Bismillah (except Al-Fatihah, where it's verse 1)
        // If Arab has 7 lines and Indo has 6, likely index 0 is Bismillah.
        
        var arabIndex = 0
        var indoIndex = 0
        
        // Handle potential Bismillah header scenario
        if (arabLines.size > indoLines.size) {
            // Check if first line seems to be Bismillah and doesn't have a number marker like (1)
            // (Arabic numerals or regular parentheses)
            val firstLine = arabLines[0]
            val hasNumber = firstLine.contains("١") || firstLine.contains("1")
            
            if (!hasNumber) {
                // Add Bismillah as a header item (no number) or skip?
                // For this implementation, we can make an item with empty number
                list.add(Ayat("", firstLine, ""))
                arabIndex++
            }
        }

        // Parse the rest
        while (arabIndex < arabLines.size) {
            val rawArab = arabLines[arabIndex]
            
            // Try to extract number from Arabic text if possible, usually at the end (١)
            // We can also just use the counter.
            // Using loop counter is safer for consistency with translation.
            
            val translation = if (indoIndex < indoLines.size) indoLines[indoIndex] else ""
            
            // Extract number from translation often formatted as "1. Translation..."
            // Or just use the incrementing index.
            val displayNum = (indoIndex + 1).toString()

            list.add(Ayat(displayNum, rawArab, translation))
            
            arabIndex++
            indoIndex++
        }
        
        return list
    }
}
