package com.example.kotlin_tut_sqlite_crud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_tut_sqlite_crud.adapter.DataListAdapter
import com.example.kotlin_tut_sqlite_crud.database.DatabaseHelper
import com.example.kotlin_tut_sqlite_crud.model.AddData
import com.example.kotlin_tut_sqlite_crud.model.DataListModel

class MainActivity : AppCompatActivity() {
    lateinit var recycler_data : RecyclerView
    lateinit var btn_add : Button
    var dataListAdapter : DataListAdapter ?= null
    var dbHandler : DatabaseHelper ?= null
    var dataList : List<DataListModel> = ArrayList<DataListModel>()
    var linearLayoutManager : LinearLayoutManager ?= null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_data = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.bt_add_items)

        dbHandler = DatabaseHelper(this)
        fetchlist()

        btn_add.setOnClickListener {
            val i = Intent(applicationContext, AddData::class.java)
            startActivity(i)
        }



    }
    private  fun fetchlist(){
        dataList = dbHandler!!.getAllData()
        dataListAdapter = DataListAdapter(dataList, applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        recycler_data.layoutManager = linearLayoutManager
        recycler_data.adapter = dataListAdapter
        dataListAdapter?.notifyDataSetChanged()

    }

}