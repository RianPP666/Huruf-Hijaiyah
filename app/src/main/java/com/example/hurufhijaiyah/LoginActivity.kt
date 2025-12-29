package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set layout yang baru dibuat
        setContentView(R.layout.activity_login)

        val tvDaftarDisini = findViewById<TextView>(R.id.tvDaftarDisini)

        tvDaftarDisini.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val tilUsername = findViewById<TextInputLayout>(R.id.til_username)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)

        val edtUsername = findViewById<EditText>(R.id.edt_username)
        val edtPassword = findViewById<EditText>(R.id.edt_password)

        val btnLogin = findViewById<MaterialButton>(R.id.btn_masuk)

        val dbHelper = DatabaseHelper(this)

        btnLogin.setOnClickListener {

            // Hapus error sebelumnya
            tilUsername.error = null
            tilPassword.error = null

            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            var valid = true

            // Validasi username kosong
            if (username.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            }

            // Validasi password kosong
            if (password.isEmpty()) {
                tilPassword.error = "Password tidak boleh kosong"
                valid = false
            }

            if (!valid) return@setOnClickListener

            // Check login database
            if (dbHelper.loginUser(username, password)) {

                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                prefs.edit().putString("username", username).apply()

                startActivity(Intent(this, MenuUtamaActivity::class.java))
                finish()

            } else {
                tilPassword.error = "Username atau password salah"
            }
        }
    }
}