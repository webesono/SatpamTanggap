package com.example.satpamtanggap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.satpamtanggap.R
import com.example.satpamtanggap.adapters.SatpamsAdapter
import com.example.satpamtanggap.databinding.ActivityListpamBinding
import com.example.satpamtanggap.models.Satpam
import com.example.satpamtanggap.utilities.Constants
import com.example.satpamtanggap.utilities.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlin.collections.ArrayList

class ListpamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListpamBinding
    private lateinit var  preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listpam)

        binding = ActivityListpamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)

        dataSatpam()
        setListener()


    }

    private fun setListener(){
        binding.FabNewSatpam.setOnClickListener {
            startActivity(Intent(this@ListpamActivity, RegipamActivity::class.java))
        }
        binding.imageBack.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun dataSatpam(){
        loading(true)
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_SATPAM)
            .get()
            .addOnCompleteListener {
                loading(false)
                val idCurrentUser: String = preferenceManager.getString(Constants.KEY_USER_ID).toString()
                if (it.isSuccessful && it.result != null){
                    val satpams = ArrayList<Satpam>()
                    for (queryDocumentSnapshot: QueryDocumentSnapshot in it.result){
                        if (idCurrentUser == queryDocumentSnapshot.id){
                            continue
                        }
                        val satpam = Satpam()
                        satpam.nama = queryDocumentSnapshot.getString(Constants.KEY_NAME).toString()
                        satpam.telepon = queryDocumentSnapshot.getString(Constants.KEY_TELEPON).toString()
                        satpam.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE).toString()
                        satpam.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN).toString()
                        satpams.add(satpam)
                    }
                    if (satpams.size > 0){
                        val satpamsAdapter = SatpamsAdapter(this,satpams)
                        binding.satpamsRecycleView.adapter= satpamsAdapter
                        binding.satpamsRecycleView.visibility= View.VISIBLE
                    }else{
                        showErrorMessage()
                    }
                }
                else{
                    showErrorMessage()
                }
            }
    }

    private fun showErrorMessage() {
        binding.textError.text = String.format("%s", "Data satpam tidak tersedia")
    }

    private fun loading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility= View.INVISIBLE
        }
    }

}