package com.example.gunluksqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gunluksqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: GunlukDataBaseHelper
    private lateinit var gunlukAdapter: GunlukAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = GunlukDataBaseHelper(this)
        gunlukAdapter = GunlukAdapter(db.getAllGunluk(),this)

        binding.gunlukRecView.layoutManager = LinearLayoutManager(this)
        binding.gunlukRecView.adapter = gunlukAdapter

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddGunlukActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        gunlukAdapter.refreshData(db.getAllGunluk())
    }
}

