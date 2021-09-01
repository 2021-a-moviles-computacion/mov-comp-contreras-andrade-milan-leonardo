package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import com.example.firebaseuno.dto.FirestoreProductoDto
import com.example.firebaseuno.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {

    var arregloRestaurante1 = ArrayList<FirestoreRestauranteDto>()
    var arregloProductos1 = ArrayList<FirestoreProductoDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)


        var documentoRestaurante:(MutableList<DocumentSnapshot>)
        var documentoProductos:(MutableList<DocumentSnapshot>)

        var arrayRestaurantes= ArrayList<FirestoreRestauranteDto>()
        var arrayProductos = ArrayList<FirestoreProductoDto>()

        val db = Firebase.firestore
        val referenciaRestaurante = db.collection("restaurante")
        val referenciaProducto = db.collection("producto")


        referenciaRestaurante
            .get()
            .addOnSuccessListener {
                documentoRestaurante  = it.documents
                documentoRestaurante.forEach { iteracion ->
                    var restauranteObjeto = iteracion.toObject(FirestoreRestauranteDto::class.java)
                    restauranteObjeto!!.uid = iteracion.id

                    arrayRestaurantes.add(restauranteObjeto)
                }
                    //llenar arreglo de la clase con restaurantes
                arregloRestaurante1=arrayRestaurantes


                referenciaProducto
                    .get()
                    .addOnSuccessListener {
                    documentoProductos  = it.documents
                    documentoProductos.forEach { iteracion ->
                        var productoObjeto = iteracion.toObject(FirestoreProductoDto::class.java)
                        productoObjeto!!.uid=iteracion.id
                        productoObjeto!!.nombre=iteracion.get("nombre").toString()
                        productoObjeto!!.precio=iteracion.get("precio").toString().toDouble()

                        arrayProductos.add(productoObjeto)
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

            }
        //=======================================
        val listViewProductos = findViewById<ListView>(R.id.lv_lista_productos)
        val botonAdd = findViewById<Button>(R.id.btn_anadir_lista_producto)

    }





}