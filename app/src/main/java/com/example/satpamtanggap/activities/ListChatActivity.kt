package com.example.satpamtanggap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.satpamtanggap.R
import com.example.satpamtanggap.adapters.RecentConvAdapter
import com.example.satpamtanggap.databinding.ActivityListChatBinding
import com.example.satpamtanggap.models.ChatMessage
import com.example.satpamtanggap.utilities.Constants
import com.example.satpamtanggap.utilities.PreferenceManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ListChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListChatBinding
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(applicationContext)
        binding = ActivityListChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()

    }

    private fun setListener(){
        binding.FabNewChat.setOnClickListener{
            startActivity(Intent(this@ListChatActivity, ContactActivity::class.java))
        }
        binding.imageBack.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }


}