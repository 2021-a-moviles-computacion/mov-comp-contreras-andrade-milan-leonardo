package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat

class actualizarUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_usuario)

        BaseDeDatos.Tablas= SQLiteHelper(this)

        val usuario= intent.getParcelableExtra<Usuario>("usuario")

        val ingresarCedulaUsuario = findViewById<EditText>(R.id.ti_actualizar_cedula)
        val ingresarNombreUsuario = findViewById<EditText>(R.id.ti_actualizar_nombre)
        val ingresarApellidoUsuario = findViewById<EditText>(R.id.ti_actualizar_apellido)
        val ingresarTelefonoUsuario = findViewById<EditText>(R.id.ti_actualizar_telefono)
        val ingresarFechaNacimientoUsuario = findViewById<EditText>(R.id.ti_actualizar_fechaNacimiento)

        ingresarCedulaUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarNombreUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarApellidoUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarTelefonoUsuario.addTextChangedListener(crearusuarioTextWatcher)
        ingresarFechaNacimientoUsuario.addTextChangedListener(crearusuarioTextWatcher)

        ingresarCedulaUsuario.setText(usuario?.cedula)
        ingresarNombreUsuario.setText(usuario?.nombre)
        ingresarApellidoUsuario.setText(usuario?.apellido)
        ingresarTelefonoUsuario.setText(usuario?.telefono)
        ingresarFechaNacimientoUsuario.setText(SimpleDateFormat("dd/MM/yyyy").format(usuario?.fechaNacimiento))

        val botonactualizarUsuario = findViewById<Button>(R.id.btn_actualizar_Usuario)
        botonactualizarUsuario.isEnabled=false
        botonactualizarUsuario
            .setOnClickListener {
                if(usuario!=null){

                    val resultado = BaseDeDatos.Tablas!!.actualizarUsuarioFormulario(
                        ingresarNombreUsuario.text.toString(),
                        ingresarApellidoUsuario.text.toString(),
                        ingresarTelefonoUsuario.text.toString(),
                        SimpleDateFormat("dd/MM/yyyy").parse(ingresarFechaNacimientoUsuario.text.toString()),
                        usuario.id)

                    Log.i("Crear-Usuario","Actualizando el usuario:" +
                            "cedula: ${ingresarCedulaUsuario.text} \n" +
                            "Nombre: ${ingresarNombreUsuario.text} \n" +
                            "Apellido: ${ingresarApellidoUsuario.text} \n" +
                            "Telefono: ${ingresarTelefonoUsuario.text} \n" +
                            "Fecha Nacimeinto: ${ingresarFechaNacimientoUsuario.text} \n")
                    if(resultado){
                        abrirActividad(MainActivity::class.java)
                    }

                }
            }
    }


    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }

    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val ingresarCedulaUsuario = findViewById<EditText>(R.id.ti_actualizar_cedula).text.toString().trim()
            val ingresarNombreUsuario = findViewById<EditText>(R.id.ti_actualizar_nombre).text.toString().trim()
            val ingresarApellidoUsuario = findViewById<EditText>(R.id.ti_actualizar_apellido).text.toString().trim()
            val ingresarTelefonoUsuario = findViewById<EditText>(R.id.ti_actualizar_telefono).text.toString().trim()
            val ingresarFechaNacimientoUsuario = findViewById<EditText>(R.id.ti_actualizar_fechaNacimiento).text.toString().trim()

            val botonactualizarUsuario = findViewById<Button>(R.id.btn_actualizar_Usuario)
            val regexFechaNacimientoUsuario = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])\\/(19|20)\\d\\d")

            botonactualizarUsuario.isEnabled = (ingresarCedulaUsuario.isNotEmpty() &&
                    ingresarNombreUsuario.isNotEmpty() &&
                    ingresarApellidoUsuario.isNotEmpty() &&
                    ingresarTelefonoUsuario.isNotEmpty() &&
                    ingresarFechaNacimientoUsuario.isNotEmpty() &&
                   regexFechaNacimientoUsuario.matches(ingresarFechaNacimientoUsuario))
        }

        override fun afterTextChanged(s: Editable) {}
    }
}