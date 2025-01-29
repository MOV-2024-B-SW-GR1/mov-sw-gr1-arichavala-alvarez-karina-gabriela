package com.example.sw2024bgr1_kgaa

class BDTiendas {

    companion object {
        val arregloTiendas = arrayListOf<Tienda>()

        init {
            // Agregar datos iniciales de las tiendas
            arregloTiendas.add(Tienda(1,"Zapatería Central", "Juan Pérez", "Av. Principal 123"))
            arregloTiendas.add(Tienda(2,"Tienda del Este", "María López", "Calle Secundaria 456"))
            arregloTiendas.add(Tienda(3,"Zapatos Elegantes", "Carlos Torres", "Plaza Comercial 789"))


        }

        //CRUD
        //Eliminar
        fun eliminarTienda(posicion: Int): Tienda? {
            return if (posicion in arregloTiendas.indices) {
                arregloTiendas.removeAt(posicion)
            } else {
                null // Retorna null si la posición no existe
            }
        }

        //Editar
        fun editarTienda(posicion: Int, nombre: String, dueno: String, ubicacion: String): Boolean {
            return if (posicion in arregloTiendas.indices) {
                val tienda = arregloTiendas[posicion]
                tienda.nombre = nombre
                tienda.dueno = dueno
                tienda.ubicacion = ubicacion
                true // Edición exitosa
            } else {
                false // Posición inválida
            }
        }



    //
    }
}