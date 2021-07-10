package com.example.examenb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    var posicionItemSelecionado = 0
    var adapter: ArrayAdapter<Usuario>? = null

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

        //adaptador!!.notifyDataSetChanged()



        //listViewUsuario.setOnItemClickListener { parent, view, position, id ->
        //registerForContextMenu(listViewUsuario)
        //listViewUsuario.showContextMenu();
        //   listViewUsuario.showContextMenuForChild(view);
        //}

/*
        listViewUsuario.setOnItemLongClickListener{
                adapterView, view, posicion, id ->
            Log.i("list-view","Dio click ${posicion}")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Titulo")
            // builder.setMessage("Mensaje")

            val seleccionUsuario = booleanArrayOf(
                true,
                false,
                false
            )
            //val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
            builder.setMultiChoiceItems(
                null,
                seleccionUsuario
            ) { dialog, which, isChhecked ->
                Log.i("list-view", "${which} ${isChhecked}")
            }

            builder.setPositiveButton(
                "Si",
            ) { dialog, which ->
                Log.i("list-view", "Si")
            }

            builder.setNegativeButton(
                "No",
                null
            )


            val dialogo = builder.create()
            dialogo.show()

            return@setOnItemLongClickListener true
        }

        */
    }


    fun actualizarListView(){
        val arregloUsuarios = BaseDeDatos.Tablas!!.consultarUsuariosFormulario()
        adapter?.notifyDataSetChanged();
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

            else -> super.onContextItemSelected(item)
        }
    }


}