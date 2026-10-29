package com.hvacpro.faultcodes.tuan6.bai1

data class Student(val id: String, val name: String, val room: String, val status: String) {
    override fun toString(): String {
        return name
    }
}