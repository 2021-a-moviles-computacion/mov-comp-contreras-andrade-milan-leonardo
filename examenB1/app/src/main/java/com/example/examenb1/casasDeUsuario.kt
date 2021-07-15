package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class casasDeUsuario : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var posicionItemSelecionado = 0
    var idCasaItemSelecionado = 0
    var usuarioid=0
    var adapter: ArrayAdapter<Casa>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casas_de_usuario)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        val a = getActionBar()


        val usuario= intent.getParcelableExtra<Usuario>("usuario")


            usuarioid = usuario!!.id

        val valtextViewUsuario = findViewById<TextView>(R.id.tv_usuario_casas)

        valtextViewUsuario.text = usuario.nombre +" "+usuario.apellido

        Log.i("prueba", "id usuario: $usuarioid")

        BaseDeDatos.Tablas= SQLiteHelper(this)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, BaseDeDatos.Tablas!!.consultarCasasPorIdUsuario(usuarioid))
        val listViewUsuario = findViewById<ListView>(R.id.ltv_casas)
        listViewUsuario.adapter = adapter


        registerForContextMenu(listViewUsuario)


        val botonCrearCasa = findViewById<Button>(R.id.btn_irAActividadCrearCasa)
        botonCrearCasa
            .setOnClickListener {
                Log.i("boton","click en boton ${usuario.toString()}")
                abrirActividadConParametros(crearCasa::class.java,usuario)
            }





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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_casa,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
        Log.i("list-view-casa","onCreate ${posicionItemSelecionado}")
        Log.i("list-view-casa","Casa id: ${BaseDeDatos.Tablas!!.consultarCasasPorIdUsuario(usuarioid)[posicionItemSelecionado].id}")

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        var CasaSelect = BaseDeDatos.Tablas!!.consultarCasasPorIdUsuario(usuarioid)[posicionItemSelecionado]

        return when(item?.itemId){

            //Editar
            R.id.mi_editarCasa -> {
                Log.i("list-view-casa","Editar Casa id: ${CasaSelect}")
                abrirActividadConParametros2(editarCasa::class.java,CasaSelect)
                return true
            }

            //Eliminar
            R.id.mi_eliminarCasa -> {
                Log.i("list-view-casa","Eliminar Casa id: ${CasaSelect}")

                if(BaseDeDatos.Tablas!=null){
                    BaseDeDatos.Tablas!!.eliminarCasaFormularioPorId(CasaSelect.id)
                    adapter?.remove(adapter!!.getItem(posicionItemSelecionado));
                    adapter?.notifyDataSetChanged()

                }

                return true }

            else -> super.onContextItemSelected(item)
        }
    }




    fun abrirActividadConParametros2(
        clase: Class<*>,
        casa: Casa,
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


}