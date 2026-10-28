package com.hvacpro.faultcodes.tuan6.bai2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.bai1.SinhVien
import com.hvacpro.faultcodes.tuan6.bai1.chitietsinhvien
import com.hvacpro.faultcodes.tuan6.bai1.themsinhvien
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity() {
    var select = false
    var selectall = 1
    val dsTen = mutableListOf<String>()
    val setTen= mutableSetOf<String>()
    private lateinit var adapTen: ArrayAdapter<String>

    val ds = mutableListOf<SinhVien>(
        SinhVien("SV01", "Nguyễn Văn A", "Dev", "Chính thức"),
        SinhVien("SV02", "Trần Thị B", "Kế toán", "Chính thức"),
        SinhVien("SV03", "Lê Văn C", "Kinh doanh", "Thực tập")
    )
    val adap = myAdaper(ds,
        onClick = {sv->xemtt(sv)},
        onLongClick ={sv, view ->hienpopup(sv, view)}
    )
    fun xemtt(sv: SinhVien){
        if(!select){
            val intent = Intent(this, chitietsinhvien::class.java)
            layThongTin(intent, sv)
            startActivity(layThongTin(intent, sv))
        }
        else{
            val a = ds.indexOf(sv)
            if(adap.dsSelect.contains(sv.id)){
                adap.dsSelect.remove(sv.id)
                adap.notifyItemChanged(a)
            }
            else{
                adap.dsSelect.add(sv.id)
                adap.notifyItemChanged(a)
            }
            binding.tv.text = adap.dsSelect.size.toString()
        }
    }
    fun hienpopup(sv: SinhVien, view:View){
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.chinhSua) {
                val intent = Intent(this, themsinhvien::class.java)
                updateStudentLauncher.launch(layThongTin(intent, sv))
            } else {
                val pos = adap.getPosition(sv)
                adap.xoa(sv)
                adap.notifyItemRemoved(pos)
            }
            true
        }
        popup.show()
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

            if (position >= 0) {
                adap.upDate(position, sinhVien)
                adap.notifyItemChanged(position)
            }
        }
    }
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
            val a = ds.indexOf(sinhVien)
            adap.notifyItemInserted(adap.getPosition(sinhVien))
        }
    }
    private lateinit var binding: ActivityMain4Binding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Tao adap cho search
         adapTen = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dsTen)
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter = adap

        binding.rcv.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
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
            select = false
            adap.dsSelect.clear()
            adap.notifyDataSetChanged()
            binding.tv.text = adap.dsSelect.size.toString()
        }
        //nút search
        binding.search.setOnClickListener {
            binding.tbstart.visibility = View.GONE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.VISIBLE
            adapTen.clear()
            setTen.clear()
            for(i in ds){
                setTen.add(i.ten)
            }
            for(i in setTen){
               adapTen.add(i)
        }
            adapTen.filter.filter("")
            adapTen.notifyDataSetChanged()
        }
        //set sự kiện thay đổi text
        binding.editSearch.doAfterTextChanged { text ->
            adap.filter(text.toString())
            adap.notifyDataSetChanged()
        }
        //Nút cancle 2
        binding.cancel2.setOnClickListener {
            binding.tbstart.visibility = View.VISIBLE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.GONE
            binding.editSearch.setText("")
            adap.filter("")
            dsTen.clear()
            adap.notifyDataSetChanged()
        }
        //Them sinh vien
        binding.btnThem.setOnClickListener {
            val intent = Intent(this, themsinhvien::class.java)
            addStudentLauncher.launch(intent)
        }
        //Chon tat ca
        binding.selectall.setOnClickListener {
            selectall = kiemtra()
            if(selectall == 1 || selectall == 3){
                for(i in ds){
                    adap.dsSelect.add(i.id)
                }
                selectall = 2
                adap.notifyDataSetChanged()
                binding.tv.text = adap.dsSelect.size.toString()
            }
            else if(selectall == 2){
                adap.dsSelect.clear()
                selectall = 1
                adap.notifyDataSetChanged()
                binding.tv.text = adap.dsSelect.size.toString()
            }

        }
        //nút bin
        binding.bin.setOnClickListener {
            for(i in adap.dsSelect.toList()){
                val sv = ds.find { it.id == i}
                adap.xoa(sv)
            }
            adap.dsSelect.clear()
            adap.notifyDataSetChanged()
            binding.tv.text = adap.dsSelect.size.toString()
        }
        //nút search
        binding.editSearch.setAdapter(adapTen)

        binding.editSearch.setOnItemClickListener{parent, view, position, id ->
            val ten = parent.getItemAtPosition(position).toString()
            adap.filter(ten)
            adap.notifyDataSetChanged()
        }
    }
    fun layThongTin(intent: Intent,sinhVien: SinhVien): Intent {
        val id = sinhVien.id
        val ten = sinhVien.ten
        val phong = sinhVien.tenPhong
        val trangThai = sinhVien.trangThai
        intent.putExtra("ten", ten)
        intent.putExtra("id", id)
        intent.putExtra("phong", phong)
        intent.putExtra("trang thai", trangThai)
        intent.putExtra("position", adap.getPosition(sinhVien))
        return intent
    }
    fun kiemtra(): Int {
        if (adap.dsSelect.isEmpty()) {
            return 1
        } else if (adap.dsSelect.size == ds.size) {
            return 2
        } else if (adap.dsSelect.size < ds.size) {
            return 3
        }
        return 0
    }
}