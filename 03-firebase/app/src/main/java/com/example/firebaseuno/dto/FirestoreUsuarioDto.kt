package com.example.firebaseuno.dto

data class FirestoreUsuarioDto(val uid: String = "",
                               val email: String = "",
                               val roles: ArrayList<String> = arrayListOf()) {
}