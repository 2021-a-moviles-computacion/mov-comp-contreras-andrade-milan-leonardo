package com.example.firebaseuno.dto

import com.google.type.Date

class OrdenMCDto (
    fechaPedido: Date,
    total: Double,
    calificacion: Double?,
    estado: String,
    usuario: String,
    restaurante: FirestoreRestauranteDto,
    productos: List<ProductoMCDto>

    ){
}