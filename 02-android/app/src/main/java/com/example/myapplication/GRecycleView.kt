package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GRecycleView : AppCompatActivity() {
    var totalLikes=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grecycle_view)

        val listaEntrenador = arrayListOf<BEntrenador>()
        val ligaPokemon = DLiga("Kanto","Liga Kanto")

        listaEntrenador
            .add(
                BEntrenador(
                    "Vicente",
                    "1718137159",
                    ligaPokemon
                )
            )

        listaEntrenador
            .add(
                BEntrenador(
                    "Adrian",
                    "0198137123",
                    ligaPokemon
                )
            )

        val recyclerViewEntrenador = findViewById<RecyclerView>(R.id.rv_entrenadores)

        iniciarRecyclerView(
            listaEntrenador,
            this,
            recyclerViewEntrenador
        )


    }

    fun iniciarRecyclerView(
        lista: List<BEntrenador>,
        actividad: GRecycleView,
        recyclerView: RecyclerView
    ){

        val adaptador = FRecyclerViewAdaptadorNombreCedula(
            actividad,
            lista,
            recyclerView
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager= LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()

    }

    fun aumentarTotalLikes(){
        totalLikes= totalLikes+1
        val textView = findViewById<TextView>(R.id.tv_total_likes)
        textView.text=totalLikes.toString()
    }






}















