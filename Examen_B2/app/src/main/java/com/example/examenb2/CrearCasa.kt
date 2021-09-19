package com.example.examenb2

import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.examenb2.dto.CasaDto
import com.example.examenb2.dto.UsuarioDto
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearCasa : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var usuarioIntent: UsuarioDto?=null
    var ubicacionMapa:LatLng? = null
    private lateinit var mapa: GoogleMap
    var permisos = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_casa)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_CrearCasaMapa) as SupportMapFragment
        fragmentMapa?.view?.visibility = View.INVISIBLE
        solicitarPermisos()

        usuarioIntent = intent.getParcelableExtra<UsuarioDto>("usuario")
        //Log.i("usuario","Usuario Crear casa : ${usuarioIntent!!.cedula}")

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
                crearCasa()
            }

        val botonCrearUbicacion = findViewById<Button>(R.id.btn_ir_crearDireccionMapa)
        botonCrearUbicacion
            .setOnClickListener {
                mapa()
            }





    }
    fun mapa(){
        val ingresarNumCasa = findViewById<EditText>(R.id.ti_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_direccion)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_areaTerreno)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_areaConstruccion)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_parqueaderos)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_avaluo)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_bodega)

        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_crear)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_crear)

        visibilidad(true)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_CrearCasaMapa) as SupportMapFragment
        fragmentMapa.getMapAsync{ googleMap ->
            if(googleMap!=null){
                mapa = googleMap
                establecerConfiguracionMapa()
                val quicentro = LatLng(-0.176125, -78.480208)
                val titulo = "Quicentro"
                val zoom = 10f
                //anadirMarcador(quicentro,titulo)
                moverCamaraZoom(quicentro, zoom)
                escucharListeners()
            }
        }

        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapa)
        botonBuscarEnMapa
            .setOnClickListener {
                mapa.clear()
                val buscar = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())
                val zoom = 17f
                moverCamaraZoom(buscar,zoom)
                anadirMarcador(buscar,"Direccion")


            }


        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarUbicacion)
        botonGuardarUbicacion
            .setOnClickListener {
                visibilidad(false)
                ingresarDireccionCasa.setText("${ingresarLatitud.text.toString()} ; ${ingresarLongitud.text.toString()}")
                ubicacionMapa = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())

            }
    }

    fun visibilidad(mapa:Boolean){

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_direccion)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_areaTerreno)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_areaConstruccion)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_parqueaderos)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_avaluo)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_bodega)
        val botonCrearUbicacion = findViewById<Button>(R.id.btn_ir_crearDireccionMapa)
        val botonCrearCasa = findViewById<Button>(R.id.btn_CrearCasa)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_CrearCasaMapa) as SupportMapFragment
        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarUbicacion)
        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_crear)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_crear)
        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapa)
        val textViewBodega = findViewById<TextView>(R.id.tv_tieneBodega)

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

            fragmentMapa?.view?.visibility = View.INVISIBLE
            botonGuardarUbicacion.isVisible = false
            ingresarLatitud.isVisible = false
            ingresarLongitud.isVisible = false
            botonBuscarEnMapa.isVisible = false

        }


    }

    fun escucharListeners(){

        mapa.setOnMarkerClickListener {
            //Log.i("mapa" ,"setOnMarkerClickListener ${it}")
            val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_crear)
            val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_crear)
            ingresarLatitud.setText(it.position.latitude.toString())
            ingresarLongitud.setText(it.position.longitude.toString())
            Log.i("mapa" ,"latitud:  ${it.position.latitude}")
            Log.i("mapa" ,"logitud:  ${it.position.longitude}")

            return@setOnMarkerClickListener true
        }

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

    fun crearCasa(){

        val ingresarNumCasa = findViewById<EditText>(R.id.ti_numCasa)
        val ingresarDireccionCasa = findViewById<EditText>(R.id.ti_direccion)
        val ingresarAreaTerreno = findViewById<EditText>(R.id.ti_areaTerreno)
        val ingresarAreaConstruccion = findViewById<EditText>(R.id.ti_areaConstruccion)
        val ingresarParqueaderos = findViewById<EditText>(R.id.ti_parqueaderos)
        val ingresarAvaluo = findViewById<EditText>(R.id.ti_avaluo)
        val ingresarBobeda = findViewById<CheckBox>(R.id.cb_bodega)



        var objetCasaDto = CasaDto(
            null,
            usuarioIntent!!.uid.toString(),
            ingresarNumCasa.text.toString(),
            ubicacionMapa,
            ingresarAreaTerreno.text.toString().toDouble(),
            ingresarAreaConstruccion.text.toString().toDouble(),
            ingresarParqueaderos.text.toString().toInt(),
            ingresarAvaluo.text.toString().toDouble(),
            ingresarBobeda.isChecked
        )

        val nuevaCasa = hashMapOf<String,Any>(
            "usuario_uid" to objetCasaDto.uid_usuario!!,
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
        referencia
            .add(nuevaCasa)
            .addOnSuccessListener {
                //editTextNombre.text.clear()
                abrirActividadConParametros(VerCasas::class.java,usuarioIntent!!)
            }
            .addOnFailureListener{}


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
        usuario: UsuarioDto,
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