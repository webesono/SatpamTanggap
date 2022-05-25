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
import com.example.satpamtanggap.models.ChatMessage
import com.makeramen.roundedimageview.RoundedImageView

class RecentConvAdapter(val context: Context, private val chatMessages: List<ChatMessage>): RecyclerView.Adapter<RecentConvAdapter.RecentConvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentConvViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_container_recent_conv, parent, false)
        return RecentConvViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentConvViewHolder, position: Int) {
        holder.setData(chatMessages[position])
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    class RecentConvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textNama = itemView.findViewById<TextView>(R.id.textNama)
        private  val textRecentMsg = itemView.findViewById<TextView>(R.id.textRecentMsg)
        private val imageProfile = itemView.findViewById<RoundedImageView>(R.id.imageProfile)

        private fun getConvImage(encodedImage: String): Bitmap{
            val bytes: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            return  BitmapFactory.decodeByteArray(bytes,0, bytes.size)
        }

        fun setData(chatMessage: ChatMessage){
            textNama.text = chatMessage.convName
            textRecentMsg.text = chatMessage.message
            imageProfile.setImageBitmap(getConvImage(chatMessage.convImage!!))
        }
    }


}