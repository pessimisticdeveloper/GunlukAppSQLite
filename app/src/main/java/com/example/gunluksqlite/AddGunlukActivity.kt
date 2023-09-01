package com.example.gunluksqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gunluksqlite.databinding.ActivityAddGunlukBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddGunlukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGunlukBinding
    private lateinit var db: GunlukDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGunlukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        val dateTimeFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("tr", "TR"))
        val formattedDateTime = dateTimeFormat.format(calendar.time)

        db = GunlukDataBaseHelper(this)

        binding.savebtn.setOnClickListener {
            val title = "($formattedDateTime) \n " +" "+"\n" + binding.titleEdt.text.toString().uppercase(Locale.ROOT)
            val content = binding.contentEdt.text.toString()
            if (title.length != 0 && content.length != 0){
                val gunluk = Gunluk(0,title,content)
                db.insertGunluk(gunluk)
                finish()
                Toast.makeText(this,"Yazınız Kayıt Edildi.",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Alanları Boş Bırakamazsın.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}