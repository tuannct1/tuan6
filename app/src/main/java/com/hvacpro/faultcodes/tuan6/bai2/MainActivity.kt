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
import com.hvacpro.faultcodes.tuan6.bai1.AddStudent
import com.hvacpro.faultcodes.tuan6.bai1.Student
import com.hvacpro.faultcodes.tuan6.bai1.StudentDetail
import com.hvacpro.faultcodes.tuan6.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity() {
    var selectStatus = false
    var selectAllStatus = "NONE"
    val dsTen = mutableListOf<String>()
    val setTen= mutableSetOf<String>()
    private lateinit var adapTen: ArrayAdapter<String>

    val ds = mutableListOf<Student>(
        Student("SV01", "Nguyễn Văn A", "Dev", "Chính thức"),
        Student("SV02", "Trần Thị B", "Kế toán", "Chính thức"),
        Student("SV03", "Lê Văn C", "Kinh doanh", "Thực tập")
    )
    val adap = MyAdapter(ds,
        onClick = {sv->showInfo(sv)},
        onLongClick ={sv, view ->showPopup(sv, view)}
    )
    fun showInfo(sv: Student){
        if(!selectStatus){
            val intent = Intent(this, StudentDetail::class.java)
            getStudentInfo(intent, sv)
            startActivity(getStudentInfo(intent, sv))
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
    fun showPopup(sv: Student, view:View){
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.chinhSua) {
                val intent = Intent(this, AddStudent::class.java)
                updateStudentLauncher.launch(getStudentInfo(intent, sv))
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
            val ten = intent?.getStringExtra("name") ?: ""
            val id = intent?.getStringExtra("id") ?: ""
            val phong = intent?.getStringExtra("room") ?: ""
            val trangThai = intent?.getStringExtra("status") ?: ""
            val position = intent?.getIntExtra("position", 0) ?: 0
            val student = Student(id, ten, phong, trangThai)

            if (position >= 0) {
                adap.upDate(position, student)
                adap.notifyItemChanged(position)
            }
        }
    }
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val ten = intent?.getStringExtra("name") ?: ""
            val id = intent?.getStringExtra("id") ?: ""
            val phong = intent?.getStringExtra("room") ?: ""
            val trangThai = intent?.getStringExtra("status") ?: ""
            val student = Student(id, ten, phong, trangThai)
            adap.add(student)
            val a = ds.indexOf(student)
            adap.notifyItemInserted(adap.getPosition(student))
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
            selectStatus = true
        }
        //nút cancle
        binding.cancel.setOnClickListener {
            binding.tbstart.visibility = View.VISIBLE
            binding.tbselect.visibility = View.GONE
            binding.tbsearch.visibility = View.GONE
            selectStatus = false
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
                setTen.add(i.name)
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
            val intent = Intent(this, AddStudent::class.java)
            addStudentLauncher.launch(intent)
        }
        //Chon tat ca
        binding.selectall.setOnClickListener {
            selectAllStatus = checkSelectAllStatus()
            if(selectAllStatus == "NONE" || selectAllStatus == "PARTIAL"){
                for(i in ds){
                    adap.dsSelect.add(i.id)
                }
                selectAllStatus = "ALL"
                adap.notifyDataSetChanged()
                binding.tv.text = adap.dsSelect.size.toString()
            }
            else if(selectAllStatus == "ALL"){
                adap.dsSelect.clear()
                selectAllStatus = "NONE"
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
    fun getStudentInfo(intent: Intent, student: Student): Intent {
        val id = student.id
        val name = student.name
        val room = student.room
        val status = student.status
        intent.putExtra("name", name)
        intent.putExtra("id", id)
        intent.putExtra("room", room)
        intent.putExtra("status", status)
        intent.putExtra("position", adap.getPosition(student))
        return intent
    }
    fun checkSelectAllStatus(): String {
        if (adap.dsSelect.isEmpty()) {
            return "NONE"
        } else if (adap.dsSelect.size == ds.size) {
            return "ALL"
        } else if (adap.dsSelect.size < ds.size) {
            return "PARTIAL"
        }
        return ""
    }
}