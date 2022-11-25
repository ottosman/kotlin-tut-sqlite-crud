package com.example.kotlin_tut_sqlite_crud.model

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_tut_sqlite_crud.MainActivity
import com.example.kotlin_tut_sqlite_crud.R
import com.example.kotlin_tut_sqlite_crud.database.DatabaseHelper

class AddData : AppCompatActivity(){
    lateinit var btn_save : Button
    lateinit var btn_delete : Button
    lateinit var et_cariadi : EditText
    lateinit var et_vno : EditText
    lateinit var et_vergidairesi : EditText
    lateinit var et_adres : EditText
    lateinit var et_telefon : EditText
    lateinit var et_sehir : EditText
    var dbHandler : DatabaseHelper ?= null
    var isEditMode : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        btn_save = findViewById(R.id.btn_save)
        btn_delete = findViewById(R.id.btn_delete)

        et_cariadi = findViewById(R.id.et_cariadi)
        et_vno = findViewById(R.id.et_vno)
        et_vergidairesi = findViewById(R.id.et_vergidairesi)
        et_adres = findViewById(R.id.et_adres)
        et_telefon = findViewById(R.id.et_telefon)
        et_sehir = findViewById(R.id.et_sehir)

        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E"){
            //update
            isEditMode = true
            btn_save.text = "Update Data"
            btn_delete.visibility = View.VISIBLE
            val datas : DataListModel = dbHandler!!.getData(intent.getIntExtra("Id", 0))
            et_cariadi.setText(datas.cariadi)
            et_vno.setText(datas.vno)
            et_vergidairesi.setText(datas.vergidairesi)
            et_adres.setText(datas.adres)
            et_telefon.setText(datas.telefon)
            et_sehir.setText(datas.sehir)


        }else{
            //insert_new
            isEditMode = false
            btn_save.text = "Save Data"
            btn_delete.visibility = View.GONE
        }

        btn_save.setOnClickListener {
            var success : Boolean = false
            var datas : DataListModel = DataListModel()
            if (isEditMode){
                //update
                datas.id = intent.getIntExtra("Id", 0)
                datas.cariadi = et_cariadi.text.toString()
                datas.vno = et_vno.text.toString()
                datas.vergidairesi = et_vergidairesi.text.toString()
                datas.adres = et_adres.text.toString()
                datas.telefon = et_telefon.text.toString()
                datas.sehir = et_sehir.text.toString()

                success = dbHandler?.updateData(datas) as Boolean

            }else{
                //insert
                datas.cariadi = et_cariadi.text.toString()
                datas.vno = et_vno.text.toString()
                datas.vergidairesi = et_vergidairesi.text.toString()
                datas.adres = et_adres.text.toString()
                datas.telefon = et_telefon.text.toString()
                datas.sehir = et_sehir.text.toString()

                success = dbHandler?.addData(datas) as Boolean
            }
            if(success){
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_LONG).show()
            }
        }

        btn_delete.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click Yes If You Want t Delete Data").setPositiveButton("YES") { dialog, i ->
                val success = dbHandler?.deleteData(intent.getIntExtra("Id", 0)) as Boolean
                if (success){
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                    finish()}
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, i ->
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}