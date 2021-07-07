package com.example.myapplication

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
           arregloBEntrenador.add(BEntrenador("Adrian","a@a.com",null))
           //arregloBEntrenador.add(BEntrenador("Vicente","b@b.com"))
            //arregloBEntrenador.add(BEntrenador("Carlina","c@c.com"))
        }
    }
}