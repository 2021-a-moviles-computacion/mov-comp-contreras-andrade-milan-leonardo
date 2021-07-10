package com.example.examenb1

import java.text.SimpleDateFormat
import java.util.*

class Usuario (
    var id:Int,
    var cedula: String,
    var nombre: String,
    var apellido:String ,
    var telefono:String,
    var fechaNacimiento: Date
){

    override fun toString(): String {
        return  "${id} - " +
                "${cedula} - " +
                "${nombre} - " +
                "${apellido} - " +
                "${telefono} - " +
                "${SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento)}"

    }

}