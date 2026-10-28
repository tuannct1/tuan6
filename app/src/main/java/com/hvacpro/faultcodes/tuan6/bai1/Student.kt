package com.hvacpro.faultcodes.tuan6.bai1

class Student {
    var id = ""
    var name =""
    var room = ""
    var status= ""
    constructor(id:String, name:String, room:String, status:String ){
        this.id = id
        this.name = name
        this.room = room
        this.status = status
    }

    override fun toString(): String {
        return name
    }
}