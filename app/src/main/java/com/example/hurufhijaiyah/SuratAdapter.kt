package com.example.hurufhijaiyah

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuratAdapter(private val listSurat: List<Surah>) : RecyclerView.Adapter<SuratAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNamaSurat)
        val tvArti: TextView = view.findViewById(R.id.tvArtiSurat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surat = listSurat[position]
        holder.tvNama.text = surat.nama
        holder.tvArti.text = surat.arti

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailSuratActivity::class.java)
            // Pass data using Intent extras
            intent.putExtra("NAMA_SURAT", surat.nama)
            intent.putExtra("ARTI_SURAT", surat.arti)
            intent.putExtra("AYAT_SURAT", surat.ayat)
            intent.putExtra("TERJEMAHAN_SURAT", surat.terjemahan)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listSurat.size
}
