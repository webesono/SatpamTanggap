package com.example.satpamtanggap.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.satpamtanggap.R
import com.example.satpamtanggap.databinding.ActivityLaporanBinding
import com.example.satpamtanggap.utilities.Constants

class LaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

    }

    private fun loadData(){
        val judul = intent.getStringExtra(Constants.KEY_LAPORAN_JDL)
        val deskripsi = intent.getStringExtra(Constants.KEY_LAPORAN_ISI)
        val pengirim = intent.getStringExtra(Constants.KEY_SENDER_NAME)
        val email = intent.getStringExtra(Constants.KEY_EMAIL)
        val tanggal = intent.getStringExtra(Constants.KEY_TIMESTAMP)

        binding.inputJudul.text = judul
        binding.inputDeskripsi.text = deskripsi
        binding.textPengirim.text = pengirim
        binding.textTanggal.text = tanggal
    }
}