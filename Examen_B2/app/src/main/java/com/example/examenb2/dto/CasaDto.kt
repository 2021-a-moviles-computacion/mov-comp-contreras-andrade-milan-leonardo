package com.example.examenb2.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

class CasaDto (
    var uid:String?,
    var uid_usuario:String?,
    var numcasa: String?,
    var ubicacion: LatLng?,
    var terrenoArea:Double ,
    var construccionArea:Double,
    var parqueaderos:Int,
    var avaluo:Double,
    var bodega:Boolean,
        ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun toString(): String {
        return  "Num. Casa: ${numcasa} \n" +
                "Ubicacion: ${ubicacion} \n" +
                "Área del terreno: ${terrenoArea} m2\n" +
                "Área de construcción: ${construccionArea} m2\n" +
                "Número de parqueaderos: ${parqueaderos} \n" +
                "Avalúo: ${String.format("%.2f", avaluo)} $\n" +
                "Tiene Bodega: ${if(bodega) "Si" else "No"}"

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(uid_usuario)
        parcel.writeString(numcasa)
        parcel.writeParcelable(ubicacion, flags)
        parcel.writeDouble(terrenoArea)
        parcel.writeDouble(construccionArea)
        parcel.writeInt(parqueaderos)
        parcel.writeDouble(avaluo)
        parcel.writeByte(if (bodega) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CasaDto> {
        override fun createFromParcel(parcel: Parcel): CasaDto {
            return CasaDto(parcel)
        }

        override fun newArray(size: Int): Array<CasaDto?> {
            return arrayOfNulls(size)
        }
    }
}