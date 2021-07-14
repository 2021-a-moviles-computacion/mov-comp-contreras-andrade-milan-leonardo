package com.example.examenb1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getIntOrNull
import java.text.SimpleDateFormat
import java.util.*

class SQLiteHelper (contexto: Context?): SQLiteOpenHelper(
    contexto,
    "examen",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario=
            """
            CREATE TABLE USUARIO(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            cedula VARCHAR(10),
            nombre VARCHAR(50),
            apellido VARCHAR(50),
            telefono VARCHAR (10),
            fechaNacimiento date);
  
            """.trimIndent()

        db?.execSQL(scriptCrearTablaUsuario)

        val scriptQuemarDatosUsuario=
            """INSERT INTO USUARIO (cedula,nombre,apellido,telefono,fechaNacimiento) 
            VALUES 	("1234567891","NOMBRE1","APELLIDO1","0999999901","01/01/1990"),
		            ("1234567892","NOMBRE2","APELLIDO2","0999999902","01/02/1990"),
                    ("1234567893","NOMBRE3","APELLIDO3","0999999903","01/03/1990"),
                    ("1234567894","NOMBRE4","APELLIDO4","0999999904","01/04/1990"),
                    ("1234567895","NOMBRE5","APELLIDO5","0999999905","01/05/1990");""".trimIndent()
        db?.execSQL(scriptQuemarDatosUsuario)

        val scriptCrearTablaCasa=
            """
                CREATE TABLE CASA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER,
                numcasa VARCHAR(4),
                direccion VARCHAR(20),
                terrenoArea double,
                construccionArea DOUBLE,
                parqueaderos int,
                avaluo Double,
                bodega int,
                foreign key(id_usuario) references USUARIO1(id)
                );
            """.trimIndent()
        db?.execSQL(scriptCrearTablaCasa)


        val scriptQuemarDatosCasa=
            """
                INSERT INTO CASA (id_usuario,numcasa,direccion,terrenoarea,construccionarea,parqueaderos,avaluo,bodega) VALUES
                (1,"casa1.1","direccion1.1",10000,10000,1,500000,0),
                (1,"casa1.2","direccion1.2",10000,10000,0,500000,1),
                (2,"casa2","direccion2",10000,10000,0,500000,0), 
                (3,"casa3","direccion3",10000,10000,0,500000,1), 
                (4,"casa4","direccion4",10000,10000,1,500000,1), 
                (5,"casa5","direccion5",10000,10000,1,500000,1);
                
            """.trimIndent()
        db?.execSQL(scriptQuemarDatosCasa)

        Log.i("bdd", "Creacion tablas")
    }

    //Usuario=======================================================================================
    fun crearUsuarioFormulario(
        cedula: String,
        nombre: String,
        apellido:String ,
        telefono:String,
        fechaNacimiento:String
    ):Boolean{
        //val formato = SimpleDateFormat("dd/MM/yyyy")
        val conexionEscritura= writableDatabase
        val valoresAGuardar= ContentValues()

        valoresAGuardar.put("cedula",cedula)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("apellido", apellido)
        valoresAGuardar.put("telefono", telefono)
        valoresAGuardar.put("fechaNacimiento", fechaNacimiento)

        val resultadoEscritura: Long= conexionEscritura.insert("USUARIO", null,valoresAGuardar)
        conexionEscritura.close()
        if(resultadoEscritura.toInt()!=-1){

            Log.i("bdd",
                "Se Creo el usuario: cedula:${cedula} - " +
                        "nombre:${nombre} - " +
                        "apellido:${apellido} - " +
                        "telefono:${telefono} - " +
                        "fechaNacimiento:${fechaNacimiento} - ")
            return true
        }else{
            Log.i("bdd","Error: No se ha logrado crear el Usuario")
            return false

        }
        // return resultadoEscritura.toInt() != -1
    }

    fun crearCasaFormulario(
        id_usuario:Int?,
        numcasa: String,
        direccion: String,
        terrenoArea:Double ,
        construccionArea:Double,
        parqueaderos:Int,
        avaluo:Double,
        bodega:Int,
    ):Boolean{
        val conexionEscritura= writableDatabase
        val valoresAGuardar= ContentValues()
        valoresAGuardar.put("id_usuario",id_usuario)
        valoresAGuardar.put("numcasa", numcasa)
        valoresAGuardar.put("direccion", direccion)
        valoresAGuardar.put("terrenoArea", terrenoArea)
        valoresAGuardar.put("construccionArea", construccionArea)
        valoresAGuardar.put("parqueaderos", parqueaderos)
        valoresAGuardar.put("avaluo", avaluo)
        valoresAGuardar.put("bodega", bodega)
        val resultadoEscritura: Long= conexionEscritura.insert("CASA", null,valoresAGuardar)
        conexionEscritura.close()
        if(resultadoEscritura.toInt()!=-1){
            Log.i("bdd",
                "Se Creo la Casa: id_usuario:${id_usuario} - " +
                        "numcasa:${numcasa} - " +
                        "direccion:${direccion} - " +
                        "terrenoArea:${terrenoArea} - " +
                        "construccionArea:${construccionArea} - " +
                        "parqueaderos:${parqueaderos} - " +
                        "avaluo:${avaluo} - " +
                        "bodega:${bodega} - ")
            return true
        }else{
            Log.i("bdd","Error: No se ha logrado crear la Casa")
            return false
        }

        //return resultadoEscritura.toInt() != -1
    }

    fun consultarUsuarioPorId(id:Int): Usuario{
        val formato = SimpleDateFormat("dd/MM/yyyy")
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID = ${id}"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        val arregloUsuario = arrayListOf<Usuario>()
        val usuarioEencontrado = Usuario(0,"","","","", Date("01/01/1990"))
        if(existeUsuario){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                val cedula = resultaConsultaLectura.getString(1)
                val nombre = resultaConsultaLectura.getString(2)
                val apellido = resultaConsultaLectura.getString(3)
                val telefono = resultaConsultaLectura.getString(4)
                val fechaNacimiento = resultaConsultaLectura.getString(5)


                if(id!=null){
                    usuarioEencontrado.id = id
                    usuarioEencontrado.cedula = cedula
                    usuarioEencontrado.nombre = nombre
                    usuarioEencontrado.apellido = apellido
                    usuarioEencontrado.telefono = telefono
                    SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento)

                    //arregloUsuario.add(usuarioEncontrado)
                }
            }while(resultaConsultaLectura.moveToNext())

        }

        Log.i("bdd","Se ha consultado el Usuario por el id: ${id}")

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEencontrado
    }

    fun consultarUsuariosFormulario(): ArrayList<Usuario> {
        val formato = SimpleDateFormat("dd/MM/yyyy")
        val scriptConsultarUsuario = "SELECT * FROM USUARIO"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        var arregloUsuario = arrayListOf<Usuario>()

        if(existeUsuario){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                if(id!=null){
                    Log.i("actual", "${resultaConsultaLectura.getString(1)} \n" +
                            "${resultaConsultaLectura.getString(2)} \n" +
                            "${resultaConsultaLectura.getString(3)} \n" +
                            "${resultaConsultaLectura.getString(4)} \n" +
                            "${resultaConsultaLectura.getString(5)} \n")


                    arregloUsuario.add(
                        Usuario(id,
                            resultaConsultaLectura.getString(1),
                            resultaConsultaLectura.getString(2),
                            resultaConsultaLectura.getString(3),
                            resultaConsultaLectura.getString(4),
                            SimpleDateFormat("dd/MM/yyyy").parse(resultaConsultaLectura.getString(5))
                        ))
                }
            }while(resultaConsultaLectura.moveToNext())
        }else{
            Log.i("actual","no entro")
        }

        Log.i("bdd", "Se han consultado todos los Usuarios")
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloUsuario
    }

    fun eliminarUsuarioFormulario(id: Int):Boolean{
        val conexionEscritura = readableDatabase
        val resultadoEliminacion = conexionEscritura.delete("USUARIO","id=?",
            arrayOf(id.toString()))
        conexionEscritura.close()

        if(resultadoEliminacion.toInt()!=-1){
            Log.i("bdd",
                "Se ha ELIMINADO el Usuario con id: ${id}")
            return true
        }else{
            Log.i("bdd","Error: No se ha logrado ELIMINAR el Usuario con id: ${id}")
            return false
        }
        //return  if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarUsuarioFormulario(nombre:String, apellido:String, telefono: String, fechaNacimiento: Date, idActualizar:Int): Boolean {
        val formato = SimpleDateFormat("dd/MM/yyyy")
        val conexionEscritura = writableDatabase
        val valorAActualizar = ContentValues()

        valorAActualizar.put("nombre", nombre)
        valorAActualizar.put("apellido", apellido)
        valorAActualizar.put("telefono", telefono)
        valorAActualizar.put("fechaNacimiento", formato.format(fechaNacimiento))


        val resultadoActualizacion = conexionEscritura.update(
            "USUARIO",
            valorAActualizar,
            "id=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()

        if(resultadoActualizacion.toInt()!=-1){
            Log.i("bdd",
                "Se ha ACTUALIZADO el Usuario con id: ${idActualizar}")
            return true
        }else{
            Log.i("bdd","Error: No se ha logrado ACTUALIZADO el Usuario con id: ${idActualizar}")
            return false
        }


        //return if (resultadoActualizacion.toInt() == -1) false else true
    }


    //Casa==========================================================================================
    fun consultarCasaPorId(id:Int): Casa{
        //val formato = SimpleDateFormat("dd/MM/yyyy")
        val scriptConsultarCasa = "SELECT * FROM CASA WHERE ID = ${id}"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarCasa, null)
        val existeCasa = resultaConsultaLectura.moveToFirst()
        //val arregloCasa = arrayListOf<Casa>()
        val casaEncontrada = Casa(0,null,"","",0.0,0.0,0,0.0,false)
        if(existeCasa){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                val id_usuario = resultaConsultaLectura.getInt(1)
                val numcasa = resultaConsultaLectura.getString(2)
                val direccion = resultaConsultaLectura.getString(3)
                val terrenoArea = resultaConsultaLectura.getDouble(4)
                val construccionArea = resultaConsultaLectura.getDouble(5)
                val parqueaderos = resultaConsultaLectura.getInt(6)
                val avaluo = resultaConsultaLectura.getDouble(7)
                val bodega = resultaConsultaLectura.getString(8)


                if(id!=null){
                    casaEncontrada.id = id
                    casaEncontrada.id_usuario = id_usuario
                    casaEncontrada.numcasa = numcasa
                    casaEncontrada.direccion = direccion
                    casaEncontrada.terrenoArea = terrenoArea
                    casaEncontrada.construccionArea = construccionArea
                    casaEncontrada.parqueaderos = parqueaderos
                    casaEncontrada.avaluo = avaluo
                    casaEncontrada.bodega = bodega.toBoolean()
                    //arregloUsuario.add(usuarioEncontrado)
                }
            }while(resultaConsultaLectura.moveToNext())
        }
        Log.i("bdd","Se ha consultado la Casa por el id: ${id}")
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return casaEncontrada
    }


    fun consultarCasasPorIdUsuario(id_usuario:Int): ArrayList<Casa> {
        //val formato = SimpleDateFormat("dd/MM/yyyy")
        val scriptConsultarCasa = "SELECT * FROM CASA WHERE ID_USUARIO = ${id_usuario}"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarCasa, null)
        val existeCasa = resultaConsultaLectura.moveToFirst()
        val arregloCasa = arrayListOf<Casa>()
        //val casaEncontrada = Casa(0,null,"","",0.0,0.0,0,0.0,false)
        if(existeCasa){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                //val id_usuario = resultaConsultaLectura.getInt(1)
                val numcasa = resultaConsultaLectura.getString(2)
                val direccion = resultaConsultaLectura.getString(3)
                val terrenoArea = resultaConsultaLectura.getDouble(4)
                val construccionArea = resultaConsultaLectura.getDouble(5)
                val parqueaderos = resultaConsultaLectura.getInt(6)
                val avaluo = resultaConsultaLectura.getDouble(7)
                val bodega = resultaConsultaLectura.getInt(8).toBoolean()

                if(id!=null){
                    arregloCasa.add(
                        Casa(
                            id,
                            id_usuario,
                            numcasa,
                            direccion,
                            terrenoArea,
                            construccionArea,
                            parqueaderos,
                            avaluo,
                            bodega))
                }
            }while(resultaConsultaLectura.moveToNext())
        }

        Log.i("bdd","Se ha consultado la Casa por el id del Usuario: ${id_usuario}")
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloCasa
    }


    fun eliminarCasaFormularioPorIdUsuario(id_usuario: Int):Boolean{
        val conexionEscritura = readableDatabase
        val resultadoEliminacion = conexionEscritura.delete("CASA","ID_USUARIO=?",
            arrayOf(id_usuario.toString()))
        conexionEscritura.close()

        if(resultadoEliminacion.toInt()==1){
            Log.i("bdd",
                "Se ha ELIMINADO la Casa del Usuario con  id: ${id_usuario}")
            return true
        }else{
            Log.i("bdd","Error: No se ha logrado ELIMINAR la  Casa del Usuario con: ${id_usuario}")
            return false
        }
        //return  if (resultadoEliminacion.toInt() == -1) false else true
    }


    fun eliminarCasaFormularioPorId(id: Int):Boolean{
        val conexionEscritura = readableDatabase
        val resultadoEliminacion = conexionEscritura.delete("CASA","ID=?",
            arrayOf(id.toString()))
        conexionEscritura.close()

        if(resultadoEliminacion.toInt()==1){
            Log.i("bdd",
                "Se ha ELIMINADO la Casa con  id: ${id}")
            return true
        }else{
            Log.i("bdd","Error: No se ha logrado ELIMINAR la  Casa con: ${id}")
            return false
        }

    }

    fun actualizarCasaFormulario(
                                idActualizar:Int,
                                id_usuario:Int,
                                 numcasa: String,
                                 direccion: String,
                                 terrenoArea:Double,
                                 construccionArea:Double,
                                 parqueaderos:Int,
                                 avaluo:Double,
                                 bodega: Boolean
    ): Boolean {

        val conexionEscritura = writableDatabase
        val valorAActualizar = ContentValues()

        valorAActualizar.put("id_usuario",id_usuario)
        valorAActualizar.put("numcasa", numcasa)
        valorAActualizar.put("direccion", direccion)
        valorAActualizar.put("terrenoArea", terrenoArea)
        valorAActualizar.put("construccionArea", construccionArea)
        valorAActualizar.put("parqueaderos", parqueaderos)
        valorAActualizar.put("avaluo", avaluo)
        valorAActualizar.put("bodega", bodega)


        val resultadoActualizacion = conexionEscritura.update(
            "CASA",
            valorAActualizar,
            "id=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()

        if (resultadoActualizacion.toInt() == 1) {
            Log.i(
                "bdd",
                "Se ha ACTUALIZADO la Casa con id: ${idActualizar}"
            )
            return true
        } else {
            Log.i("bdd", "Error: No se ha logrado ACTUALIZADO la Casa con id: ${idActualizar}")
            return false
        }
    }

    private fun Int.toBoolean():Boolean{
        return this==1
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}
