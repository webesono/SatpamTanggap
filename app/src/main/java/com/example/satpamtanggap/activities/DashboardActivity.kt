package com.example.satpamtanggap.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.example.satpamtanggap.databinding.ActivityDashboardBinding
import com.example.satpamtanggap.utilities.Constants
import com.example.satpamtanggap.utilities.PreferenceManager
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.collections.HashMap

class DashboardActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        preferenceManager = PreferenceManager(applicationContext)
        loadUserDetails()
        getToken()
        setListeners()
    }

    private fun setListeners(){


        binding.layoutImage.setOnClickListener {
            changeActivity(ProfileActivity::class.java)
        }
        binding.laporan.setOnClickListener {
            changeActivity(ListLapActivity::class.java)
        }
        binding.warga.setOnClickListener {
            changeActivity(ListgaActivity::class.java)
        }
        binding.satpam.setOnClickListener {
            changeActivity(ListpamActivity::class.java)
        }
        binding.listChat.setOnClickListener{
            changeActivity(ContactActivity::class.java)
        }
        binding.imageSignOut.setOnClickListener{
            signOut()
        }
        binding.imageDeleteAccount.setOnClickListener {
            showToast("klik untuk hapus akun")
        }
    }

    private fun loadUserDetails(){

        binding.textNama.text = preferenceManager.getString(Constants.KEY_NAME)
        binding.textTelepon.text = preferenceManager.getString(Constants.KEY_TELEPON)

        val bytes: ByteArray = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT)
        val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0,bytes.size)
        binding.imageProfile.setImageBitmap(bitmap)
    }

    private fun showToast(pesan: String){
        Toast.makeText(applicationContext, pesan,Toast.LENGTH_SHORT).show()
    }

    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener (this::updateToken )
    }

    private fun updateToken(token: String){
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val documentReference: DocumentReference =
            database.collection(Constants.KEY_COLLECTION_SATPAM)
                .document(mAuth.currentUser?.uid!!
                )
    documentReference.update(Constants.KEY_FCM_TOKEN,token)
        .addOnFailureListener{ showToast("Tidak dapat melakukan update token")}
    }

    private fun signOut(){
        showToast("Signing out ...")
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val documentReference: DocumentReference =
            database.collection(Constants.KEY_COLLECTION_SATPAM)
                .document(preferenceManager.getString(Constants.KEY_USER_ID).toString())

        val updates: HashMap<String, FieldValue> = HashMap()
        updates[Constants.KEY_FCM_TOKEN] = FieldValue.delete()
        documentReference.update(updates as Map<String, Any>)
            .addOnSuccessListener {
                    mAuth.signOut()
                    preferenceManager.clear()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
            }
            .addOnFailureListener{showToast("Tidak dapat logout")}

    }

    private fun deleteAkun(){
        showToast("Deleting & Signing out ...")


        database.collection(Constants.KEY_COLLECTION_SATPAM)
            .document(preferenceManager.getString(Constants.KEY_USER_ID).toString())
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    mAuth.signOut()
                }
            }
    }

    private fun ubahPass(newPass: String){

        val user = mAuth.currentUser!!
        user.let {
            user.delete()
                .addOnCompleteListener {
                if (it.isSuccessful){
                    showToast("Berhasil menghapus akun!")
                }else{
                    showToast("Gagal menghapus akun")
                }
            }
        }
    }

    private fun reAuth(newPass: String, oldPass: String){
        val user = mAuth.currentUser!!
        val credential = EmailAuthProvider.getCredential(user.email.toString(), oldPass)

        user.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful){
                ubahPass(newPass)
                showToast("User re-Authenticated")
            }else{
                showToast("Gagal")
            }
        }
    }

    private fun changeActivity( kelas: Class<*>){
        startActivity(Intent(applicationContext,kelas))
    }
}