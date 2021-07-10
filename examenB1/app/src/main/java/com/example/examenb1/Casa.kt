package com.example.examenb1

class Casa (

    var id:Int,
    var id_usuario:Int?,
    var numcasa: String,
    var direccion: String,
    var terrenoArea:Double ,
    var construccionArea:Double,
    var parqueaderos:Int,
    var avaluo:Double,
    var bodega:Boolean,
){

    override fun toString(): String {
        return  "${id} - " +
                "${id_usuario} - " +
                "${numcasa} - " +
                "${direccion} - " +
                "${terrenoArea} - " +
                "${construccionArea} - " +
                "${parqueaderos} - " +
                "${avaluo} - " +
                "${bodega}"
    }

}