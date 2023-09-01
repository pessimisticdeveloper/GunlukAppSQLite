package com.example.gunluksqlite


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class GunlukAdapter(private var gunluk: List<Gunluk>,context: Context) : RecyclerView.Adapter<GunlukAdapter.GunlukViewHolder>() {

    private val db: GunlukDataBaseHelper = GunlukDataBaseHelper(context)

    class GunlukViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val contentTv: TextView = itemView.findViewById(R.id.contentTv)
        val uptadeBtn: ImageView = itemView.findViewById(R.id.updateBtn)
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteBtn)
        val readBtn: ImageView = itemView.findViewById(R.id.readBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GunlukViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gunluk_item, parent, false)
        return GunlukViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gunluk.size
    }

    override fun onBindViewHolder(holder: GunlukViewHolder, position: Int) {
        val gunluk = gunluk[position]
        holder.titleTv.text = gunluk.title
        holder.contentTv.text = gunluk.content

        holder.uptadeBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("gunluk_id", gunluk.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteBtn.setOnClickListener {
            db.deleteGunluk(gunluk.id)
            refreshData(db.getAllGunluk())
            Toast.makeText(holder.itemView.context, "Yazı Silindi", Toast.LENGTH_SHORT).show()
        }
        holder.readBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context,ReadActivity::class.java).apply {
                putExtra("gunluk_id", gunluk.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    fun refreshData(newGunluk: List<Gunluk>) {
        gunluk = newGunluk
        notifyDataSetChanged()
    }
}