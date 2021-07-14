package com.example.examenb1

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
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    var posicionItemSelecionado = 0
    var adapter: ArrayAdapter<Usuario>? = null
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        BaseDeDatos.Tablas= SQLiteHelper(this)

        val formato = SimpleDateFormat("dd/MM/yyyy")

        //  BaseDeDatos.Tablas!!.crearUsuarioFormulario("1719051984","nombremc4","apellidomc4","099999949",
        //     "04/02/1998")

        // BaseDeDatos.Tablas!!.crearUsuarioFormulario("1719051984","nombremc5","apellidomc5","099999949",
        //     "04/02/1998")
        //Log.i("actual", "${arregloUsuarios} \n")
        //listView = findViewById<View>(R.id.lvContextMenu) as ListView
        // = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name)


        //val arregloUsuarios = BaseDeDatos.Tablas!!.consultarUsuariosFormulario()
        //val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloUsuarios)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, BaseDeDatos.Tablas!!.consultarUsuariosFormulario())
        val listViewUsuario = findViewById<ListView>(R.id.ltv_usuarios)
        listViewUsuario.adapter = adapter


        registerForContextMenu(listViewUsuario)


        val botonIrActividadcrearUsuarioUsuario = findViewById<Button>(R.id.btn_ir_crear_usuario)
        botonIrActividadcrearUsuarioUsuario
            .setOnClickListener {
                abrirActividad(crearUsuario::class.java)
            }

    }



    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu1,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
        Log.i("list-view","onCreate ${posicionItemSelecionado}")
        Log.i("list-view","Usuario ${BaseDeDatos.Tablas!!.consultarUsuariosFormulario()[posicionItemSelecionado].id}")

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        var UsuarioSelect = BaseDeDatos.Tablas!!.consultarUsuariosFormulario()[posicionItemSelecionado]
        return when(item?.itemId){

            //Editar
            R.id.mi_editar -> {
                Log.i("list-view","Editar ${UsuarioSelect}")
                //Log.i("list-view2","Editar")
                abrirActividadConParametros(actualizarUsuario::class.java,UsuarioSelect)

                return true
            }

            //Eliminar
            R.id.mi_eliminar -> {
                Log.i("list-view","Eliminar ${UsuarioSelect}")

                if(BaseDeDatos.Tablas!=null){
                    BaseDeDatos.Tablas!!.eliminarUsuarioFormulario(UsuarioSelect.id)
                    adapter?.remove(adapter!!.getItem(posicionItemSelecionado));
                    adapter?.notifyDataSetChanged()

                }
                return true }
            //Ver Casas
            R.id.mi_verCasas -> {

                abrirActividadConParametros(casasDeUsuario::class.java,UsuarioSelect)

                Log.i("list-view","Ver Casas ${UsuarioSelect}")

                return true }

            else -> super.onContextItemSelected(item)
        }
    }



    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: Usuario,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }


}