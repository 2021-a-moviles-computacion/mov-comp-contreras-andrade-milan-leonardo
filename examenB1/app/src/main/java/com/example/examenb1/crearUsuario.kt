package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class crearUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)


        BaseDeDatos.Tablas= SQLiteHelper(this)

        val ingresarCedulaUsuario = findViewById<EditText>(R.id.ti_cedula)
        val ingresarNombreUsuario = findViewById<EditText>(R.id.ti_nombre)
        val ingresarApellidoUsuario = findViewById<EditText>(R.id.ti_apellido)
        val ingresarTelefonoUsuario = findViewById<EditText>(R.id.ti_telefono)
        val ingresarFechaNacimientoUsuario = findViewById<EditText>(R.id.ti_fechaNacimiento)

        val botonCrearUsuario = findViewById<Button>(R.id.btn_crearUsuario)
        botonCrearUsuario
            .setOnClickListener {

                if(BaseDeDatos.Tablas!=null){
                    BaseDeDatos.Tablas!!.crearUsuarioFormulario(
                        ingresarCedulaUsuario.text.toString(),
                        ingresarNombreUsuario.text.toString(),
                        ingresarApellidoUsuario.text.toString(),
                        ingresarTelefonoUsuario.text.toString(),
                        ingresarFechaNacimientoUsuario.text.toString()
                    )

                    Log.i("Crear-Usuario","Creando el usuario:" +
                            "cedula: ${ingresarCedulaUsuario.text.toString()} \n" +
                            "Nombre: ${ingresarNombreUsuario.text.toString()} \n" +
                            "Apellido: ${ingresarApellidoUsuario.text.toString()} \n" +
                            "Telefono: ${ingresarTelefonoUsuario.text.toString()} \n" +
                            "Fecha Nacimeinto: ${ingresarFechaNacimientoUsuario.text.toString()} \n")
                }


                abrirActividad(MainActivity::class.java)

            }



    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }






}