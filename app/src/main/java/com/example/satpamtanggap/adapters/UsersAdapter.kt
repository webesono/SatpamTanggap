package com.example.satpamtanggap.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.satpamtanggap.R
import com.example.satpamtanggap.activities.ChatActivity
import com.example.satpamtanggap.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.makeramen.roundedimageview.RoundedImageView

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private lateinit var users: List<User>
    private lateinit var context: Context

    constructor(context: Context, users: List<User>){
        this.context = context
        this.users = users
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_container_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]

        holder.setUserData(currentUser)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.nama)
            intent.putExtra("uid", currentUser.id)
            intent.putExtra("token", currentUser.token)
            intent.putExtra("image", currentUser.image)
            intent.putExtra("alamat", currentUser.alamat)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNama = itemView.findViewById<TextView>(R.id.textNama)
        private val textAlamat: TextView = itemView.findViewById(R.id.textAlamat)
        private val imageProfile: RoundedImageView = itemView.findViewById(R.id.imageProfile)

        private fun getUserImage(encodedImage: String): Bitmap {
        val bytes: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        fun setUserData(currentUser: User){
            textNama.text = currentUser.nama
            textAlamat.text = currentUser.alamat
            imageProfile.setImageBitmap(currentUser.image?.let { getUserImage(it) })
        }

    }

}