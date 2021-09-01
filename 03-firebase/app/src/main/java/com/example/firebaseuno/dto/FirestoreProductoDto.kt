package com.example.firebaseuno.dto

class FirestoreProductoDto(
    var uid:String = "",
    var nombre:String = "",
    var precio: Double? = null
) {

    override fun toString(): String {
        return nombre
    }
}