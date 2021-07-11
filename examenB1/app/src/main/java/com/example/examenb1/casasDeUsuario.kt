package com.example.examenb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import java.text.SimpleDateFormat

class casasDeUsuario : AppCompatActivity() {
    var posicionItemSelecionado = 0
    var adapter: ArrayAdapter<Casa>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casas_de_usuario)

        val usuario= intent.getParcelableExtra<Usuario>("usuario")

        val usuarioid = usuario?.id!!.toInt()

        Log.i("prueba", "id usuario: $usuarioid")

        BaseDeDatos.Tablas= SQLiteHelper(this)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, BaseDeDatos.Tablas!!.consultarCasasPorIdUsuario(usuarioid))
        val listViewUsuario = findViewById<ListView>(R.id.ltv_casas)
        listViewUsuario.adapter = adapter


        registerForContextMenu(listViewUsuario)


        val botonCrearCasa = findViewById<Button>(R.id.btn_crearCasa)
        botonCrearCasa
            .setOnClickListener {
             //   abrirActividad()
            }


    }
}