package com.example.myapplication

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
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {

    var posicionItemSeleciinado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)



        val arregloNumeros = BBaseDatosMemoria.arregloBEntrenador

        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloNumeros)

        val listViewEjemplo = findViewById<ListView>(R.id.ltv_ejemplo)

        listViewEjemplo.adapter = adaptador





        val botonAñadirItem = findViewById<Button>(R.id.btn_añadir_numero)
        //botonAñadirItem.setOnClickListener{añadirItemsAlListView(BEntrenador("Prueba","d@d.com"),arregloNumeros, adaptador)}

        listViewEjemplo.setOnItemLongClickListener{
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
             val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
            builder.setMultiChoiceItems(
                opciones,
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

       // registerForContextMenu(listViewEjemplo)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleciinado = id
        Log.i("list-view","onCreate ${posicionItemSeleciinado}")
        Log.i("list-view","Entrenador ${BBaseDatosMemoria.arregloBEntrenador[id]}")

    }


    fun añadirItemsAlListView(valor:BEntrenador,arreglo: ArrayList<BEntrenador>, adaptador: ArrayAdapter<BEntrenador>){
        arreglo.add(valor)
        adaptador.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        return when(item?.itemId){

            //Editar
                R.id.mi_editar -> {
                    Log.i("list-view","Editar ${BBaseDatosMemoria.arregloBEntrenador
                    [posicionItemSeleciinado]}")

                    return true
                }

            //Eliminar
            R.id.mi_eliminar -> {
                Log.i("list-view","Eliminar ${BBaseDatosMemoria.arregloBEntrenador
                        [posicionItemSeleciinado]}")

                return true }

            else -> super.onContextItemSelected(item)
        }
    }
}