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
import com.hvacpro.faultcodes.tuan6.bai1.Student

class MyAdapter(private val ds: MutableList<Student>,
                private val onClick: ((Student)-> Unit),
                private val onLongClick: ((Student, View)-> Unit)

) : RecyclerView.Adapter<MyAdapter.VH>(), Filterable{
    val dsSelect = mutableSetOf<String>()
    var dsHien: MutableList<Student> = ds.toMutableList()
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
                if(i.name == s){
                    dsHien.add(i)
                }
            }
        }
    }
    fun xoa(sv:Student?){
        ds.remove(sv)
        dsHien.remove(sv)
    }
    fun upDate(idx: Int, student: Student){
        val a = ds.indexOf(dsHien[idx])
        ds[a] = student
        dsHien[idx] = student
    }
    fun getPosition(sv: Student): Int {
        return dsHien.indexOf(sv)
    }
    fun add(sv: Student){
        ds.add(sv)
        dsHien.add(sv)
    }
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.name)
        val id = itemView.findViewById<TextView>(R.id.id)
        val room = itemView.findViewById<TextView>(R.id.room)
        val status = itemView.findViewById<TextView>(R.id.status)
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
        val sv = dsHien[position]
        holder.name.text = sv.name
        holder.id.text = sv.id
        holder.room.text = sv.room
        holder.status.text = sv.status
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