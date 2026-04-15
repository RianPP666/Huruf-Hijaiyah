package com.example.hurufhijaiyah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class KelolaUserActivity : AppCompatActivity() {

    private lateinit var rvUsers: RecyclerView
    private lateinit var tvEmpty: TextView
    private val firestoreHelper = FirestoreHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kelola_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)
        btnKembali.setOnClickListener { finish() }

        val btnTambahUser = findViewById<MaterialButton>(R.id.btnTambahUser)
        btnTambahUser.setOnClickListener {
            startActivity(Intent(this, TambahUserActivity::class.java))
        }

        rvUsers = findViewById(R.id.rvUsers)
        tvEmpty = findViewById(R.id.tvEmpty)

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        firestoreHelper.getAllUsers { userList ->
            val mutableList = userList.toMutableList()

            if (mutableList.isEmpty()) {
                tvEmpty.visibility = View.VISIBLE
                rvUsers.visibility = View.GONE
            } else {
                tvEmpty.visibility = View.GONE
                rvUsers.visibility = View.VISIBLE
                rvUsers.layoutManager = LinearLayoutManager(this)
                rvUsers.adapter = KelolaUserAdapter(mutableList) { user, position ->
                    // Konfirmasi hapus
                    DialogUtils.showConfirmationDialog(
                        context = this,
                        title = "Hapus User",
                        message = "Apakah kamu yakin ingin menghapus akun \"${user.username}\"? Semua data user akan dihapus.",
                        positiveButtonText = "Hapus"
                    ) { _, _ ->
                        firestoreHelper.deleteUser(user.username) { success ->
                            if (success) {
                                (rvUsers.adapter as KelolaUserAdapter).removeItem(position)
                                Toast.makeText(this, "User \"${user.username}\" berhasil dihapus", Toast.LENGTH_SHORT).show()

                                if ((rvUsers.adapter as KelolaUserAdapter).itemCount == 0) {
                                    tvEmpty.visibility = View.VISIBLE
                                    rvUsers.visibility = View.GONE
                                }
                            } else {
                                Toast.makeText(this, "Gagal menghapus user", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
