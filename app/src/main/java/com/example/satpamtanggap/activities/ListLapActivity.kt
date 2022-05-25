package com.example.satpamtanggap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.satpamtanggap.R
import com.example.satpamtanggap.adapters.LaporanAdapter
import com.example.satpamtanggap.databinding.ActivityListLapBinding
import com.example.satpamtanggap.models.Laporan
import com.example.satpamtanggap.utilities.Constants
import com.example.satpamtanggap.utilities.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class ListLapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListLapBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListLapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)

        setListener()
        addDataLaporan()
    }

    private fun setListener(){
        binding.imageBack.setOnClickListener {
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
        }
    }

    private fun addDataLaporan(){
        loading(true)
        val laporans = ArrayList<Laporan>()
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_LAPORAN)
            .get()
            .addOnCompleteListener {
                loading(false)
                if (it.isSuccessful && it.result != null){
                    for (queryDocumentSnapshot: QueryDocumentSnapshot in it.result){
                        val laporan = Laporan()
                        laporan.judul = queryDocumentSnapshot.getString(Constants.KEY_LAPORAN_JDL)
                        laporan.isi = queryDocumentSnapshot.getString(Constants.KEY_LAPORAN_ISI)
                        laporan.pengirim = queryDocumentSnapshot.getString(Constants.KEY_SENDER_NAME)
                        laporan.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL)
                        laporan.timestamp = queryDocumentSnapshot.getString(Constants.KEY_TIMESTAMP)
                        laporans.add(laporan)
                    }
                    if (laporans.size > 0){
                        val laporanAdapter = LaporanAdapter(this, laporans)
                        binding.laporansRecycleView.adapter = laporanAdapter
                        binding.laporansRecycleView.visibility = View.VISIBLE
                    }else{
                        showErrorMessage()
                    }
                }else{
                    showErrorMessage()
                }
            }
    }

    private fun loading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun showErrorMessage(){
        binding.errorText.text = String.format("%s", "Data laporan tidak tersedia")
        binding.errorText.visibility = View.VISIBLE
    }


    
}