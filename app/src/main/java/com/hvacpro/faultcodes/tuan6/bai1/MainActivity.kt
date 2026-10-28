package com.hvacpro.faultcodes.tuan6.bai1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMain2Binding
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var select = false
    var selectall = 1
    private lateinit var binding: ActivityMain2Binding

    val dsTen = mutableListOf<String>()
    val ds = mutableListOf<SinhVien>(
        SinhVien("SV01", "Nguyễn Văn A", "Dev", "Chính thức"),
        SinhVien("SV02", "Trần Thị B", "Kế toán", "Chính thức"),
        SinhVien("SV03", "Lê Văn C", "Kinh doanh", "Thực tập")
    )
    private lateinit var adap: myAdaper
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val ten = intent?.getStringExtra("ten") ?: ""
            val id = intent?.getStringExtra("id") ?: ""
            val phong = intent?.getStringExtra("phong") ?: ""
            val trangThai = intent?.getStringExtra("trang thai") ?: ""
            val sinhVien = SinhVien(id, ten, phong, trangThai)
            adap.add(sinhVien)
            adap.filter.filter("")
        }
    }
    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val ten = intent?.getStringExtra("ten") ?: ""
            val id = intent?.getStringExtra("id") ?: ""
            val phong = intent?.getStringExtra("phong") ?: ""
            val trangThai = intent?.getStringExtra("trang thai") ?: ""
            val position = intent?.getIntExtra("position", 0) ?: 0
            val sinhVien = SinhVien(id, ten, phong, trangThai)
            val idx = ds.indexOf(adap.getItem(position))
            if (idx >= 0) {
                ds[idx] = sinhVien
            }
            adap = myAdaper(this, R.layout.item_lv, ds)
            binding.lv.adapter = adap
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
            //đổi chế độ chọn
            binding.lv.choiceMode = ListView.CHOICE_MODE_MULTIPLE
            select = true
        }
        //nút cancle
        binding.cancel.setOnClickListener {
            binding.tbstart.visibility = View.VISIBLE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.GONE
            binding.lv.clearChoices()
            binding.tv.setText(binding.lv.checkedItemCount.toString())
            //đổi chế độ chọn
            binding.lv.choiceMode = ListView.CHOICE_MODE_NONE
            select = false
            for (i in 0 until binding.lv.count) {
                cb(i)
            }
        }
        //tạo adaper
        adap = myAdaper(this, R.layout.item_lv, ds)
        binding.lv.adapter = adap

        //nút thêm sinh viên
        binding.btnThem.setOnClickListener {
            val intent = Intent(this, themsinhvien::class.java)
            addStudentLauncher.launch(intent)
        }

        //set sự kiên chọn item trong listview
        binding.lv.setOnItemClickListener { parent, view, position, id ->
            if (!select) {
                val intent = Intent(this, chitietsinhvien::class.java)
                startActivity(layThongTin(intent, position))
            } else {
                binding.tv.setText(binding.lv.checkedItemCount.toString())
                cb(position)
            }
        }
        //nút chọn tất cả
        binding.selectall.setOnClickListener {
            selectall = kiemtra()
            if (selectall == 1 || selectall == 3) {
                for (i in 0 until binding.lv.count) {
                    binding.lv.setItemChecked(i, true)
                    cb(i)
                }
                selectall = 2
            }
            else if(selectall == 2){
                for (i in 0 until binding.lv.count) {
                    binding.lv.setItemChecked(i, false)
                    cb(i)
                }
            }
            binding.tv.setText(binding.lv.checkedItemCount.toString())
        }
        //Truyen adap vao autocomplete
        val adapTen = ArrayAdapter(this, android.R.layout.simple_list_item_1, dsTen)
        binding.editSearch.setAdapter(adapTen)
        //nút search
        binding.search.setOnClickListener {
            binding.tbstart.visibility = View.GONE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.VISIBLE
            dsTen.clear()
            for (sinhVien in ds) {
                dsTen.add(sinhVien.ten)
            }
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
            dsTen.clear()
            binding.editSearch.setText("")
            adap.filter.filter("")
        }
        // nuts bin
        binding.bin.setOnClickListener {
            val a = binding.lv.checkedItemPositions
            for (i in ds.size - 1 downTo 0) {
                if (a.get(i)) {
                    adap.remove(adap.getItem(i))
                }
            }
            adap.notifyDataSetChanged()
            binding.lv.clearChoices()
            binding.tv.text = binding.lv.checkedItemCount.toString()
        }

        //tạo popup
        binding.lv.setOnItemLongClickListener { parent, view, position, id ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.popup, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.chinhSua) {
                    val intent = Intent(this, themsinhvien::class.java)
                    layThongTin(intent, position)
                    updateStudentLauncher.launch(intent)
                } else {
                    adap.remove(adap.getItem(position))
                    adap.notifyDataSetChanged()
                }
                true
            }
            popup.show()
            true
        }
    }

    fun layThongTin(intent: Intent, position: Int): Intent {
        val sinhVien = adap.getItem(position)
        val id = sinhVien?.id
        val ten = sinhVien?.ten
        val phong = sinhVien?.tenPhong
        val trangThai = sinhVien?.trangThai
        intent.putExtra("ten", ten)
        intent.putExtra("id", id)
        intent.putExtra("phong", phong)
        intent.putExtra("trang thai", trangThai)
        intent.putExtra("position", position)
        return intent
    }

    fun cb(position: Int) {
        val a = binding.lv.firstVisiblePosition
        val b = position - a
        val item = binding.lv.getChildAt(b)
        val cb = item.findViewById<CheckBox>(R.id.cb)
        if (binding.lv.isItemChecked(position)) {
            cb.visibility = View.VISIBLE
            cb.isChecked = true
        } else {
            cb.visibility = View.GONE
            cb.isChecked = false
        }
    }

    fun kiemtra(): Int {
        if (binding.lv.checkedItemCount == 0) {
            return 1
        } else if (binding.lv.count == binding.lv.checkedItemCount) {
            return 2
        } else if (binding.lv.checkedItemCount < binding.lv.count) {
            return 3
        }
        return 0
    }

}