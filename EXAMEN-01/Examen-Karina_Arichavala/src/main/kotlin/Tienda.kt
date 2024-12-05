class Tienda(
    private val id: Int,            // Identificador único (inmutable)
    private var nombre: String,   // Nombre de la tienda (mutable y protegido)
    private var ubicacion: String,// Dirección o ubicación (mutable y protegida)
    private var activa: Boolean,    // Estado de la tienda (mutable y privado)
    private var administrador: String // Nombre del administrador (mutable y privado)
) {
    init {
        this.id
        this.nombre
        this.ubicacion
        this.activa
        this.administrador

        if (this.id <= 0) {
            throw IllegalArgumentException("El ID debe ser positivo.")
        }
        if (this.nombre.isBlank()) {
            throw IllegalArgumentException("El nombre no puede estar vacío.")
        }
        if (this.ubicacion.isBlank()) {
            throw IllegalArgumentException("La ubicación no puede estar vacía.")
        }
    }

    // Métodos manuales para acceder y modificar los atributos privados
    fun obtenerId(): Int = id

    fun obtenerNombre(): String = nombre
    fun actualizarNombre(nuevoNombre: String) {
        require(nuevoNombre.isNotBlank()) { "El nombre no puede estar vacío." }
        nombre = nuevoNombre
    }

    fun obtenerUbicacion(): String = ubicacion
    fun actualizarUbicacion(nuevaUbicacion: String) {
        require(nuevaUbicacion.isNotBlank()) { "La ubicación no puede estar vacía." }
        ubicacion = nuevaUbicacion
    }

    fun obtenerEstadoActivo(): Boolean = activa
    fun actualizarEstadoActivo(nuevoEstado: Boolean) {
        activa = nuevoEstado
    }

    fun obtenerAdministrador(): String = administrador
    fun actualizarAdministrador(nuevoAdministrador: String) {
        require(nuevoAdministrador.isNotBlank()) { "El administrador no puede estar vacío." }
        administrador = nuevoAdministrador
    }

    companion object {
        private val tiendas = ArrayList<Tienda>()

        // Método para buscar una tienda por ID
        fun findTiendaById(id: Int): Tienda? {
            return tiendas.find { it.obtenerId() == id }
        }

        // Método renombrado de createTienda a agregarTienda
        fun agregarTienda(tienda: Tienda) {
            tiendas.add(tienda)
            println("Tienda agregada: $tienda")
        }

        fun readTiendas() {
            if (tiendas.isEmpty()) {
                println("No hay tiendas registradas.")
            } else {
                println("Lista de tiendas:")
                tiendas.forEach { println(it) }
            }
        }

        fun deleteTienda(id: Int) {
            val eliminado = tiendas.removeIf { it.obtenerId() == id }
            if (eliminado) {
                println("Tienda con ID $id eliminada.")
            } else {
                println("Tienda con ID $id no encontrada.")
            }
        }
    }

    override fun toString(): String {
        return "Tienda(id: $id, nombre: '$nombre', ubicacion: '$ubicacion', activa: $activa, administrador: '$administrador')"
    }
}
