package com.example.hurufhijaiyah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AyatAdapter(private val listAyat: List<Ayat>) : RecyclerView.Adapter<AyatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNomor: TextView = view.findViewById(R.id.tvNomorAyat)
        val tvArab: TextView = view.findViewById(R.id.tvAyatArab)
        val tvTerjemahan: TextView = view.findViewById(R.id.tvAyatTerjemahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ayat = listAyat[position]
        holder.tvNomor.text = ayat.nomor
        holder.tvArab.text = ayat.arab
        holder.tvTerjemahan.text = ayat.terjemahan
    }

    override fun getItemCount(): Int = listAyat.size
}
