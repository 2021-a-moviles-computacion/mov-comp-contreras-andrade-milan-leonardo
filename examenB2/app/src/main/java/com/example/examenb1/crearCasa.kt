package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class crearCasa : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_casa)

        BaseDeDatos.Tablas = SQLiteHelper(this)

        val usuario = intent.getParcelableExtra<Usuario>("usuario")

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_direccion)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_areaTerreno)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_areaConstruccion)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_parqueaderos)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_avaluo)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_bodega)



        ingresarNumCasa.addTextChangedListener(crearusuarioTextWatcher)
        ingresarDireccionCasa.addTextChangedListener(crearusuarioTextWatcher)
        ingresarAreaTerreno.addTextChangedListener(crearusuarioTextWatcher)
        ingresarAreaConstruccion.addTextChangedListener(crearusuarioTextWatcher)
        ingresarParqueaderos.addTextChangedListener(crearusuarioTextWatcher)
        ingresarAvaluo.addTextChangedListener(crearusuarioTextWatcher)



        val botonCrearCasa = findViewById<Button>(R.id.btn_CrearCasa)
        botonCrearCasa.isEnabled = false

        botonCrearCasa
            .setOnClickListener {

                if (BaseDeDatos.Tablas != null) {
                    BaseDeDatos.Tablas!!.crearCasaFormulario(
                        usuario!!.id,
                        ingresarNumCasa.text.toString(),
                        ingresarDireccionCasa.text.toString(),
                        ingresarAreaTerreno.text.toString().toDouble(),
                        ingresarAreaConstruccion.text.toString().toDouble(),
                        ingresarParqueaderos.text.toString().toInt(),
                        ingresarAvaluo.text.toString().toDouble(),
                        ingresarBobeda.isChecked.toInt()
                    )
                }



                Log.i("boton","help ${usuario}")
                abrirActividadConParametros(casasDeUsuario::class.java,usuario!!)


                Log.i("Crear-casa","Creando la Casa:" +
                        "usuario: ${usuario!!.id} \n" +
                        "NumCasa: ${ingresarNumCasa.text} \n" +
                        "Direccion: ${ingresarDireccionCasa.text} \n" +
                        "Terreno: ${ingresarAreaTerreno.text} \n" +
                        "Construccion: ${ingresarAreaConstruccion.text} \n" +
                        "Parqueaderos: ${ingresarParqueaderos.text} \n" +
                        "Avaluo: ${ingresarAvaluo.text} \n" +
                        "Bobeda: ${ingresarBobeda.isChecked.toInt()} \n")
            }

            }



    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            val ingresarNumCasa = findViewById<EditText>(R.id.ti_numCasa).text.toString().trim()
            val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_direccion).text.toString().trim()
            val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_areaTerreno).text.toString().trim()
            val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_areaConstruccion).text.toString().trim()
            val ingresarParqueaderos = findViewById<EditText>(R.id.ti_parqueaderos).text.toString().trim()
            val ingresarAvaluo = findViewById<EditText>(R.id.ti_avaluo).text.toString().trim()
            //val ingresarBobeda = findViewById<CheckBox>(R.id.cb_bodega)


            val botonCrearCasa = findViewById<Button>(R.id.btn_CrearCasa)
            botonCrearCasa.isEnabled = (
                    ingresarNumCasa.isNotEmpty() &&
                    ingresarDireccionCasa.isNotEmpty() &&
                    ingresarAreaTerreno.isNotEmpty() &&
                    ingresarAreaConstruccion.isNotEmpty() &&
                    ingresarParqueaderos.isNotEmpty() &&
                    ingresarAvaluo.isNotEmpty())
        }

        override fun afterTextChanged(s: Editable) {}
    }


    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: Usuario,
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }



    }

private fun Boolean.toInt(): Int {
    return if(this){
        1
    }else{
        0
    }
}







