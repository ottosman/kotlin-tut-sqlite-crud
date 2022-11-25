package com.example.kotlin_tut_sqlite_crud.adapter

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_tut_sqlite_crud.R
import com.example.kotlin_tut_sqlite_crud.database.DatabaseHelper
import com.example.kotlin_tut_sqlite_crud.model.AddData
import com.example.kotlin_tut_sqlite_crud.model.DataListModel

class DataListAdapter(datalist : List<DataListModel>, internal var context: Context)
    : RecyclerView.Adapter<DataListAdapter.DataViewHolder>()
{
    internal var datalist : List<DataListModel> = ArrayList()
    init {
        this.datalist = datalist
    }

    inner class DataViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var cariadi : TextView = view.findViewById(R.id.txt_cariadi)
        var vno : TextView = view.findViewById(R.id.txt_vno)
        var vergidairesi : TextView = view.findViewById(R.id.txt_vergidairesi)
        var adres : TextView = view.findViewById(R.id.txt_adres)
        var telefon : TextView = view.findViewById(R.id.txt_telefon)
        var sehir : TextView = view.findViewById(R.id.txt_sehir)
        var btn_edit : Button = view.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_task_list, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val datas = datalist[position]
        holder.cariadi.text = datas.cariadi
        holder.vno.text = datas.vno
        holder.vergidairesi.text = datas.vergidairesi
        holder.adres.text = datas.adres
        holder.telefon.text = datas.telefon
        holder.sehir.text = datas.sehir
        holder.btn_edit.setOnClickListener {
            val i = Intent(context, AddData::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", datas.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)

        }
    }


    override fun getItemCount(): Int {
        return datalist.size
    }
}