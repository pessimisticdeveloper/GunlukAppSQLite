package com.example.gunluksqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gunluksqlite.databinding.ActivityReadBinding

class ReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadBinding
    private lateinit var db: GunlukDataBaseHelper
    private var gunlukId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = GunlukDataBaseHelper(this)
        gunlukId = intent.getIntExtra("gunluk_id",-1)
        if (gunlukId == -1){
            finish()
            return
        }
        val gunluk = db.getGunlukById(gunlukId)
        binding.baslik.setText(gunluk.title)
        binding.icerik.setText(gunluk.content)


        binding.off.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}