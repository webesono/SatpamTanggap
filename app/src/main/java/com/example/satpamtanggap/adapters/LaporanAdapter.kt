package com.example.satpamtanggap.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.satpamtanggap.R
import com.example.satpamtanggap.activities.LaporanActivity
import com.example.satpamtanggap.databinding.ItemContainerLaporanBinding
import com.example.satpamtanggap.models.Laporan
import com.example.satpamtanggap.utilities.Constants

class LaporanAdapter(val context: Context, private val laporans: List<Laporan>): RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_container_laporan, parent, false)
        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        val currentLaporan  = laporans[position]

        holder.setLaporanData(currentLaporan)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, LaporanActivity::class.java)
            intent.putExtra(Constants.KEY_LAPORAN_JDL, currentLaporan.judul)
            intent.putExtra(Constants.KEY_LAPORAN_ISI, currentLaporan.isi)
            intent.putExtra(Constants.KEY_SENDER_NAME, currentLaporan.pengirim)
            intent.putExtra(Constants.KEY_TIMESTAMP, currentLaporan.timestamp)
            intent.putExtra(Constants.KEY_EMAIL, currentLaporan.email)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return laporans.size
    }


    inner class LaporanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val textJudul = itemView.findViewById<TextView>(R.id.textJudul)
        private val textTanggal = itemView.findViewById<TextView>(R.id.textTanggal)

        fun setLaporanData(laporan: Laporan){
            textJudul.text = laporan.judul
            textTanggal.text = laporan.timestamp
        }
    }

}