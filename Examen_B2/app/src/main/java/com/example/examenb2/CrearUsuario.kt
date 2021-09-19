package com.example.examenb2

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.examenb2.dto.UsuarioDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

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

                crearUsuario()

            }


    }

    fun crearUsuario(){
        val ingresarCedulaUsuario = findViewById<EditText>(R.id.ti_cedula)
        val ingresarNombreUsuario = findViewById<EditText>(R.id.ti_nombre)
        val ingresarApellidoUsuario = findViewById<EditText>(R.id.ti_apellido)
        val ingresarTelefonoUsuario = findViewById<EditText>(R.id.ti_telefono)
        val ingresarFechaNacimientoUsuario = findViewById<EditText>(R.id.ti_fechaNacimiento)

        var objetoUsuarioDto = UsuarioDto(
            null,
            ingresarCedulaUsuario.text.toString(),
            ingresarNombreUsuario.text.toString(),
            ingresarApellidoUsuario.text.toString(),
            ingresarTelefonoUsuario.text.toString(),
            SimpleDateFormat("dd/MM/yyyy").parse(ingresarFechaNacimientoUsuario.text.toString())

        )
        val nuevoUsuario = hashMapOf<String,Any>(
            "cedula" to objetoUsuarioDto.cedula!!,
            "nombre" to objetoUsuarioDto.nombre!!,
            "apellido" to objetoUsuarioDto.apellido!!,
            "telefono" to objetoUsuarioDto.telefono!!,
            "fechaNacimiento" to objetoUsuarioDto.fechaNacimiento
        )
        val db = Firebase.firestore
        val referencia = db.collection("usuario")
        referencia
            .add(nuevoUsuario)
            .addOnSuccessListener {
                //editTextNombre.text.clear()
                abrirActividad(MainActivity::class.java)
            }
            .addOnFailureListener{}
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