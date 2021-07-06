package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario(
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1,

) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
                CREATE TABLE USUARIO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion varchar(50)
            """.trimIndent()
        Log.i("bbd","Creando la tabla usuario")
    }

    fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String
    ):Boolean{
 //      val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)
        val resultadoEscritura: Long = conexionEscritura
            .insert(
                "USUARIO",
            null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return if(resultadoEscritura.toInt()==-1) false else true
    }


    fun consultarUsuarioPorId(id: Int):EUsuarioBDD {
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE id = ${id}"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )

        val existeUsuario = resultaConsultaLectura.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0, "","")
        if (existeUsuario) {
            do {
                val id = resultaConsultaLectura.getInt(0)
                val nombre = resultaConsultaLectura.getString(1)
                val descripcion =
                    resultaConsultaLectura.getString(2)
                if (id != null) {
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            } while (resultaConsultaLectura.moveToNext())
        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}