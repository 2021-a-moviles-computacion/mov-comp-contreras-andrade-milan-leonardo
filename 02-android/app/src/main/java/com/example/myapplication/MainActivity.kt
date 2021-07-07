package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val botonIrACicloVida = findViewById<Button>(R.id.btn_ciclo_vida)  //Lo heredamos de la clase.
        botonIrACicloVida.setOnClickListener{ abrirActividad(ACicloVida::class.java) }

        val botonIrBListView = findViewById<Button>(R.id.btn_ir_list_view)  //Lo heredamos de la clase.
        botonIrBListView.setOnClickListener{ abrirActividad(BListView::class.java) }

        val botonIrIntent = findViewById<Button>(R.id.btn_ir_intent)  //Lo heredamos de la clase.
        botonIrIntent.setOnClickListener{ abrirActividadConParametros(CIntentExplicioParametros::class.java) }

        val botoAbrirIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_implicit)

        botoAbrirIntentExplicito
            .setOnClickListener{
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                startActivityForResult(intentConRespuesta,CODIGO_RESPUESTA_INTENT_EXPLICITO)
        }


        val botonIrInterfazUsuario = findViewById<Button>(R.id.btn_ir_interfazUsuario)

        botonIrInterfazUsuario
            .setOnClickListener {
                abrirActividad(InterfazUsuario::class.java)
            }


    }


    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("apellido","Eguez")
        intentExplicito.putExtra("edad","32")
        //intentExplicito.putExtra(
        //    "entrenador",
            //BEntrenador("Adrian","Eguez")
        //)

        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            when(it.resultCode){
//                Activity.RESULT_OK-> {
//                    //Ejecutar codigo OK
//                    it.data?.getStringExtra("nombreModofocado")
//                    it.data?.getIntExtra("edadModificada",0)
//                    it.data?.getParcelableExtra<BEntrenador>("entrenadorModificado")
//                }
//            }
//        }
        //startActivity(intentExplicito)
    }





    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }

    override fun onActivityResult(requestCode: Int, resultCode:Int,data: Intent?){
        super.onActivityResult(requestCode,resultCode,data)
        when(requestCode){
            CODIGO_RESPUESTA_INTENT_EXPLICITO -> {
                if(resultCode== RESULT_OK){
                    Log.i("intent-explicito","SI actualizo los datos")
                    if(data !=null){
//                        val nombre = data.getStringExtra("nombreModificado")
//                        val edad = data.getIntExtra("edadModificada",0)
//                        val entrenador = data.getParcelableExtra<BEntrenador>("entrenadorModificado")
//                        Log.i("inten-explicito","${nombre}")
//                        Log.i("inten-explicito","${edad}")
//                        Log.i("inten-explicito","${entrenador}")
                        val uri: Uri =data.data!!
                        val cursor = contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null,
                        )
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(
                            indiceTelefono!!
                        )

                        cursor?.close()
                        Log.i("resultado","Telefono ${telefono}")
                    }
                }
            }
        }
    }

}