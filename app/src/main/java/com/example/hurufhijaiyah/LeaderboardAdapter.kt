package com.example.hurufhijaiyah

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter(
    private val list: List<User>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRank: TextView = view.findViewById(R.id.tvRank)
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvQuizCount: TextView = view.findViewById(R.id.tvQuizCount)
        val tvScore: TextView = view.findViewById(R.id.tvScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        val rank = position + 1

        holder.tvRank.text = rank.toString()
        holder.tvUsername.text = if (user.namaLengkap.isNotEmpty()) user.namaLengkap else user.username
        holder.tvQuizCount.text = "Quiz: ${user.totalQuiz} kali"
        holder.tvScore.text = "${user.highestScore}/10"

        // Warna medali untuk top 3
        val bgColor = when (rank) {
            1 -> Color.parseColor("#F4A261") // Emas
            2 -> Color.parseColor("#B0BEC5") // Perak
            3 -> Color.parseColor("#CD7F32") // Perunggu
            else -> Color.parseColor("#0D7377") // Teal
        }
        holder.tvRank.background.setTint(bgColor)
    }
}
