package com.example.sw2024bgr1_kgaa

class Zapato(
    var id: Int,             // Identificador único del zapato
    var marca: String,       // Marca del zapato
    var talla: Int,          // Talla del zapato
    var color: String,       // Color del zapato
    var precio: Double,       // Precio del zapato
    val idTienda: Int        //Identificador de tienda asociada
) {
    override fun toString(): String {
        return "Marca: $marca, Talla: $talla, Color: $color, Precio: $$precio"
    }
}
