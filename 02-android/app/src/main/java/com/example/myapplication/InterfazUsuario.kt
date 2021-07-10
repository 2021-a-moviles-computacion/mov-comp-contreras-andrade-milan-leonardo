package com.example.myapplication

import android.os.Bundle

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity





class InterfazUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz_usuario)

        EBaseDeDatos.TablaUsuario= ESqliteHelperUsuario(this)

        val arregloUsuarios = EBaseDeDatos.TablaUsuario!!.devolverUsuariosFormulario()
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloUsuarios)
        val listViewEjemplo = findViewById<ListView>(R.id.ltv_UsuariosBDD)
        listViewEjemplo.adapter = adaptador



        //Crear Usuario
        val ingresarNombreUsuario = findViewById<EditText>(R.id.editText_IngresarNombreUsuario)
        val ingresarDescripcionUsuario = findViewById<EditText>(R.id.editText_IngresarDescripcionUsuario)
        val botonCrearUsuario = findViewById<Button>(R.id.btn_ingresarUsuario)
        botonCrearUsuario
            .setOnClickListener {

                if(EBaseDeDatos.TablaUsuario!=null){
                    EBaseDeDatos.TablaUsuario!!.crearUsuarioFormulario(ingresarNombreUsuario.text.toString(),ingresarDescripcionUsuario.text.toString())
                    //  BBaseDatos.TablaUsuario!!.crearUsuarioFormulario(nombre.text.toString(),descripcion.text.toString())
                    Log.i("Interfaz-usuario", "Creando el Usuario: Nombre: ${ingresarNombreUsuario.text} Descripcion: ${ingresarDescripcionUsuario.text}")
                    ingresarNombreUsuario.setText("")
                    ingresarDescripcionUsuario.setText("")
                    adaptador.notifyDataSetChanged()
                }
            }

        //Consultar Usuario
        val idAConsultar = findViewById<EditText>(R.id.editText_idUsuario)
        val nombreConsultado = findViewById<EditText>(R.id.editText_editarNombreUsuario)
        val descripcionConsultada = findViewById<EditText>(R.id.editText_EditarDescripcionUsuario)
        val botonConsultarUsuarioPorId = findViewById<Button>(R.id.btn_consultarUsuario)
        botonConsultarUsuarioPorId
            .setOnClickListener {
                if(EBaseDeDatos.TablaUsuario!=null){
                    val resultadoConsulta = EBaseDeDatos.TablaUsuario!!.consultarUsuarioPorId(idAConsultar.text.toString().toInt())
                    nombreConsultado.setText(resultadoConsulta.nombre)
                    descripcionConsultada.setText(resultadoConsulta.descripcion)
                    Log.i("Interfaz-usuario", "Usuario encontrado: id: ${idAConsultar.text.toString()} ${resultadoConsulta.nombre.toString()}  ${resultadoConsulta.descripcion.toString()}")
                }

            }

        //Actualizar Usuario
        val botonActualizarUsuario =findViewById<Button>(R.id.btn_EditarUsuario)
        botonActualizarUsuario
            .setOnClickListener {
                if(EBaseDeDatos.TablaUsuario!=null){
                        EBaseDeDatos.TablaUsuario!!.actualizarUsuarioFormulario(nombreConsultado.text.toString(),descripcionConsultada.text.toString(),idAConsultar.text.toString().toInt())
                        Log.i("Interfaz-usuario", "Actualizado Usuario  Id: ${idAConsultar.text.toString()}  Nombre:${nombreConsultado.text.toString()}  Descripcion: ${descripcionConsultada.text.toString()}")
                    nombreConsultado.setText("")
                    descripcionConsultada.setText("")
                    adaptador.notifyDataSetChanged()
                }
            }

        //Eliminar Usuario
        val botonEliminarUsuario =findViewById<Button>(R.id.btn_eliminarUsuario)
        botonEliminarUsuario
            .setOnClickListener {
                if(EBaseDeDatos.TablaUsuario!=null){
                    EBaseDeDatos.TablaUsuario!!.eliminarUsuarioFomrulario(idAConsultar.text.toString().toInt())
                    Log.i("Interfaz-usuario", "Usuario Eliminado: ${idAConsultar}")
                    nombreConsultado.setText("")
                    descripcionConsultada.setText("")
                    adaptador.notifyDataSetChanged()
                }
            }




        listViewEjemplo.setOnItemClickListener{
                adapterView, view, posicion, id->

            val isUsuario =arregloUsuarios[posicion].id

            idAConsultar.setText(isUsuario.toString())
            botonConsultarUsuarioPorId.performClick()
            Log.i("list-view2","Dio click  ${arregloUsuarios[posicion].id}")
        }



    }

    }
