package com.example.firebaseuno

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebaseuno.dto.FirestoreUsuarioDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val CODIGO_INICIO_SESION=102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //FirebaseApp.initializeApp(this)


        val botonLogin = findViewById<Button>(R.id.btn_login)

        botonLogin
            .setOnClickListener {
                llamarLoginUsuario()
            }

        val botonlogout = findViewById<Button>(R.id.btn_logout)

        botonlogout
            .setOnClickListener {
                solicitarSalirDelAplicativo()
            }

        val botonProducto = findViewById<Button>(R.id.btn_producto)
        botonProducto
            .setOnClickListener {
                val intent = Intent(
                    this,
                    CProducto::class.java
                )
                startActivity(intent)
            }

        val botonRestaurante= findViewById<Button>(R.id.btn_restaurante)
        botonRestaurante
            .setOnClickListener {
                val intent = Intent(
                    this,
                    DRestaurante::class.java
                )
                startActivity(intent)
            }



        val botonOrdenes= findViewById<Button>(R.id.btn_ordenes)
        botonOrdenes
            .setOnClickListener {
                val intent = Intent(
                    this,
                    EOrdenes::class.java
                )
                startActivity(intent)
            }

    }




    fun llamarLoginUsuario(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html")
                .build(),
            CODIGO_INICIO_SESION
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            CODIGO_INICIO_SESION-> {
                if(resultCode==Activity.RESULT_OK){
                    val usuario: IdpResponse? = IdpResponse.fromResultIntent(data)

                    if(usuario != null) {
                        if (usuario.isNewUser) {
                            Log.i("firebase-login", "Nuevo Usuario")
                            registrarUsuarioPorPrimeraVez(usuario)
                        } else {
                            setearUsuarioFirebase()
                            Log.i("firebase-login", "Nuevo Antiguo")
                        }
                    }
                }else{
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
        }
    }


    fun setearUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        if(usuarioLocal != null){
            if(usuarioLocal.email !=null){
                val db = Firebase.firestore

                val referencia = db
                    .collection("usuario")
                    .document(usuarioLocal.email.toString())

                referencia
                    .get()
                    .addOnSuccessListener {
                        val usuarioCargado : FirestoreUsuarioDto? =
                            it.toObject(FirestoreUsuarioDto::class.java)
                        if(usuarioCargado != null){
                            BAuthUsuario.usuario = BUsuarioFireBase(
                                usuarioCargado.uid,
                                usuarioCargado.email,
                                usuarioCargado.roles)
                        }
                        setearBienvenida()
                        //BAuthUsuario.usuario = usuarioCargado
                        Log.i("firebase-firestore", "Usuario Cargado")
                    }
                    .addOnFailureListener{
                        Log.i("firebase-firestore", "Fallo cargar usuario")
                    }

            }
        }

    }

    fun setearBienvenida(){
        val textViewBienvenida = findViewById<TextView>(R.id.tv_bienvenida)
        val botonLogin = findViewById<Button>(R.id.btn_login)
        val botonlogout = findViewById<Button>(R.id.btn_logout)
        val botonProducto = findViewById<Button>(R.id.btn_producto)
        val botonRestaurante= findViewById<Button>(R.id.btn_restaurante)
        val botonOrdenes= findViewById<Button>(R.id.btn_ordenes)

        if(BAuthUsuario.usuario !=null){
            textViewBienvenida.text = "Bienvenido ${BAuthUsuario.usuario?.email}"
            botonLogin.visibility = View.INVISIBLE
            botonlogout.visibility = View.VISIBLE
            botonProducto.visibility = View.VISIBLE
            botonRestaurante.visibility = View.VISIBLE
            //botonOrdenes.visibility = View.VISIBLE
        }else{
            textViewBienvenida.text = "Ingresa al aplicativo"
            botonLogin.visibility = View.VISIBLE
            botonlogout.visibility = View.INVISIBLE
            botonProducto.visibility = View.INVISIBLE
            botonRestaurante.visibility = View.INVISIBLE
            //botonOrdenes.visibility = View.INVISIBLE
        }
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){
        val usuarioLogeado = FirebaseAuth
            .getInstance()
            .getCurrentUser()


        if(usuario.email !=null && usuarioLogeado != null){
            //roles : ["usuario"],["admin"]
            val db = Firebase.firestore
            val rolesUsuario = arrayListOf("usuario")

            val identificadorUsuario = usuario.email

            val nuevoUsuario = hashMapOf<String, Any>(
                "roles" to rolesUsuario,
                "uid" to usuarioLogeado.uid,
                "email" to identificadorUsuario.toString()
            )

            db.collection("usuario")
                //opcion b
                .document(identificadorUsuario.toString())
                .set(nuevoUsuario)
               //opcion a
               // .add(nuevoUsuario)
                .addOnSuccessListener {
                    Log.i("firebase-login", "Se creo")
                    setearUsuarioFirebase()
                }
                .addOnFailureListener{
                    Log.i("firebase-login", "Fallo")
                }

        }else{
            Log.i("firebase-login", "ERROR")
        }
    }


    fun solicitarSalirDelAplicativo(){
        AuthUI
            .getInstance()
            .signOut(this)
            .addOnCompleteListener{
                BAuthUsuario.usuario = null
                setearBienvenida()
            }
    }



}