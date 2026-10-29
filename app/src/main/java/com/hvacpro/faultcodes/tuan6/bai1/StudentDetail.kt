package com.hvacpro.faultcodes.tuan6.bai1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.databinding.ActivityChitietsinhvienBinding

public class StudentDetail : AppCompatActivity() {
    private lateinit var binding : ActivityChitietsinhvienBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChitietsinhvienBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val room = intent.getStringExtra("room")
        val status = intent.getStringExtra("status")
        binding.edtID.setText(id)
        binding.name.setText(name)
        binding.room.setText(room)
        binding.status.setText(status)
        binding.btn.setOnClickListener { finish() }
    }
}