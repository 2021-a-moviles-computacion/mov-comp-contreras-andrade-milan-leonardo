package com.example.firebaseuno.dto

import java.text.NumberFormat
import java.util.*

class OrdenDto (
    val nombreProducto: String,
    val precioUnitario: Double,
    val cantidad: Int,
    val restaurante: FirestoreRestauranteDto
        ){
    override fun toString(): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(2)
        format.setCurrency(Currency.getInstance("USD"))

        return "${nombreProducto} || ${format.format(precioUnitario)} || ${cantidad.toString()} || ${format.format(calcularTotal())} "
    }

    fun calcularTotal():Double{
        return precioUnitario * cantidad.toDouble()
    }

}