package com.example.hurufhijaiyah

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HurufAdapter(private val items: List<Huruf>) : RecyclerView.Adapter<HurufAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvArabic: TextView = view.findViewById(R.id.tvArabic)
        val tvLatin: TextView = view.findViewById(R.id.tvLatin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_huruf, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val h = items[position]
        holder.tvArabic.text = h.arab
        holder.tvLatin.text = h.latin

        val fileName = h.latin.replace("'", "a").replace(" ", "_")

        // Click behavior: placeholder — could play audio later
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val resId = context.resources.getIdentifier(fileName, "raw", context.packageName)
            if (resId != 0) {
                val player = MediaPlayer.create(context, resId)
                player.start()
                player.setOnCompletionListener {
                    it.release()
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
