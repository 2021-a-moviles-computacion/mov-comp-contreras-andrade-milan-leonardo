package com.example.examenb2

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.examenb2.dto.CasaDto
import com.example.examenb2.dto.UsuarioDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    var adapter: ArrayAdapter<UsuarioDto>? = null
    var arrayUsuarios =  arrayListOf<UsuarioDto>()
    var posicionItemSelecionado = 0
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //quemarDatos()

        val botonIrActividadcrearUsuarioUsuario = findViewById<Button>(R.id.btn_ir_crear_usuario)
        botonIrActividadcrearUsuarioUsuario
            .setOnClickListener {
                abrirActividad(CrearUsuario::class.java)
            }

        consultarUsuario()
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: UsuarioDto,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }

    fun consultarUsuario(){

        val db = Firebase.firestore
        var refUsuario = db
            .collection("usuario")
        refUsuario
            .get()
            .addOnSuccessListener {
                //Log.i("consultas","${it.documents}")
                for (documentos in it){
                    Log.i("consultas","${documentos.data["nombre"]}")
                    val timestamp = documentos.data["fechaNacimiento"] as com.google.firebase.Timestamp
                    val fechaNacimiento = timestamp.toDate()
                    arrayUsuarios.add(
                        UsuarioDto(
                            documentos.id,
                            documentos.data["cedula"].toString(),
                            documentos.data["nombre"].toString(),
                            documentos.data["apellido"].toString(),
                            documentos.data["telefono"].toString(),
                            fechaNacimiento,
                        )
                    )

                    val date: Date = timestamp.toDate()
                    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayUsuarios)
                    val listViewUsuario = findViewById<ListView>(R.id.ltv_usuarios)
                    listViewUsuario.adapter = adapter

                    registerForContextMenu(listViewUsuario)
            }

            }
            .addOnFailureListener{}

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_usuario,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
        //Log.i("list-view","onCreate ${posicionItemSelecionado}")
        //Log.i("list-view","Usuario ${arrayUsuarios[posicionItemSelecionado].toString()}")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        var UsuarioSelect = arrayUsuarios[posicionItemSelecionado]
        return when(item?.itemId){

            //Editar
            R.id.mi_editar -> {
                Log.i("list-view","Editar ${UsuarioSelect}")
                //Log.i("list-view2","Editar")
                abrirActividadConParametros(EditarUsuario::class.java,UsuarioSelect)

                return true
            }

            //Eliminar
            R.id.mi_eliminar -> {
                Log.i("list-view","Eliminar ${UsuarioSelect}")
                val arrayCasasUIDEliminar = arrayListOf<String>()
                val db = Firebase.firestore
                var refCasa = db
                    .collection("casa")
                refCasa
                    .whereEqualTo("usuario_uid",UsuarioSelect.uid.toString())
                    .get()
                    .addOnSuccessListener {
                        for(casa in it){
                            arrayCasasUIDEliminar.add(casa.id.toString())
                            refCasa.document(casa.id).delete()
                                .addOnSuccessListener {
                                    Log.d("list-view", "DocumentSnapshot successfully deleted!")
                                }
                                .addOnFailureListener { e -> Log.w("list-view", "Error deleting document", e) }
                        }
                    }
                    .addOnFailureListener{}



                var refUsuario = db
                    .collection("usuario")

                refUsuario.document(UsuarioSelect.uid.toString())
                    .delete()
                    .addOnSuccessListener {
                        adapter?.remove(adapter!!.getItem(posicionItemSelecionado));
                        adapter?.notifyDataSetChanged()
                        Log.d("list-view", "DocumentSnapshot successfully deleted!")



                    }
                    .addOnFailureListener { e -> Log.w("list-view", "Error deleting document", e) }



                return true }
            //Ver Casas
            R.id.mi_verCasas -> {

                abrirActividadConParametros(VerCasas::class.java,UsuarioSelect)

                Log.i("list-view","Ver Casas ${UsuarioSelect}")

                return true }

            else -> super.onContextItemSelected(item)
        }
    }

    fun quemarDatos(){
        val arrayUsuarios1 =  arrayListOf<UsuarioDto>()
        arrayUsuarios1.add(
            UsuarioDto(
            null,
            "1111111111",
            "Nombre1",
            "Apellido1",
            "2111111",
            SimpleDateFormat("dd/MM/yyyy").parse("01/01/1991")

        ))
        arrayUsuarios1.add(
            UsuarioDto(
                null,
                "2222222222",
                "Nombre2",
                "Apellido2",
                "2222222",
                SimpleDateFormat("dd/MM/yyyy").parse("02/02/1992")

            ))
        arrayUsuarios1.add(
            UsuarioDto(
                null,
                "3333333333",
                "Nombre3",
                "Apellido3",
                "2333333",
                SimpleDateFormat("dd/MM/yyyy").parse("03/03/1993")

            ))

for(objeto in arrayUsuarios1){
    val nuevoUsuario = hashMapOf<String,Any>(
        "cedula" to objeto.cedula!!,
        "nombre" to objeto.nombre!!,
        "apellido" to objeto.apellido!!,
        "telefono" to objeto.telefono!!,
        "fechaNacimiento" to objeto.fechaNacimiento
    )
    val db = Firebase.firestore
    val referencia = db.collection("usuario").document()
    referencia.set(nuevoUsuario)
}






    }

}