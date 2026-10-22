package com.hvacpro.faultcodes.tuan6.bai1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMain2Binding
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMainBinding
import com.hvacpro.faultcodes.tuan6.databinding.ActivityThemsinhvienBinding

class themsinhvien : AppCompatActivity() {
    val dsPhong = mutableListOf<String>("Kinh doanh", "Dev", "Kế toán")
    val dsTrangThai = mutableListOf<String>("Chính thức", "Thực tập")
    private lateinit var binding : ActivityThemsinhvienBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThemsinhvienBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapPhong = ArrayAdapter(this, android.R.layout.simple_list_item_1, dsPhong)
        val adapTrangThai = ArrayAdapter(this, android.R.layout.simple_list_item_1, dsTrangThai)
        binding.phong.adapter = adapPhong
        binding.trangthai.adapter = adapTrangThai

        val ten = intent.getStringExtra("ten")
        val id = intent.getStringExtra("id")
        val phong = intent.getStringExtra("phong")
        val trangThai = intent.getStringExtra("trang thai")
        binding.edtID.setText(id)
        binding.ten.setText(ten)
        when(phong){
            "Kinh doanh"->binding.phong.setSelection(0)
            "Dev"->binding.phong.setSelection(1)
            "Kế toán"->binding.phong.setSelection(2)
        }
        when(trangThai){
            "Chính thức"->binding.trangthai.setSelection(0)
            "Thực tập"->binding.trangthai.setSelection(1)
        }

        binding.btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("id",binding.edtID.text.toString())
            intent.putExtra("ten",binding.ten.text.toString())
            intent.putExtra("phong",binding.phong.selectedItem.toString())
            intent.putExtra("trang thai",binding.trangthai.selectedItem.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}