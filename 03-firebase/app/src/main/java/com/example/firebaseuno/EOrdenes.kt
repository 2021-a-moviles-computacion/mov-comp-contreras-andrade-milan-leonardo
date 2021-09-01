package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {

    var arregloRestaurante1 = ArrayList<String>()
    var arregloProductos1 = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)


        var documentoRestaurante:(MutableList<DocumentSnapshot>)
        var documentoProductos:(MutableList<DocumentSnapshot>)

        var arrayRestaurantes= ArrayList<String>()
        var arrayProductos = ArrayList<String>()
        val db = Firebase.firestore

        var referencia = db.collection("restaurante")
            .get()
            .addOnSuccessListener {
                documentoRestaurante  = it.documents
                documentoRestaurante.forEach { iteracion ->
                    arrayRestaurantes.add(iteracion.get("nombre").toString())
                }
                    //llenar arreglo de la clase con restaurantes
                arregloRestaurante1=arrayRestaurantes

                val referencia2 = db.collection("producto")
                referencia2
                    .get()
                    .addOnSuccessListener {
                    documentoProductos  = it.documents
                    documentoProductos.forEach { iteracion ->
                        arrayProductos.add(iteracion.get("nombre").toString())
                    }
                        //agregar al array producto
                        arregloProductos1=arrayProductos

                        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
                        val spinnerProductos = findViewById<Spinner>(R.id.sp_producto)

                        val adaptadorRestaurantes = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arregloRestaurante1)
                        spinnerRestaurantes.adapter = adaptadorRestaurantes

                        val adaptadorProductos = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arregloProductos1)
                        spinnerProductos.adapter = adaptadorProductos
                }

                Log.i("help", "obtuvo")
            }



        //=======================================


    }





}