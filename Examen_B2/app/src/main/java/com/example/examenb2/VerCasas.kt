package com.example.examenb2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.examenb2.dto.CasaDto
import com.example.examenb2.dto.UsuarioDto
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class VerCasas : AppCompatActivity() {
    var adapter: ArrayAdapter<CasaDto>? = null
    var arrayCasas =  arrayListOf<CasaDto>()
    var posicionItemSelecionado = 0
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var usuario: UsuarioDto?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_casas)

        usuario= intent.getParcelableExtra<UsuarioDto>("usuario")
        Log.i("usuario","Usuario: ${usuario!!.cedula}")

        consultarCasa()

        val botonCrearCasa = findViewById<Button>(R.id.btn_irAActividadCrearCasa)
        botonCrearCasa
            .setOnClickListener {
                abrirActividadConParametros(CrearCasa::class.java,usuario!!)
            }

    }

    fun consultarCasa(){

        val db = Firebase.firestore
        var refUsuario = db
            .collection("casa")
        refUsuario
            .whereEqualTo("usuario_uid",usuario!!.uid)
            .get()
            .addOnSuccessListener {
                //Log.i("consultas","${it.documents}")
                for (documentos in it){
                    val ubicacionStore: HashMap<String, *> = documentos.data["ubicacion"] as HashMap<String, *>
                    //Log.i("consultarCasa","${ubicacionStore["latitude"]}")

                    arrayCasas.add(
                        CasaDto(
                            documentos.id,
                            documentos.data["usuario_uid"].toString(),
                            documentos.data["num_casa"].toString(),
                            LatLng(ubicacionStore["latitude"].toString().toDouble(),ubicacionStore["longitude"].toString().toDouble()),
                            documentos.data["area_terreno"].toString().toDouble(),
                            documentos.data["area_construccion"].toString().toDouble(),
                            documentos.data["parqueaderos"].toString().toInt(),
                            documentos.data["avaluo"].toString().toDouble(),
                            documentos.data["bodega"] as Boolean,
                        )
                    )

                    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCasas)
                    val listViewUsuario = findViewById<ListView>(R.id.ltv_casas)
                    listViewUsuario.adapter = adapter

                    registerForContextMenu(listViewUsuario)

                }

            }
            .addOnFailureListener{}

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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_casas,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
        //Log.i("list-view-casa","onCreate ${posicionItemSelecionado}")
        //Log.i("list-view-casa","Casa id: ${BaseDeDatos.Tablas!!.consultarCasasPorIdUsuario(usuarioid)[posicionItemSelecionado].id}")

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        var CasaSelect = arrayCasas[posicionItemSelecionado]

        return when(item?.itemId){

            //Editar
            R.id.mi_editarCasa -> {
                Log.i("list-view-casa","Editar Casa id: ${CasaSelect}")
                abrirActividadConParametros3(EditarCasa::class.java,CasaSelect,usuario!!)
                return true
            }

            //Eliminar
            R.id.mi_eliminarCasa -> {
                Log.i("list-view-casa","Eliminar Casa id: ${CasaSelect}")

                val db = Firebase.firestore
                var refUsuario = db
                    .collection("casa")

                refUsuario.document(CasaSelect.uid.toString())
                    .delete()
                    .addOnSuccessListener {
                        adapter?.remove(adapter!!.getItem(posicionItemSelecionado));
                        adapter?.notifyDataSetChanged()
                        Log.d("list-view", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("list-view", "Error deleting document", e) }

                return true }

            R.id.mi_UbicacionCasa->{
                abrirActividadConParametros2(VerUbicacion::class.java,CasaSelect)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadConParametros2(
        clase: Class<*>,
        casa: CasaDto,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        //intentExplicito.putExtra("usuario",usuario)

        intentExplicito.putExtra("casa",casa)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }

    fun abrirActividadConParametros3(
        clase: Class<*>,
        casa: CasaDto,
        usuario: UsuarioDto,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        //intentExplicito.putExtra("usuario",usuario)

        intentExplicito.putExtra("casa",casa)
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }
}