package com.example.satpamtanggap.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.satpamtanggap.R
import com.example.satpamtanggap.models.Satpam

import com.makeramen.roundedimageview.RoundedImageView

class SatpamsAdapter(val context: Context, private val satpams: List<Satpam>): RecyclerView.Adapter<SatpamsAdapter.SatpamViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatpamViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_container_satpam, parent, false)
        return SatpamViewHolder(view)
    }

    override fun onBindViewHolder(holder: SatpamViewHolder, position: Int) {
        holder.setSatpamData(satpams[position])
    }

    override fun getItemCount(): Int {
        return satpams.size
    }

    class SatpamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNama = itemView.findViewById<TextView>(R.id.textName)
        private val textTelepon= itemView.findViewById<TextView>(R.id.textTelepon)
        private val imageProfile: RoundedImageView = itemView.findViewById(R.id.imageProfile)

        private fun getSatpamImage(encodedImage: String): Bitmap {
            val bytes: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        fun setSatpamData(satpam: Satpam){
            textNama.text = satpam.nama
            textTelepon.text = satpam.telepon
            imageProfile.setImageBitmap(getSatpamImage(satpam.image))
        }

    }

}