package com.hvacpro.faultcodes.tuan6.bai1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan6.R
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMain2Binding
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMainBinding
import com.hvacpro.faultcodes.tuan6.databinding.ActivityThemsinhvienBinding

class themsinhvien : AppCompatActivity() {
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
        val ten = intent.getStringExtra("ten")
        binding.edt.setText(ten)
        binding.btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("ten",binding.edt.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}