package com.hvacpro.faultcodes.tuan6

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val ds = arrayOf("Tuấn", "Tài", "TÚ")
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adap = ArrayAdapter(this, android.R.layout.simple_list_item_1,ds)
        binding.lv.adapter = adap
        binding.gv.adapter = adap
        binding.sp.adapter = adap
    }
}