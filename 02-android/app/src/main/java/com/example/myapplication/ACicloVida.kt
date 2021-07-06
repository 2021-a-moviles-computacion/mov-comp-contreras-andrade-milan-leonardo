package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ACicloVida : AppCompatActivity() {

    var numero = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        Log.i("ciclo-vida","onCreate")


        val texViewACicloVida = findViewById<TextView>(R.id.tvx_ciclo_vida) //Obtenemos el valor del txt

        texViewACicloVida.text=numero.toString() //Le ponemos el valor de 0


        val buttonACicloVida=findViewById<Button>(R.id.btn_aumentar_ciclo_vida) //Obtenemos el boton ese.

        buttonACicloVida.setOnClickListener { aumentarNumero() } //Colocamos la función.



    }
    //guardamos solo primitivos
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run{
            putInt("numeroguardado", numero)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val numeroRecuperado:Int? = savedInstanceState.getInt("numeroguardado")
        if(numeroRecuperado!= null){
            numero = numeroRecuperado
            val texViewACicloVida = findViewById<TextView>(R.id.tvx_ciclo_vida) //Obtenemos el valor del txt

            texViewACicloVida.text=numero.toString()

        }
    }


    fun aumentarNumero(){
        numero=numero+1
        val texViewACicloVida = findViewById<TextView>(
            R.id.tvx_ciclo_vida
        )
        texViewACicloVida.text=numero.toString()
    }


    override fun onStart(){
        super.onStart()
        Log.i("ciclo-vida","onStart")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i("ciclo-vida","onRestart")
    }

    override fun onResume(){
        super.onResume()
        Log.i("ciclo-vida","onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i("ciclo-vida","onPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i("ciclo-vida","onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("ciclo-vida","onDestroy")
    }


}