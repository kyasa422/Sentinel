package com.agussetiawan.sentinel.ui.theme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.agussetiawan.sentinel.R
import com.agussetiawan.sentinel.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebase: Firebase
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(binding.appbar)
        firebase = Firebase
        val user = firebase.auth.currentUser

        val materialCardView = findViewById<MaterialCardView>(R.id.materialCardView)


        if(user == null){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        binding.addbtn.setOnClickListener {
            startActivity(Intent(this,AddPost::class.java))
        }
    }

    override fun onStart() {
        val user = firebase.auth.currentUser
        if(user == null){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.avatar -> startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}