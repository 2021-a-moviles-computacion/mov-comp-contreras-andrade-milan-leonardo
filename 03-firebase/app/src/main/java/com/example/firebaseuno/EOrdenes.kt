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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)

        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
        var documentoRestaurante:(MutableList<DocumentSnapshot>)
        var arrayRestaurantes= ArrayList<String>()
        val db = Firebase.firestore

        var referencia = db.collection("restaurante")
            .get()
            .addOnSuccessListener {
                documentoRestaurante  = it.documents
                documentoRestaurante.forEach { iteracion ->
                    arrayRestaurantes.add(iteracion.get("nombre").toString())
                }

                val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayRestaurantes)
                spinnerRestaurantes.adapter = adaptador
            }
        

        
        //=======================================

        val spinnerProducto = findViewById<Spinner>(R.id.sp_producto)

        var documentoProductos:(MutableList<DocumentSnapshot>)
        var arrayProductos= ArrayList<String>()
        val referencia2 = db.collection("producto").get().addOnSuccessListener {
            documentoProductos  = it.documents
            documentoProductos.forEach { iteracion ->
                arrayProductos.add(iteracion.get("nombre").toString())
            }
            val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayProductos)
            spinnerProducto.adapter = adaptador
        }

    }


    fun obtenerRestaurantes(): List<String> {

        var documentoRestaurantes:(MutableList<DocumentSnapshot>)
        var arrayRestaurantes= ArrayList<String>()
        val db = Firebase.firestore
        var referencia = db.collection("restaurante")
            .get()
            .addOnSuccessListener {
                documentoRestaurantes  = it.documents
                documentoRestaurantes.forEach {iteracion ->
                    arrayRestaurantes.add(iteracion.get("nombre").toString())
                }
            }
            return arrayRestaurantes
    }


}