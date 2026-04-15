package com.example.hurufhijaiyah

import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class TambahUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { finish() }

        val tilNama = findViewById<TextInputLayout>(R.id.til_nama)
        val tilUsername = findViewById<TextInputLayout>(R.id.til_username)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)
        val edtNama = findViewById<EditText>(R.id.edt_nama)
        val edtUsername = findViewById<EditText>(R.id.edt_username)
        val edtPassword = findViewById<EditText>(R.id.edt_password)
        val radioGroupRole = findViewById<RadioGroup>(R.id.radioGroupRole)
        val btnSimpan = findViewById<MaterialButton>(R.id.btnSimpan)

        val firestoreHelper = FirestoreHelper()

        btnSimpan.setOnClickListener {
            tilNama.error = null
            tilUsername.error = null
            tilPassword.error = null

            val namaLengkap = edtNama.text.toString().trim()
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val role = if (radioGroupRole.checkedRadioButtonId == R.id.rbGuru) "guru" else "murid"

            var valid = true

            if (namaLengkap.isEmpty()) {
                tilNama.error = "Nama lengkap tidak boleh kosong"
                valid = false
            }

            if (username.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            }

            if (password.length < 8) {
                tilPassword.error = "Password minimal 8 karakter"
                valid = false
            }

            if (!valid) return@setOnClickListener

            btnSimpan.isEnabled = false
            btnSimpan.text = "Menyimpan..."

            firestoreHelper.createUserByAdmin(namaLengkap, username, password, role) { success ->
                if (success) {
                    Toast.makeText(
                        this,
                        "Akun ${role.replaceFirstChar { it.uppercase() }} \"$username\" berhasil dibuat!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    btnSimpan.isEnabled = true
                    btnSimpan.text = "Simpan"
                    tilUsername.error = "Username sudah digunakan"
                }
            }
        }
    }
}
