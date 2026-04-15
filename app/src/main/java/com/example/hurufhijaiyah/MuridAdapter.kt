package com.example.hurufhijaiyah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MuridAdapter(
    private val list: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<MuridAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvInfo: TextView = view.findViewById(R.id.tvInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_murid, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.tvUsername.text = if (user.namaLengkap.isNotEmpty()) "${user.namaLengkap} (${user.username})" else user.username
        holder.tvInfo.text = "Quiz: ${user.totalQuiz} kali | Skor tertinggi: ${user.highestScore}/10"

        holder.itemView.setOnClickListener { onItemClick(user) }
    }
}
