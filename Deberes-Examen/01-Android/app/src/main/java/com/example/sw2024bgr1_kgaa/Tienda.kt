package com.example.sw2024bgr1_kgaa

class Tienda(
    var id: Int,          // Cambiar `Int` por `String`
    var nombre: String,
    var dueno: String,
    var ubicacion: String?,
    var coordenadas: String?


) {
    override fun toString(): String {
        return "Nombre: $nombre, Dueño: $dueno, Ubicación: $ubicacion"
    }


}