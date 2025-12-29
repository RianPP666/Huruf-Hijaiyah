package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvMasukDisini = findViewById<TextView>(R.id.tvMasukDisini)
        tvMasukDisini.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Input Layout
        val tilUsername = findViewById<TextInputLayout>(R.id.til_username)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.til_confirm_password)

        // EditText
        val edtUsername = findViewById<EditText>(R.id.edt_username)
        val edtPassword = findViewById<EditText>(R.id.edt_password)
        val edtConfirmPassword = findViewById<EditText>(R.id.edt_confirm_password)

        val btnRegister = findViewById<MaterialButton>(R.id.btn_register)
        val dbHelper = DatabaseHelper(this)

        btnRegister.setOnClickListener {

            // Clear previous errors
            tilUsername.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null

            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()

            var valid = true

            // Username Kosong
            if (username.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            }

            // Password minimal 8 karakter
            if (password.length < 8) {
                tilPassword.error = "Password minimal 8 karakter"
                valid = false
            }

            // Konfirmasi password kosong
            if (confirmPassword.isEmpty()) {
                tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                valid = false
            }

            // Password tidak sama
            if (password != confirmPassword) {
                tilConfirmPassword.error = "Password tidak cocok"
                valid = false
            }

            if (!valid) return@setOnClickListener

            // Register
            val success = dbHelper.registerUser(username, password)

            if (success) {
                tilUsername.error = null
                tilPassword.error = null
                tilConfirmPassword.error = null

                // Toast masih dipakai sebagai feedback sukses
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                tilUsername.error = "Username sudah digunakan"
            }
        }
    }
}


