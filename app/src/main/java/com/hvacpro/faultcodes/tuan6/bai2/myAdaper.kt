package com.hvacpro.faultcodes.tuan6.bai2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.bai1.SinhVien

class myAdaper(private val ds: MutableList<SinhVien>,
               private val onClick: ((SinhVien)-> Unit),
               private val onLongClick: ((SinhVien, View)-> Unit)

) : RecyclerView.Adapter<myAdaper.VH>(), Filterable{
    val dsSelect = mutableSetOf<String>()
    var dsHien: MutableList<SinhVien> = ds.toMutableList()
    fun filter(s:String){
        if(s == ""){
            dsHien.clear()
            for(i in ds){
                    dsHien.add(i)
            }
        }
        else{
            dsHien.clear()
            for(i in ds){
                if(i.ten == s){
                    dsHien.add(i)
                }
            }
        }
    }
    fun xoa(sv:SinhVien?){
        ds.remove(sv)
        dsHien.remove(sv)
    }
    fun upDate(idx: Int, sinhVien: SinhVien){
        val a = ds.indexOf(dsHien[idx])
        ds[a] = sinhVien
        dsHien[idx] = sinhVien
    }
    fun getPosition(sv: SinhVien): Int {
        return dsHien.indexOf(sv)
    }
    fun add(sv: SinhVien){
        ds.add(sv)
        dsHien.add(sv)
    }
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        val ten = itemView.findViewById<TextView>(R.id.hoTen)
        val id = itemView.findViewById<TextView>(R.id.id)
        val phong = itemView.findViewById<TextView>(R.id.phongBan)
        val trangThai = itemView.findViewById<TextView>(R.id.trangThai)
        val cb = itemView.findViewById<CheckBox>(R.id.cb)
        init {
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onClick(dsHien[pos])
                }
            }
            itemView.setOnLongClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onLongClick(dsHien[pos], it)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val b = LayoutInflater.from(parent.context).inflate(R.layout.item_lv, parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val sv = dsHien.get(position)
        holder.ten.text = sv.ten
        holder.id.text = sv.id
        holder.phong.text = sv.tenPhong
        holder.trangThai.text = sv.trangThai
        if(dsSelect.contains(sv.id)){
            holder.cb.isChecked = true
            holder.cb.visibility = View.VISIBLE
        }
        else{
            holder.cb.isChecked = false
            holder.cb.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dsHien.size
    }

    override fun getFilter(): Filter? {
        TODO("Not yet implemented")
    }
}