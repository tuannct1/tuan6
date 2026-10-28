package com.hvacpro.faultcodes.tuan6.bai1

class SinhVien {
    var id = ""
    var ten =""
    var tenPhong = ""
    var trangThai= ""
    constructor(id:String,ten:String, tenPhong:String, trangThai:String ){
        this.id = id
        this.ten = ten
        this.tenPhong = tenPhong
        this.trangThai = trangThai
    }

    override fun toString(): String {
        return ten
    }
}