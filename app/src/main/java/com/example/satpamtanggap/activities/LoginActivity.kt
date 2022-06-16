package com.example.satpamtanggap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.satpamtanggap.R
import com.example.satpamtanggap.databinding.ActivityLoginBinding
import com.example.satpamtanggap.utilities.Constants
import com.example.satpamtanggap.utilities.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferenceManager = PreferenceManager(applicationContext)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

       setListeners()
    }

    private fun setListeners(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            val pass2 = binding.etPass2.text.toString().trim()

            if (notEmpty(email, pass, pass2)){
                login(email, pass, pass2)
            }
        }
    }

    private fun login(email: String,pass: String, pass2: String){
        loading(true)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()

        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    preferenceManager.putString(Constants.KEY_USER_EMAIL, email)
                    preferenceManager.putString(Constants.KEY_USER_P, pass)
                    database.collection(Constants.KEY_COLLECTION_SATPAM)
                        .whereEqualTo(Constants.KEY_PASSWORD2, pass2)
                        .get()
                        .addOnCompleteListener(this){ task ->
                            if (task.isSuccessful && task.result != null
                                && task.result.documents.size > 0){
                                val documentSnapshot: DocumentSnapshot = task.result.documents[0]
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                                preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.id)
                                documentSnapshot.getString(Constants.KEY_NAME)?.let { it1 ->
                                    preferenceManager.putString(Constants.KEY_NAME,
                                        it1
                                    )
                                }
                                documentSnapshot.getString(Constants.KEY_TELEPON)?.let { it1 ->
                                    preferenceManager.putString(Constants.KEY_TELEPON,
                                        it1
                                    )
                                }
                                documentSnapshot.getString(Constants.KEY_IMAGE)?.let { it1 ->
                                    preferenceManager.putString(Constants.KEY_IMAGE,
                                        it1
                                    )
                                }
                                Intent(applicationContext, DashboardActivity::class.java).also {
                                    it.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(it)
                                    showToast("!!! Selamat Datang !!!")
                                }
                            }
                            else{
                                mAuth.signOut()
                                loading(false)
                                showToast("Login Gagal \n Pastikan akun anda telah terdaftar")
                            }

                        }

                }
                else{
                    mAuth.signOut()
                    loading(false)
                    showToast("Login Gagal \n Pastikan akun anda telah terdaftar")
                }
            }
    }

    private fun showToast(pesan: String){
        Toast.makeText(applicationContext, pesan, Toast.LENGTH_SHORT).show()
    }

    private fun notEmpty(email:String, pass: String, pass2: String):Boolean {

        if (email.isEmpty()){
            showToast("Email wajib terisi !")
            binding.etEmail.requestFocus()
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showToast("Email tidak valid !")
            binding.etEmail.requestFocus()
            return false
        }
        else if (pass.isEmpty()){
            showToast("Password belum terisi !")
            binding.etPassword.requestFocus()
            return false
        }
        else if (pass2.isEmpty()){
            showToast("Password belum terisi !")
            binding.etPassword.requestFocus()
            return false
        }
        else{
            return true
        }
    }

    private fun loading(isLoading: Boolean){
        if (isLoading){
            binding.btnLogin.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnLogin.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            Intent(this@LoginActivity, DashboardActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}