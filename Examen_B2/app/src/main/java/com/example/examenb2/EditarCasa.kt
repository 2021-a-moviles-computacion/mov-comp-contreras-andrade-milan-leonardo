package com.example.examenb2

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.examenb2.dto.CasaDto
import com.example.examenb2.dto.UsuarioDto
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarCasa : AppCompatActivity() {
    var casa: CasaDto?=null
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var ubicacionMapa:LatLng? = null
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_casa)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_EditarCasaMapa1) as SupportMapFragment
        fragmentMapa?.view?.visibility = View.INVISIBLE

        solicitarPermisos()

        casa = intent.getParcelableExtra<CasaDto>("casa")

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_editar_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_editar_direccionCasa)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_editar_areaTerrenoCasa)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_editar_areaConstruccionCasa)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_editar_parqueaderosCasa)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_editar_avaluoCasa)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_editar_bodebaCasa)

        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_actualizar)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_actualizar)


        ingresarNumCasa.setText(casa!!.numcasa)
        ingresarDireccionCasa.setText("${casa!!.ubicacion!!.latitude.toString()} ; ${casa!!.ubicacion!!.longitude.toString()}")
        ingresarAreaTerreno.setText(casa!!.terrenoArea.toString())
        ingresarAreaConstruccion.setText(casa!!.construccionArea.toString())
        ingresarParqueaderos.setText(casa!!.parqueaderos.toString())
        ingresarAvaluo.setText(casa!!.avaluo.toString())
        ingresarBobeda.isChecked = casa!!.bodega
        //Log.i("editarCasa","latitud: ${casa!!.ubicacion!!.latitude.toString()}")
        ingresarLatitud.setText(casa!!.ubicacion!!.latitude.toString())
        ingresarLongitud.setText(casa!!.ubicacion!!.longitude.toString())


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
                actualizarCasa()
            }

        val botonActualizarUbicacion = findViewById<Button>(R.id.btn_ir_actualizarDireccionMapa)
        botonActualizarUbicacion
            .setOnClickListener {
                mapa()
            }
    }

    fun mapa(){

        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_editar_direccionCasa)


        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_actualizar)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_actualizar)

        visibilidad(true)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_EditarCasaMapa1) as SupportMapFragment
        fragmentMapa.getMapAsync{ googleMap ->
            if(googleMap!=null){
                mapa = googleMap
                establecerConfiguracionMapa()

                val ubcacionCasa = casa!!.ubicacion
                val titulo = "Ubicacion"
                val zoom = 17f

                anadirMarcador(ubcacionCasa!!, titulo)
                moverCamaraZoom(ubcacionCasa!!, zoom)
            }
        }

        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapaActualizar)
        botonBuscarEnMapa
            .setOnClickListener {
                //mapa.clear()
                val buscar = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())
                val zoom = 17f
                moverCamaraZoom(buscar,zoom)
                anadirMarcador(buscar,"Direccion")


            }


        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarActualizarUbicacion)
        botonGuardarUbicacion
            .setOnClickListener {
                visibilidad(false)
                ingresarDireccionCasa.setText("${ingresarLatitud.text.toString()} ; ${ingresarLongitud.text.toString()}")
                ubicacionMapa = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())

            }
    }

    fun visibilidad(mapa:Boolean){

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_editar_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_editar_direccionCasa)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_editar_areaTerrenoCasa)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_editar_areaConstruccionCasa)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_editar_parqueaderosCasa)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_editar_avaluoCasa)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_editar_bodebaCasa)

        val botonCrearUbicacion = findViewById<Button>(R.id.btn_ir_actualizarDireccionMapa)
        val botonCrearCasa = findViewById<Button>(R.id.btn_editarCasa)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_EditarCasaMapa1) as SupportMapFragment
        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarActualizarUbicacion)

        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_actualizar)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_actualizar)
        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapaActualizar)
        val textViewBodega = findViewById<TextView>(R.id.cb_editar_bodebaCasa)

        val tv11 = findViewById<TextView>(R.id.textView11)
        val tv12 = findViewById<TextView>(R.id.textView12)
        val tv13 = findViewById<TextView>(R.id.textView13)
        val tv14 = findViewById<TextView>(R.id.textView14)
        val tv15 = findViewById<TextView>(R.id.textView15)
        val tv16 = findViewById<TextView>(R.id.textView16)
        val tv17 = findViewById<TextView>(R.id.textView17)
        //fragmentMapa?.view?.visibility = View.GONE


        if(mapa){
            ingresarNumCasa.isVisible = false
            ingresarDireccionCasa.isVisible = false
            ingresarAreaTerreno.isVisible = false
            ingresarAreaConstruccion.isVisible = false
            ingresarParqueaderos.isVisible = false
            ingresarAvaluo.isVisible = false
            ingresarBobeda.isVisible = false
            botonCrearUbicacion.isVisible = false
            botonCrearCasa.isVisible = false
            textViewBodega.isVisible = false
            tv11.isVisible = false
            tv12.isVisible = false
            tv13.isVisible = false
            tv14.isVisible = false
            tv15.isVisible = false
            tv16.isVisible = false
            tv17.isVisible = false


            fragmentMapa?.view?.visibility = View.VISIBLE
            botonGuardarUbicacion.isVisible = true
            ingresarLatitud.isVisible = true
            ingresarLongitud.isVisible = true
            botonBuscarEnMapa.isVisible = true
        }else{
            ingresarNumCasa.isVisible = true
            ingresarDireccionCasa.isVisible = true
            ingresarAreaTerreno.isVisible = true
            ingresarAreaConstruccion.isVisible = true
            ingresarParqueaderos.isVisible = true
            ingresarAvaluo.isVisible = true
            ingresarBobeda.isVisible = true
            botonCrearUbicacion.isVisible = true
            botonCrearCasa.isVisible = true
            textViewBodega.isVisible = true

            tv11.isVisible = true
            tv12.isVisible = true
            tv13.isVisible = true
            tv14.isVisible = true
            tv15.isVisible = true
            tv16.isVisible = true
            tv17.isVisible = true

            fragmentMapa?.view?.visibility = View.INVISIBLE
            botonGuardarUbicacion.isVisible = false
            ingresarLatitud.isVisible = false
            ingresarLongitud.isVisible = false
            botonBuscarEnMapa.isVisible = false

        }


    }

    fun actualizarCasa(){

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_editar_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_editar_direccionCasa)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_editar_areaTerrenoCasa)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_editar_areaConstruccionCasa)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_editar_parqueaderosCasa)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_editar_avaluoCasa)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_editar_bodebaCasa)



        var objetCasaDto = CasaDto(
            casa!!.uid.toString(),
            casa!!.uid_usuario.toString(),
            ingresarNumCasa.text.toString(),
            ubicacionMapa,
            ingresarAreaTerreno.text.toString().toDouble(),
            ingresarAreaConstruccion.text.toString().toDouble(),
            ingresarParqueaderos.text.toString().toInt(),
            ingresarAvaluo.text.toString().toDouble(),
            ingresarBobeda.isChecked
        )

        val nuevaCasa = hashMapOf<String,Any>(
            "num_casa" to objetCasaDto.numcasa!!,
            "ubicacion" to objetCasaDto.ubicacion!!,
            "area_terreno" to objetCasaDto.terrenoArea!!,
            "area_construccion" to objetCasaDto.construccionArea!!,
            "parqueaderos" to objetCasaDto.parqueaderos!!,
            "avaluo" to objetCasaDto.avaluo,
            "bodega" to objetCasaDto.bodega,
        )

        val db = Firebase.firestore
        val referencia = db.collection("casa")
            .document(casa?.uid!!)

        db.runTransaction {  transaction ->
            //val documentoActual = transaction.get(referencia)
            transaction.update(referencia, nuevaCasa)
        }
            .addOnSuccessListener {
                val usuario1 = intent.getParcelableExtra<UsuarioDto>("usuario")!!
                abrirActividadConParametros(VerCasas::class.java,usuario1)
                Log.i("transaccion", "Transaccion Completa")
            }
            .addOnFailureListener{
                Log.i("transaccion", "ERROR")
            }




    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: UsuarioDto,
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

    fun anadirMarcador(latLng: LatLng, title: String){
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
            //.draggable(true)
        )
    }

    fun moverCamaraZoom(latLng: LatLng, zoom: Float ){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )

    }

    fun solicitarPermisos(){

        val contexto = this. applicationContext
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this. applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )

            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true //no tenemos aun permisos
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true //no tenemos aun permisos
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
}