package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class ESqliteHelperUsuario (contexto: Context?): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario=
            """
                CREATE TABLE USUARIO(id INTEGER PRIMARY KEY AUTOINCREMENT, 
                nombre VARCHAR(50),
                descripcion VARCHAR (50))
            """.trimIndent()
        Log.i("bdd", "Creacion tabla usuario")
        db?.execSQL(scriptCrearTablaUsuario)

    }

    fun crearUsuarioFormulario(nombre: String, descripcion: String):Boolean{
        val conexionEscritura= writableDatabase
        val valoresAGuardar=ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoEscritura: Long= conexionEscritura.insert("USUARIO", null,valoresAGuardar)
        conexionEscritura.close()
        return if (resultadoEscritura.toInt()==-1) false else true
    }

    fun consultarUsuarioPorId(id:Int): EUsuarioBDD{
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID = ${id}"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        val arregloUsuario = arrayListOf<EUsuarioBDD>()
        val usuarioEencontrad = EUsuarioBDD(0,"","")
        if(existeUsuario){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                val nombre = resultaConsultaLectura.getString(1)
                val descripcion = resultaConsultaLectura.getString(2)
                if(id!=null){
                    usuarioEencontrad.id = id
                    usuarioEencontrad.nombre = nombre
                    usuarioEencontrad.descripcion = descripcion
                    //arregloUsuario.add(usuarioEncontrado)
                }
            }while(resultaConsultaLectura.moveToNext())

        }

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEencontrad
    }

    fun eliminarUsuarioFomrulario(id: Int):Boolean{
        val conexionEscritura = readableDatabase
        val resultadoEliminacion = conexionEscritura.delete("USUARIO","id=?",
            arrayOf(id.toString()))
        conexionEscritura.close()
        return  if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarUsuarioFormulario(nombre:String, descripcion:String, idActualizar:Int): Boolean {
        val conexionEscritura = writableDatabase
        val valorAActualizar = ContentValues()
        valorAActualizar.put("nombre",nombre)
        valorAActualizar.put("descripcion",descripcion)
        val resultadoActualizacion = conexionEscritura.update(
            "USUARIO",
            valorAActualizar,
            "id=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }


    fun devolverUsuariosFormulario(): ArrayList<EUsuarioBDD> {
        val scriptConsultarUsuario = "SELECT * FROM USUARIO"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        var arregloUsuario = arrayListOf<EUsuarioBDD>()
        if(existeUsuario){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                if(id!=null){
                    arregloUsuario.add(EUsuarioBDD(id,resultaConsultaLectura.getString(1),resultaConsultaLectura.getString(2)))
                }
            }while(resultaConsultaLectura.moveToNext())
            Log.i("bdd", "Se devolvieron todos los usuarios")
        }

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloUsuario
    }

 fun agregarAlArray(ArrayDeUsuarios: ArrayList<EUsuarioBDD>, usuarioNuevo: EUsuarioBDD): ArrayList<EUsuarioBDD> {
     ArrayDeUsuarios.add(usuarioNuevo)
     println(ArrayDeUsuarios.toString())
     return ArrayDeUsuarios
}


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}