package com.example.examenb1

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class Usuario (
    var id:Int,
    var cedula: String?,
    var nombre: String?,
    var apellido:String?,
    var telefono:String?,
    var fechaNacimiento: Date?
):Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        //(parcel.readString()),
        Date(parcel.readString())

    ) {
    }
/*
    override fun toString(): String {
        return  "${id} - " +
                "${cedula} - " +
                "${nombre} - " +
                "${apellido} - " +
                "${telefono} - " +
                "${SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento)}"
    }

 */


    override fun toString(): String {
        return  "CI: ${cedula} \n" +
                "Nombre: ${nombre}  \n" +
                "Apellido: ${apellido} \n" +
                "Teléfono: ${telefono} \n"+
                "Nacimiento: ${SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento)}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(cedula)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(telefono)
        parcel.writeString(SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento))

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }

}