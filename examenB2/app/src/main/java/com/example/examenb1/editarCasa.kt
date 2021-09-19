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

class editarCasa : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_casa)

        val casa = intent.getParcelableExtra<Casa>("casa")

        Log.i("help","casa: ${casa}")

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_editar_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_editar_direccionCasa)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_editar_areaTerrenoCasa)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_editar_areaConstruccionCasa)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_editar_parqueaderosCasa)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_editar_avaluoCasa)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_editar_bodebaCasa)


        ingresarNumCasa.setText(casa!!.numcasa)
        ingresarDireccionCasa.setText(casa!!.direccion)
        ingresarAreaTerreno.setText(casa!!.terrenoArea.toString())
        ingresarAreaConstruccion.setText(casa!!.construccionArea.toString())
        ingresarParqueaderos.setText(casa!!.parqueaderos.toString())
        ingresarAvaluo.setText(casa!!.avaluo.toString())
        ingresarBobeda.isChecked = casa.bodega


        ingresarNumCasa.addTextChangedListener(crearusuarioTextWatcher)
        ingresarDireccionCasa.addTextChangedListener(crearusuarioTextWatcher)
        ingresarAreaTerreno.addTextChangedListener(crearusuarioTextWatcher)
        ingresarAreaConstruccion.addTextChangedListener(crearusuarioTextWatcher)
        ingresarParqueaderos.addTextChangedListener(crearusuarioTextWatcher)
        ingresarAvaluo.addTextChangedListener(crearusuarioTextWatcher)


        val botoneditarCasa=findViewById<Button>(R.id.btn_editarCasa)
        //botoneditarCasa.isEnabled=false
        botoneditarCasa
            .setOnClickListener {

                if (BaseDeDatos.Tablas != null) {
                    BaseDeDatos.Tablas!!.actualizarCasaFormulario(
                        casa!!.id,
                        casa.id_usuario!!,
                        ingresarNumCasa.text.toString(),
                        ingresarDireccionCasa.text.toString(),
                        ingresarAreaTerreno.text.toString().toDouble(),
                        ingresarAreaConstruccion.text.toString().toDouble(),
                        ingresarParqueaderos.text.toString().toInt(),
                        ingresarAvaluo.text.toString().toDouble(),
                        ingresarBobeda.isChecked
                    )
                }



                Log.i("help","idUsuario: ${casa!!.id_usuario!!}")
                if (BaseDeDatos.Tablas != null) {
                    val usuario = BaseDeDatos.Tablas!!.consultarUsuarioPorId(casa!!.id_usuario!!)
                    abrirActividadConParametros(casasDeUsuario::class.java,usuario!!)
                }



                Log.i("Editar-casa","Etitando la Casa:" +
                        "id Casa: ${casa!!.id}"+
                        "usuario: ${casa.id_usuario!!} \n" +
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
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            val ingresarNumCasa = findViewById<EditText>(R.id.ti_editar_numCasa).text.toString().trim()
            val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_editar_direccionCasa).text.toString().trim()
            val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_editar_areaTerrenoCasa).text.toString().trim()
            val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_editar_areaConstruccionCasa).text.toString().trim()
            val ingresarParqueaderos = findViewById<EditText>(R.id.ti_editar_parqueaderosCasa).text.toString().trim()
            val ingresarAvaluo = findViewById<EditText>(R.id.ti_editar_avaluoCasa).text.toString().trim()

            val botonCrearCasa = findViewById<Button>(R.id.btn_editarCasa)
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