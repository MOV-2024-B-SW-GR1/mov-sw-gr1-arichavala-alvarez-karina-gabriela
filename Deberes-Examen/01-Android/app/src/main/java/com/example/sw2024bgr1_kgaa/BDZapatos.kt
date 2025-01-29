package com.example.sw2024bgr1_kgaa

class BDZapatos {
    companion object {
        val arregloZapatos = arrayListOf<Zapato>()

        init {
            // Agregar datos iniciales de los zapatos
            arregloZapatos.add(Zapato(1, "Nike", 42, "Negro", 59.99,1))
            arregloZapatos.add(Zapato(2, "Adidas", 40, "Blanco", 49.99,2))
            arregloZapatos.add(Zapato(3, "Puma", 43, "Rojo", 69.99,3))

        }
        //CRUD
        // Obtener los zapatos asociados a una tienda
        fun obtenerZapatosPorTienda(idTienda: Int): MutableList<Zapato> {
            return arregloZapatos.filter { it.idTienda == idTienda }.toMutableList()
        }


        // Generar un nuevo ID para un zapato
        fun generarIdZapato(): Int {
            return if (arregloZapatos.isEmpty()) 1 else arregloZapatos.maxOf { it.id } + 1
        }

        // Añadir un zapato al listado
        fun anadirZapato() {
            arregloZapatos.add(Zapato(4, "Adidas", 39, "Blanco", 69.99,1))
        }

        // Eliminar un zapato
        fun eliminarZapato(idZapato: Int): Zapato? {
            val zapato = arregloZapatos.find { it.id == idZapato }
            zapato?.let {
                arregloZapatos.remove(it)
            }
            return zapato
        }

        // Editar un zapato
        fun editarZapato(idZapato: Int, nuevaMarca: String, nuevaTalla: Int, nuevoPrecio: Double): Boolean {
            val zapato = arregloZapatos.find { it.id == idZapato }
            zapato?.let {
                it.marca = nuevaMarca
                it.talla = nuevaTalla
                it.precio = nuevoPrecio
                return true
            }
            return false
        }
    }
}
