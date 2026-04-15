package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tilUsername = findViewById<TextInputLayout>(R.id.til_username)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)

        val edtUsername = findViewById<EditText>(R.id.edt_username)
        val edtPassword = findViewById<EditText>(R.id.edt_password)

        val btnLogin = findViewById<MaterialButton>(R.id.btn_masuk)

        val firestoreHelper = FirestoreHelper()

        // Seed akun admin default jika belum ada
        firestoreHelper.seedAdminAccount()

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

            // Disable tombol saat proses login
            btnLogin.isEnabled = false
            btnLogin.text = "Memproses..."

            // Check login Firestore
            firestoreHelper.loginUser(username, password) { success ->
                if (success) {
                    firestoreHelper.getUserRole(username) { role ->
                        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        prefs.edit()
                            .putString("username", username)
                            .putString("role", role)
                            .apply()

                        // Arahkan ke menu berdasarkan role
                        val targetActivity = when (role) {
                            "guru" -> MenuGuruActivity::class.java
                            "admin" -> MenuAdminActivity::class.java
                            else -> MenuUtamaActivity::class.java // murid
                        }

                        Toast.makeText(this, "Login berhasil sebagai ${role.replaceFirstChar { it.uppercase() }}!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, targetActivity))
                        finish()
                    }
                } else {
                    btnLogin.isEnabled = true
                    btnLogin.text = "Masuk"
                    tilPassword.error = "Username atau password salah"
                }
            }
        }
    }
}