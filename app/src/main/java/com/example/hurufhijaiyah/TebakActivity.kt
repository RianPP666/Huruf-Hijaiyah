package com.example.hurufhijaiyah

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TebakActivity : AppCompatActivity() {

    private lateinit var txtHurufArab: TextView
    private lateinit var btn1: MaterialButton
    private lateinit var btn2: MaterialButton
    private lateinit var btn3: MaterialButton
    private lateinit var btn4: MaterialButton
    private lateinit var skorBenarText: TextView
    private lateinit var skorSalahText: TextView

    private var soalIndex = 0
    private var skorBenar = 0
    private var skorSalah = 0

    private lateinit var soalAcak: List<Huruf>
    private lateinit var semuaHuruf: List<Huruf>
    private var jawabanBenar = ""

    private fun dialogKeluarKuis() {
        DialogUtils.showConfirmationDialog(
            context = this,
            title = "Keluar Kuis Sekarang?",
            message = "Kuis belum selesai. Semua jawaban kamu akan hilang! Kamu yakin ingin keluar?",
            positiveButtonText = "Keluar",
            negativeButtonText = "Lanjutkan kuis"
        ) { _, _ ->
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tebak)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // tombol kembali
        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { dialogKeluarKuis() }

        // ambil view
        txtHurufArab = findViewById(R.id.txtHurufArab)
        btn1 = findViewById(R.id.btnPilihan1)
        btn2 = findViewById(R.id.btnPilihan2)
        btn3 = findViewById(R.id.btnPilihan3)
        btn4 = findViewById(R.id.btnPilihan4)
        skorBenarText = findViewById(R.id.skor_benar)
        skorSalahText = findViewById(R.id.skor_salah)

        // buat soal
        semuaHuruf = buildHurufList()
        soalAcak = semuaHuruf.shuffled().take(10)

        tampilkanSoal()

        // event tombol
        btn1.setOnClickListener { periksaJawaban(btn1.text.toString(), btn1) }
        btn2.setOnClickListener { periksaJawaban(btn2.text.toString(), btn2) }
        btn3.setOnClickListener { periksaJawaban(btn3.text.toString(), btn3) }
        btn4.setOnClickListener { periksaJawaban(btn4.text.toString(), btn4) }
    }

    private fun tampilkanSoal() {
        if (soalIndex >= 10) {
            showDialogQuizSelesai()
            return
        }

        val huruf = soalAcak[soalIndex]
        jawabanBenar = huruf.latin

        // tampilkan huruf arab
        txtHurufArab.text = huruf.arab

        // buat pilihan acak
        val pilihan = mutableListOf(jawabanBenar)

        val latinLain = semuaHuruf.filter { it.latin != jawabanBenar }.shuffled()
        pilihan.add(latinLain[0].latin)
        pilihan.add(latinLain[1].latin)
        pilihan.add(latinLain[2].latin)

        pilihan.shuffle()

        btn1.text = pilihan[0]
        btn2.text = pilihan[1]
        btn3.text = pilihan[2]
        btn4.text = pilihan[3]
    }

    private fun showDialogQuizSelesai() {
        btn1.isEnabled = false
        btn2.isEnabled = false
        btn3.isEnabled = false
        btn4.isEnabled = false

        val dialogView = layoutInflater.inflate(R.layout.dialog_quiz, null)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val mp = MediaPlayer.create(this, R.raw.kuis_selesai)
        mp.start()
        mp.setOnCompletionListener { it.release() }

        val txtScore = dialogView.findViewById<TextView>(R.id.txtScore)
        val btnRiwayat = dialogView.findViewById<MaterialButton>(R.id.btnRiwayat)
        val btnMenu = dialogView.findViewById<MaterialButton>(R.id.btnMenu)

        // TAMPILKAN SKOR → 7/10
        txtScore.text = "$skorBenar / 10"

        // Tombol ke riwayat huruf salah
        btnRiwayat.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, RiwayatBelajarHurufActivity::class.java))
            finish() // Agar saat kembali dari riwayat, tidak balik ke kuis selesai
        }

        // Tombol kembali ke menu
        btnMenu.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = prefs.getString("username", null) ?: return
        val dbHelper = DatabaseHelper(this)
        dbHelper.updateQuizStats(username, skorBenar)
    }

    private fun periksaJawaban(jawaban: String, btn: MaterialButton) {

        if (jawaban == jawabanBenar) {
            skorBenar++
            val mp = MediaPlayer.create(this, R.raw.benar)
            mp.start()
            mp.setOnCompletionListener { it.release() }
            btn.setBackgroundColor(Color.parseColor("#4CAF50")) // hijau

            // Remove from history if correct
            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val username = prefs.getString("username", null)
            if (username != null) {
                val dbHelper = DatabaseHelper(this)
                // We need to know the arabic char for current answer.
                // In periksaJawaban we only have 'jawaban'.
                // 'jawabanBenar' is avail as global.
                // We need the Huruf object corresponding to 'jawabanBenar'.
                // But wait, 'jawaban' is the Latin text from button.
                // 'jawabanBenar' is also Latin.
                // If correct, 'jawaban' == 'jawabanBenar'.
                // We need the Arabic text to remove it.
                // The current question is 'soalAcak[soalIndex]'.
                val currentSoal = soalAcak[soalIndex]
                dbHelper.removeWrongAnswer(username, currentSoal.arab)
            }

        } else {
            skorSalah++
            val mp = MediaPlayer.create(this, R.raw.salah)
            mp.start()
            mp.setOnCompletionListener { it.release() }
            btn.setBackgroundColor(Color.parseColor("#F44336")) // merah

            // Add to history if wrong
            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val username = prefs.getString("username", null)
            if (username != null) {
                val dbHelper = DatabaseHelper(this)
                val currentSoal = soalAcak[soalIndex]
                dbHelper.addWrongAnswer(username, currentSoal.arab, currentSoal.latin)
            }
        }

        skorBenarText.text = "Benar: $skorBenar"
        skorSalahText.text = "Salah: $skorSalah"

        // Reset warna + lanjut soal setelah 600ms
        btn.postDelayed({
            btn.setBackgroundColor(Color.parseColor("#EFE9E3")) // warna original
            soalIndex++
            tampilkanSoal()
        }, 600)
    }

    private fun buildHurufList(): List<Huruf> {
        val arab = listOf(
            "اَ","بَ","تَ","ثَ","جَ","حَ","خَ","دَ","ذَ","رَ",
            "زَ","سَ","شَ","صَ","ضَ","طَ","ظَ","عَ","غَ","فَ",
            "قَ","كَ","لَ","مَ","نَ","وَ","هَ","يَ"
        )

        val latin = listOf(
            "a","ba","ta","tsa","ja","ha","kho","da","dza","ra",
            "za","sa","sya","sho","dho","tho","zho","a'","gho","fa",
            "qo","ka","la","ma","na","wa","haa","ya"
        )

        val list = mutableListOf<Huruf>()
        for (i in arab.indices) {
            list.add(Huruf(arab[i], latin[i]))
        }
        return list
    }
}
