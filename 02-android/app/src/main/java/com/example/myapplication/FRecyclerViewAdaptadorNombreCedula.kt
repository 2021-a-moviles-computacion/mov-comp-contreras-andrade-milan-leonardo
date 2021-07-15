package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreCedula(
    private val contexto:GRecycleView,
    private val listaEntrenador:List<BEntrenador>,
    private val recyclerView: RecyclerView
):RecyclerView.Adapter<FRecyclerViewAdaptadorNombreCedula.MyViewHolder>(){
    inner class MyViewHolder(view:View):RecyclerView.ViewHolder(view){
        val nombreTextView: TextView
        val cedulaTextView:TextView
        val likesTextViews: TextView
        val accionButton: Button
        var numeroLikes=0

        init{
             nombreTextView=view.findViewById(R.id.tv_nombre)
             cedulaTextView=view.findViewById(R.id.tv_cedula)
            accionButton=view.findViewById(R.id.btn_dar_like)
             likesTextViews=view.findViewById(R.id.tv_likes)
            accionButton
                .setOnClickListener {
                    this.anadirLike()
                }
        }

        fun anadirLike(){
            this.numeroLikes = this.numeroLikes+1
            likesTextViews.text=this.numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }

    }

    //setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    //setear los datos de cada iteracion de arreglo
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenador = listaEntrenador[position]
        holder.nombreTextView.text=entrenador.nombre
        holder.cedulaTextView.text= entrenador.descripcion
        holder.accionButton.text="Like ${entrenador.nombre}"
        holder.likesTextViews.text="0"
    }
//tamano del arreglo
    override fun getItemCount(): Int {
        return listaEntrenador.size
    }

}