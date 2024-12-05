class Zapato(
    private val id: Int,         // Identificador único (acceso privado)
    private var marca: String, // Marca del zapato (mutable y protegida)
    private var modelo: String,// Modelo del zapato (mutable y protegida)
    private var talla: Double,   // Talla del zapato (mutable y privada)
    private var precio: Double,  // Precio del zapato (mutable y privado)
    private var idTienda: Int            // ID de la tienda asociada (público)
) {
    init {
        this.id
        this.marca
        this.modelo
        this.talla
        this.precio
        this.idTienda

        if (this.id <= 0) {
            throw IllegalArgumentException("El ID debe ser positivo.")
        }
        if (this.talla <= 0) {
            throw IllegalArgumentException("La talla debe ser un valor positivo.")
        }
        if (this.precio < 0) {
            throw IllegalArgumentException("El precio no puede ser negativo.")
        }
    }

    // Métodos para el atributo id
    fun obtenerId(): Int = id

    // Métodos para el atributo marca
    fun obtenerMarca(): String = marca
    fun actualizarMarca(nuevaMarca: String) {
        require(nuevaMarca.isNotBlank()) { "La marca no puede estar vacía." }
        marca = nuevaMarca
    }

    // Métodos para el atributo modelo
    fun obtenerModelo(): String = modelo
    fun actualizarModelo(nuevoModelo: String) {
        require(nuevoModelo.isNotBlank()) { "El modelo no puede estar vacío." }
        modelo = nuevoModelo
    }

    // Métodos para el atributo talla
    fun obtenerTalla(): Double = talla
    fun actualizarTalla(nuevaTalla: Double) {
        require(nuevaTalla > 0) { "La talla debe ser mayor a 0." }
        talla = nuevaTalla
    }

    // Métodos para el atributo precio
    fun obtenerPrecio(): Double = precio
    fun actualizarPrecio(nuevoPrecio: Double) {
        require(nuevoPrecio >= 0) { "El precio no puede ser negativo." }
        precio = nuevoPrecio
    }

    // Métodos para el atributo idTienda (es público y no necesita setters)
    fun obtenerIdTienda(): Int = idTienda

    fun actualizarIdTienda(nuevoIdTienda: Int) {
        require(nuevoIdTienda > 0) { "El ID de la tienda debe ser positivo." }
        idTienda = nuevoIdTienda
    }


    companion object {
        private val zapatos = ArrayList<Zapato>()

        fun findZapatoById(id: Int): Zapato? {
            return zapatos.find { it.obtenerId() == id }
        }

        fun agregarZapato(zapato: Zapato) {
            zapatos.add(zapato)
            println("Zapato agregado: $zapato")
        }

        fun readZapatos() {
            if (zapatos.isEmpty()) {
                println("No hay zapatos registrados.")
            } else {
                println("Lista de zapatos:")
                zapatos.forEach { println(it) }
            }
        }

        fun deleteZapato(id: Int) {
            val eliminado = zapatos.removeIf { it.id == id }
            if (eliminado) {
                println("Zapato con ID $id eliminado.")
            } else {
                println("Zapato con ID $id no encontrado.")
            }
        }
    }

    override fun toString(): String {
        return "Zapato(id: $id, marca: '$marca', modelo: '$modelo', talla: $talla, precio: $precio, id Tienda asociada: $idTienda)"
    }
}
