package com.example.gunluksqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GunlukDataBaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "gunlukapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allgunluk"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        p0?.execSQL(createTableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(dropTableQuery)
        onCreate(p0)
    }
    fun insertGunluk(gunluk:Gunluk){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,gunluk.title)
            put(COLUMN_CONTENT, gunluk.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getAllGunluk():List<Gunluk>{
        val gunlukList = mutableListOf<Gunluk>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val gunluk = Gunluk(id,title,content)
            gunlukList.add(gunluk)
        }
        cursor.close()
        db.close()
        return gunlukList
    }
    fun updateGunluk(gunluk: Gunluk){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,gunluk.title)
            put(COLUMN_CONTENT, gunluk.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(gunluk.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun getGunlukById(gunlukId: Int):Gunluk{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $gunlukId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Gunluk(id, title, content)
    }
    fun deleteGunluk(gunlukId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(gunlukId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}