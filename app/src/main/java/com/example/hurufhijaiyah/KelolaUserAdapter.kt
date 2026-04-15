package com.example.hurufhijaiyah

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class KelolaUserAdapter(
    private val list: MutableList<User>,
    private val onDeleteClick: (User, Int) -> Unit
) : RecyclerView.Adapter<KelolaUserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvRole: TextView = view.findViewById(R.id.tvRole)
        val tvInfo: TextView = view.findViewById(R.id.tvInfo)
        val btnHapus: MaterialButton = view.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kelola_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.tvUsername.text = if (user.namaLengkap.isNotEmpty()) "${user.namaLengkap} (${user.username})" else user.username

        // Tampilkan role dengan warna berbeda
        val roleDisplay = user.role.replaceFirstChar { it.uppercase() }
        holder.tvRole.text = roleDisplay
        holder.tvRole.setTextColor(
            when (user.role) {
                "guru" -> Color.parseColor("#264653") // Navy
                else -> Color.parseColor("#0D7377") // Teal
            }
        )

        if (user.role == "murid") {
            holder.tvInfo.text = "Quiz: ${user.totalQuiz} kali | Skor: ${user.highestScore}/10"
            holder.tvInfo.visibility = View.VISIBLE
        } else {
            holder.tvInfo.visibility = View.GONE
        }

        holder.btnHapus.setOnClickListener {
            onDeleteClick(user, holder.adapterPosition)
        }
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}
