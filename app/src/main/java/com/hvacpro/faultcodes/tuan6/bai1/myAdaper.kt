package com.hvacpro.faultcodes.tuan6.bai1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.hvacpro.faultcodes.tuan6.R

class myAdaper(context: Context,private val resource: Int,private val objects: List<SinhVien>) : ArrayAdapter<SinhVien>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_lv, parent, false)
        val sinhvien = getItem(position)

        val tvID = view.findViewById<TextView>(R.id.id)
        val tvHOTen = view.findViewById<TextView>(R.id.hoTen)
        val tvPhong = view.findViewById<TextView>(R.id.phongBan)
        val tvTrangThai = view.findViewById<TextView>(R.id.trangThai)

        tvID.setText(sinhvien?.id.toString())
        tvHOTen.setText(sinhvien?.ten.toString())
        tvPhong.setText(sinhvien?.tenPhong.toString())
        tvTrangThai.setText(sinhvien?.trangThai.toString())
        return view
    }
}