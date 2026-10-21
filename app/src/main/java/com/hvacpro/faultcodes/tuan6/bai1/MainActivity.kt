package com.hvacpro.faultcodes.tuan6.bai1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMain2Binding
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var select = false
    var total = 0
    private lateinit var binding : ActivityMain2Binding
    val ds = mutableListOf("Tuấn", "Tài", "TÚ")
    private lateinit var adap : ArrayAdapter<String>
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val ten = result.data?.getStringExtra("ten")
            if (!ten.isNullOrEmpty()) {
                ds.add(ten)
                adap.notifyDataSetChanged()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //nút chọn
        binding.select.setOnClickListener {
            binding.tbstart.visibility = View.GONE
            binding.tbselect.visibility = View.VISIBLE
            binding.tbsearch.visibility = View.GONE
            select = true
        }
        //nút cancle
        binding.cancel.setOnClickListener {
            binding.tbstart.visibility = View.VISIBLE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.GONE
            for(i in 0 until binding.lv.count){
                binding.lv.setItemChecked(i,false)
            }
            binding.tv.setText(binding.lv.checkedItemCount.toString())
            select = false
        }
        //tạo adaper
        adap = ArrayAdapter(this, android.R.layout.simple_list_item_1, ds)
        binding.lv.adapter = adap
        //nút thêm sinh viên
        binding.btnThem.setOnClickListener {
            val intent = Intent(this, themsinhvien::class.java)
            addStudentLauncher.launch(intent)
        }
        //đổi chế độ chọn
        binding.lv.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        //set sự kiên chọn item trong listview
        binding.lv.setOnItemClickListener { parent, view, position, id ->
            if(!select) {
                val ten = ds[position]
                val intent = Intent(this, chitietsinhvien::class.java)
                intent.putExtra("ten", ten)
                startActivity(intent)
            }
            else{
                binding.tv.setText(binding.lv.checkedItemCount.toString())
            }
        }
        //nút chọn tất cả
        binding.selectall.setOnClickListener {
            for(i in 0 until binding.lv.count){
                binding.lv.setItemChecked(i,true)
            }
            binding.tv.setText(binding.lv.checkedItemCount.toString())
        }
        binding.editSearch.setAdapter(adap)
        //nút search
        binding.search.setOnClickListener {
            binding.tbstart.visibility = View.GONE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.VISIBLE
        }
        //bắt sự kiện nhấn item
        binding.editSearch.setOnItemClickListener { parent, view, position, id ->
            val tenDuocChon = parent.getItemAtPosition(position).toString()
            adap.filter.filter(tenDuocChon)
        }
        //Nút cancle 2
        binding.cancel2.setOnClickListener {
            binding.tbstart.visibility = View.VISIBLE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.GONE
        }
        // nuts bin
        binding.bin.setOnClickListener {

        }
    }
}