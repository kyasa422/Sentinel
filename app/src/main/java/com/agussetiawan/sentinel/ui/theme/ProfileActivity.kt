package com.agussetiawan.sentinel.ui.theme

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.agussetiawan.sentinel.R
import com.agussetiawan.sentinel.databinding.ActivityProfileBinding
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.io.File





class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ProfileActivity, R.layout.activity_profile)
        setSupportActionBar(binding.topAppbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val user = Firebase.auth.currentUser
        if(user != null){
            Glide.with(this@ProfileActivity)
                .load(user.photoUrl)
                .into(binding.avatar)
            binding.name.text = user.displayName
            binding.email.text = user.email

            binding.signoutBtn.setOnClickListener {
                Firebase.auth.signOut()
                FirebaseAuth.getInstance().signOut();
                user.delete()
                StorageUtils.clearApplicationData(this)

                finish()

            }
        }else{
            finish()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun restartApp(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }


    object StorageUtils {
        fun clearApplicationData(context: Context) {
            val cacheDirectory = context.cacheDir
            val applicationDirectory = File(cacheDirectory.parent)
            if (applicationDirectory.exists()) {
                val fileNames = applicationDirectory.list()
                for (fileName in fileNames) {
                    if (fileName != "lib") {
                        deleteFile(File(applicationDirectory, fileName))
                    }
                }
            }
        }

        private fun deleteFile(file: File): Boolean {
            var deletedAll = true
            if (file.isDirectory) {
                val children = file.list()
                for (aChildren in children) {
                    deletedAll = deleteFile(File(file, aChildren)) && deletedAll
                }
            } else {
                deletedAll = file.delete()
            }
            return deletedAll
        }
    }


}