package com.example.satpamtanggap.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.satpamtanggap.R
import com.example.satpamtanggap.databinding.ActivityChangePassBinding

class ChangePassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()

    }

    private fun setListener(){
        binding.btnUbahMainPass.setOnClickListener{
            showToast("Berhasil diubah")
        }
    }

    private fun showToast(pesan: String){
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }


}