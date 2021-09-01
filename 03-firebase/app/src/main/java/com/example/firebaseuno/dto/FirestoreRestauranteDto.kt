package com.example.firebaseuno.dto

import android.os.Parcel
import android.os.Parcelable

class FirestoreRestauranteDto(
    var uid:String? = null,
    var nombre:String? = null
) {

    override fun toString(): String {
        return nombre!!
    }
}

