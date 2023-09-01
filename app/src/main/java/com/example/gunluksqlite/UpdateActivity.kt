package com.example.gunluksqlite


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gunluksqlite.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db : GunlukDataBaseHelper
    private var gunlukId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = GunlukDataBaseHelper(this)
        gunlukId = intent.getIntExtra("gunluk_id",-1)
        if (gunlukId == -1){
            finish()
            return
        }
        val gunluk = db.getGunlukById(gunlukId)
        binding.updatetitleEdt.setText(gunluk.title)
        binding.updatecontentEdt.setText(gunluk.content)


        binding.updatesavebtn.setOnClickListener {
            val newTitle = binding.updatetitleEdt.text.toString()
            val newContent = binding.updatecontentEdt.text.toString()
            if (newTitle.length != 0 && newContent.length != 0){
                val updateGunluk = Gunluk(gunlukId,newTitle,newContent)
                db.updateGunluk(updateGunluk)
                finish()
                Toast.makeText(this,"Düzenleme Kayıt Edildi.",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Alanları Boş Bırakamazsın.",Toast.LENGTH_SHORT).show()
            }

        }
    }
}