package com.example.examenb2.dto

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class UsuarioDto(
    var uid:String?,
    var cedula:String?,
    var nombre:String?,
    var apellido:String?,
    var telefono:String?,
    var fechaNacimiento: java.util.Date,
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        Date(parcel.readString())
    ) {
    }
    override fun toString(): String {
        return  "CI: ${cedula} \n" +
                "Nombre: ${nombre}  \n" +
                "Apellido: ${apellido} \n" +
                "Teléfono: ${telefono} \n"+
                "Nacimiento: ${SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento)}"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(uid)
        dest.writeString(cedula)
        dest.writeString(nombre)
        dest.writeString(apellido)
        dest.writeString(telefono)
        dest.writeString(SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento))
    }

    companion object CREATOR : Parcelable.Creator<UsuarioDto> {
        override fun createFromParcel(parcel: Parcel): UsuarioDto {
            return UsuarioDto(parcel)
        }

        override fun newArray(size: Int): Array<UsuarioDto?> {
            return arrayOfNulls(size)
        }
    }
}