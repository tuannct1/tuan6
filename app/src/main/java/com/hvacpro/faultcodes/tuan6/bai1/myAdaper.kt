package com.hvacpro.faultcodes.tuan6.bai1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.hvacpro.faultcodes.tuan6.R

class myAdaper(context: Context,private val resource: Int,private val objects: List<Student>) : ArrayAdapter<Student>(context, resource, objects) {


    fun getListName(): List<String>{
        val setName = mutableSetOf<String>()
        val listName = mutableListOf<String>()
        for(i in objects){
            setName.add(i.name)
        }
        for(i in setName){
            listName.add(i)
        }
        return listName
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_lv, parent, false)
        val sinhvien = getItem(position)

        val tvID = view.findViewById<TextView>(R.id.id)
        val tvHOTen = view.findViewById<TextView>(R.id.hoTen)
        val tvPhong = view.findViewById<TextView>(R.id.phongBan)
        val tvTrangThai = view.findViewById<TextView>(R.id.trangThai)

        tvID.text = sinhvien?.id.toString()
        tvHOTen.text = sinhvien?.name.toString()
        tvPhong.text = sinhvien?.room.toString()
        tvTrangThai.text = sinhvien?.status.toString()
        return view
    }
}