package com.example.examenb1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class crearUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)


        BaseDeDatos.Tablas= SQLiteHelper(this)
/*
        val regexCedulaUsuario = Regex("^[0-9]{10}")
        val regexNombreUsuario = Regex("^[A-za-z]+")
        val regexApellidoUsuario = Regex("^[A-za-z]+")
        val regexTelefonoUsuario = Regex("^[0-9]+")
        val regexFechaNacimientoUsuario = Regex("^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/(19|20)\\d\\d")
*/

        val ingresarCedulaUsuario = findViewById<EditText>(R.id.ti_cedula)
        val ingresarNombreUsuario = findViewById<EditText>(R.id.ti_nombre)
        val ingresarApellidoUsuario = findViewById<EditText>(R.id.ti_apellido)
        val ingresarTelefonoUsuario = findViewById<EditText>(R.id.ti_telefono)
        val ingresarFechaNacimientoUsuario = findViewById<EditText>(R.id.ti_fechaNacimiento)

        ingresarCedulaUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarNombreUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarApellidoUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarTelefonoUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarFechaNacimientoUsuario.addTextChangedListener(crearusuarioTextWatcher)

        val botonCrearUsuario = findViewById<Button>(R.id.btn_crearUsuario)
        botonCrearUsuario.isEnabled=false
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
                                "cedula: ${ingresarCedulaUsuario.text} \n" +
                                "Nombre: ${ingresarNombreUsuario.text} \n" +
                                "Apellido: ${ingresarApellidoUsuario.text} \n" +
                                "Telefono: ${ingresarTelefonoUsuario.text} \n" +
                                "Fecha Nacimeinto: ${ingresarFechaNacimientoUsuario.text} \n")
                    }

                    abrirActividad(MainActivity::class.java)

            }

    }


    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val ingresarCedulaUsuario = findViewById<EditText>(R.id.ti_cedula).text.toString().trim()
            val ingresarNombreUsuario = findViewById<EditText>(R.id.ti_nombre).text.toString().trim()
            val ingresarApellidoUsuario = findViewById<EditText>(R.id.ti_apellido).text.toString().trim()
            val ingresarTelefonoUsuario = findViewById<EditText>(R.id.ti_telefono).text.toString().trim()
            val ingresarFechaNacimientoUsuario = findViewById<EditText>(R.id.ti_fechaNacimiento).text.toString().trim()

            val botonCrearUsuario = findViewById<Button>(R.id.btn_crearUsuario)
            val regexFechaNacimientoUsuario = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])\\/(19|20)\\d\\d")
            botonCrearUsuario.isEnabled = (ingresarCedulaUsuario.isNotEmpty() &&
                    ingresarNombreUsuario.isNotEmpty() &&
                    ingresarApellidoUsuario.isNotEmpty() &&
                    ingresarTelefonoUsuario.isNotEmpty() &&
                    ingresarFechaNacimientoUsuario.isNotEmpty() &&
                    regexFechaNacimientoUsuario.matches(ingresarFechaNacimientoUsuario))
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }






}