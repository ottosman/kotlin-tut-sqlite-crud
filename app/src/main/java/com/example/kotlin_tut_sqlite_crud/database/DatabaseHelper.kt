package com.example.kotlin_tut_sqlite_crud.database

import android.annotation.SuppressLint

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.Data
import com.example.kotlin_tut_sqlite_crud.model.DataListModel

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        private val DB_NAME = "pehozgun_verita"
        private val DB_VERSION = 1
        private val TABLE_NAME = "musteri"
        private val ID = "id"
        private val CARIADI = "cariadi"
        private val VNO = "vno"
        private val VERGIDAIRESI = "vergidairesi"
        private val ADRES = "adres"
        private val TELEFON = "telefon"
        private val SEHIR = "sehir"


    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME( $ID INTEGER PRIMARY KEY, $CARIADI TEXT, $VNO TEXT, $VERGIDAIRESI TEXT, $ADRES TEXT, $TELEFON TEXT, $SEHIR TEXT);"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    @SuppressLint("Range")
    fun getAllData(): List<DataListModel>{
        val datalist = ArrayList<DataListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val datas = DataListModel()
                    datas.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    datas.cariadi = cursor.getString(cursor.getColumnIndex(CARIADI))
                    datas.vno = cursor.getString(cursor.getColumnIndex(VNO))
                    datas.vergidairesi = cursor.getString(cursor.getColumnIndex(VERGIDAIRESI))
                    datas.adres = cursor.getString(cursor.getColumnIndex(ADRES))
                    datas.telefon = cursor.getString(cursor.getColumnIndex(TELEFON))
                    datas.sehir = cursor.getString(cursor.getColumnIndex(SEHIR))
                    datalist.add(datas)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return datalist
    }
    fun addData(datas : DataListModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CARIADI, datas.cariadi)
        values.put(VNO, datas.vno)
        values.put(VERGIDAIRESI, datas.vergidairesi)
        values.put(ADRES, datas.adres)
        values.put(TELEFON, datas.telefon)
        values.put(SEHIR, datas.sehir)
        val _success = db.insert(TABLE_NAME,null,values)
        db.close()
        return(Integer.parseInt("$_success") != -1 )
    }

    @SuppressLint("Range")
    fun getData(_id: Int) : DataListModel{
        val datas = DataListModel()
        val db = writableDatabase
        val selectQuery = "SELECT *FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)


        cursor.moveToFirst()
        datas.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        datas.cariadi = cursor.getString(cursor.getColumnIndex(CARIADI))
        datas.vno = cursor.getString(cursor.getColumnIndex(VNO))
        datas.vergidairesi = cursor.getString(cursor.getColumnIndex(VERGIDAIRESI))
        datas.adres = cursor.getString(cursor.getColumnIndex(ADRES))
        datas.telefon = cursor.getString(cursor.getColumnIndex(TELEFON))
        datas.sehir = cursor.getString(cursor.getColumnIndex(SEHIR))
        cursor.close()
        return datas
    }

    fun deleteData(_id : Int) : Boolean{
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun updateData(datas : DataListModel) :Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CARIADI, datas.cariadi)
        values.put(VNO, datas.vno)
        values.put(VERGIDAIRESI, datas.vergidairesi)
        values.put(ADRES, datas.adres)
        values.put(TELEFON, datas.telefon)
        values.put(SEHIR, datas.sehir)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(datas.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}

































