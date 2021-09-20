package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ImagenesStorage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagenes_storage)

        val storage = Firebase.storage
        //var storageRef = storage.reference
        val gsReference = storage.getReferenceFromUrl("gs://mov-2021-a-cont-andr.appspot.com/p_viuda_negra2.png")

        //gsReference.get()

        val imageView = findViewById<ImageView>(R.id.imageView1)
        Glide.with(this /* context */)
            .load(gsReference)
            .into(imageView)




    }
}