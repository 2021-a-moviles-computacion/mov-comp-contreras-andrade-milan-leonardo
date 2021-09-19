package com.example.examenb1

import android.os.Parcel
import android.os.Parcelable
import java.math.RoundingMode

class Casa (

    var id:Int,
    var id_usuario:Int?,
    var numcasa: String?,
    var direccion: String?,
    var terrenoArea:Double ,
    var construccionArea:Double,
    var parqueaderos:Int,
    var avaluo:Double,
    var bodega:Boolean,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    ) {
    }
/*
    override fun toString(): String {
        return  "${id} - " +
                "${id_usuario} - " +
                "${numcasa} - " +
                "${direccion} - " +
                "${terrenoArea} - " +
                "${construccionArea} - " +
                "${parqueaderos} - " +
                "${avaluo} - " +
                "${bodega}"
    }

 */

    override fun toString(): String {
            return  "Num. Casa: ${numcasa} \n" +
                    "Dirección: ${direccion} \n" +
                    "Área del terreno: ${terrenoArea} m2\n" +
                    "Área de construcción: ${construccionArea} m2\n" +
                    "Número de parqueaderos: ${parqueaderos} \n" +
                    "Avalúo: ${String.format("%.2f", avaluo)} $\n" +
                    "Tiene Bodega: ${if(bodega) "Si" else "No"}"

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(id_usuario)
        parcel.writeString(numcasa)
        parcel.writeString(direccion)
        parcel.writeDouble(terrenoArea)
        parcel.writeDouble(construccionArea)
        parcel.writeInt(parqueaderos)
        parcel.writeDouble(avaluo)
        parcel.writeByte(if (bodega) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Casa> {
        override fun createFromParcel(parcel: Parcel): Casa {
            return Casa(parcel)
        }

        override fun newArray(size: Int): Array<Casa?> {
            return arrayOfNulls(size)
        }
    }

}