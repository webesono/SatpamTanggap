package com.example.satpamtanggap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.satpamtanggap.R
import com.example.satpamtanggap.adapters.UsersAdapter
import com.example.satpamtanggap.databinding.ActivityListgaBinding
import com.example.satpamtanggap.models.User
import com.example.satpamtanggap.utilities.Constants
import com.example.satpamtanggap.utilities.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class ListgaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListgaBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listga)

        binding = ActivityListgaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        setListener()
        getUsers()

    }

    private fun setListener(){
        binding.FabNewWarga.setOnClickListener {
            startActivity(Intent(this@ListgaActivity, RegigaActivity::class.java))
        }
        binding.imageBack.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun getUsers(){
        loading(true)
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USERS)
            .get()
            .addOnCompleteListener{
                loading(false)
                val currentUserId: String = preferenceManager.getString(Constants.KEY_USER_ID).toString()
                if (it.isSuccessful && it.result != null){
                    val users = ArrayList<User>()
                    for (queryDocumentSnapshot: QueryDocumentSnapshot in it.result){
                        if (currentUserId == queryDocumentSnapshot.id){
                            continue
                        }
                        val user = User()
                        user.nama = queryDocumentSnapshot.getString(Constants.KEY_NAME)
                        user.alamat = queryDocumentSnapshot.getString(Constants.KEY_ADDRESS)
                        user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE)
                        user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN)
                        user.id = queryDocumentSnapshot.getString(Constants.KEY_USER_ID)
                        users.add(user)
                    }
                    if (users.size > 0){
                        val usersAdapter = UsersAdapter(this,users)
                        binding.usersRecycleView.adapter= usersAdapter
                        binding.usersRecycleView.visibility= View.VISIBLE
                    }else{
                        showErrorMessage()
                    }
                }else{
                    showErrorMessage()
                }
            }
    }

    private fun showErrorMessage(){
        binding.textError.text = String.format("%s", "Data warga tidak tersedia")
        binding.textError.visibility = View.VISIBLE
    }

    private fun loading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}