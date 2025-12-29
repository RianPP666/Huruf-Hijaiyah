package com.example.hurufhijaiyah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

data class Huruf(val arab: String, val latin: String)

class BelajarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_belajar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener {
            finish()
        }

    val rv = findViewById<RecyclerView>(R.id.rvHuruf)
    rv.layoutManager = GridLayoutManager(this, 4)
    // make columns start from right to left so Alif appears at top-right
    rv.layoutDirection = View.LAYOUT_DIRECTION_RTL
        val data = buildHurufList()
        rv.adapter = HurufAdapter(data)
    }

    private fun buildHurufList(): List<Huruf> {
        val arab = listOf(
            "اَ","بَ","تَ","ثَ","جَ","حَ","خَ","دَ","ذَ","رَ",
            "زَ","سَ","شَ","صَ","ضَ","طَ","ظَ","عَ","غَ","فَ",
            "قَ","كَ","لَ","مَ","نَ","وَ","هَ","يَ"
        )
        val latin = listOf(
            "a","ba","ta","tsa","ja","ha","kho","da","dza","ro",
            "za","sa","sya","sho","dho","tho","zho","a'","gho","fa",
            "qo","ka","la","ma","na","wa","haa","ya"
        )

        val list = mutableListOf<Huruf>()
        val count = 28
        for (i in 0 until count) {
            val idx = i % arab.size
            list.add(Huruf(arab[idx], latin[idx]))
        }
        return list
    }
}