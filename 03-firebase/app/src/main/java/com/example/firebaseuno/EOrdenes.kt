package com.example.firebaseuno

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebaseuno.dto.FirestoreProductoDto
import com.example.firebaseuno.dto.FirestoreRestauranteDto
import com.example.firebaseuno.dto.OrdenDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {

    var arregloRestaurante1 = ArrayList<FirestoreRestauranteDto>()
    var arregloProductos1 = ArrayList<FirestoreProductoDto>()
    var productoSeleccionado = FirestoreProductoDto()
    var restauranteSeleccionado = FirestoreRestauranteDto()
    var arregloOrden = ArrayList<OrdenDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)


        val textViewEcabezado = findViewById<TextView>(R.id.tv_encabezado_text_view)
        textViewEcabezado.text = "PRODUCTO \t VALOR UNIT. \t CANT. \t VALOR"

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


        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
        val spinnerProductos = findViewById<Spinner>(R.id.sp_producto)

        spinnerRestaurantes.onItemSelectedListener = object:
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restauranteSeleccionado = arregloRestaurante1[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }


        }

        spinnerProductos.onItemSelectedListener = object:
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                productoSeleccionado = arregloProductos1[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }


        }


        val editTextCantidadProductos = findViewById<EditText>(R.id.et_cantidad_producto)
        val botonAdd = findViewById<Button>(R.id.btn_anadir_lista_producto)


        val listViewProductos = findViewById<ListView>(R.id.lv_lista_productos)
        val adaptadorListaProductos = ArrayAdapter(this, android.R.layout.simple_selectable_list_item , arregloOrden)
        listViewProductos.adapter = adaptadorListaProductos


        botonAdd.setOnClickListener {
            var editTextCantidadProductosValorCorregido:String = editTextCantidadProductos.text.toString()
            if(editTextCantidadProductosValorCorregido == ""){
                editTextCantidadProductosValorCorregido = "1"
            }
            val orden = OrdenDto(productoSeleccionado.nombre,productoSeleccionado.precio!!,editTextCantidadProductosValorCorregido.toInt())
            añadirItemsAlListView(orden,adaptadorListaProductos)
            /*
            Log.i("help",
                "orden:\n" +
                        "${productoSeleccionado.nombre}\n" +
                    "${productoSeleccionado.precio!!}\n" +
                        "${editTextCantidadProductosValorCorregido}\n")
            Log.i("help",
                "orden 2: ${orden}")

             */
        }

        val botonCompletarPedido = findViewById<Button>(R.id.btn_completar_pedido)
        botonCompletarPedido
            .setOnClickListener {
                Log.i("help", "Productos seleccionados: ${arregloOrden.toString()}")
            }

    }

    fun añadirItemsAlListView(objeto:OrdenDto, adaptador: ArrayAdapter<OrdenDto>){
        arregloOrden.add(objeto)
        adaptador.notifyDataSetChanged()
    }






    }
