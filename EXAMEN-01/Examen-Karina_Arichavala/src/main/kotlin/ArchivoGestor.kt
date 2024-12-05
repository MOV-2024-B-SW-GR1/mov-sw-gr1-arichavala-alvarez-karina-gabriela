import java.io.File

object ArchivoGestor {

    private const val ARCHIVO_TIENDAS = "tiendas.txt"
    private const val ARCHIVO_ZAPATOS = "zapatos.txt"

    // Funciones para manejar las tiendas
    fun cargarTiendas(): List<Tienda> {
        val archivo = File(ARCHIVO_TIENDAS)
        if (!archivo.exists()) {
            println("Archivo de tiendas no encontrado. Se creará uno nuevo al guardar datos.")
            return emptyList()
        }

        return archivo.readLines().mapNotNull { linea ->
            val datos = linea.split(",")
            if (datos.size == 5) {
                try {
                    Tienda(
                        id = datos[0].toInt(),
                        nombre = datos[1],
                        ubicacion = datos[2],
                        activa = datos[3].toBoolean(),
                        administrador = datos[4]
                    )
                } catch (e: Exception) {
                    println("Línea inválida en el archivo de tiendas: $linea")
                    null
                }
            } else {
                println("Línea inválida en el archivo de tiendas: $linea")
                null
            }
        }
    }

    fun guardarTiendas(tiendas: List<Tienda>) {
        val archivo = File(ARCHIVO_TIENDAS)
        archivo.printWriter().use { writer ->
            tiendas.forEach { tienda ->
                writer.println("${tienda.obtenerId()},${tienda.obtenerNombre()},${tienda.obtenerUbicacion()},${tienda.obtenerEstadoActivo()},${tienda.obtenerAdministrador()}")
            }
        }
        println("Datos de tiendas guardados correctamente.")
    }

    // Funciones para manejar los zapatos
    fun cargarZapatos(): List<Zapato> {
        val archivo = File(ARCHIVO_ZAPATOS)
        if (!archivo.exists()) {
            println("Archivo de zapatos no encontrado. Se creará uno nuevo al guardar datos.")
            return emptyList()
        }

        return archivo.readLines().mapNotNull { linea ->
            val datos = linea.split(",")
            if (datos.size == 6) {
                try {
                    Zapato(
                        id = datos[0].toInt(),
                        marca = datos[1],
                        modelo = datos[2],
                        talla = datos[3].toDouble(),
                        precio = datos[4].toDouble(),
                        idTienda = datos[5].toInt()
                    )
                } catch (e: Exception) {
                    println("Línea inválida en el archivo de zapatos: $linea")
                    null
                }
            } else {
                println("Línea inválida en el archivo de zapatos: $linea")
                null
            }
        }
    }

    fun guardarZapatos(zapatos: List<Zapato>) {
        val archivo = File(ARCHIVO_ZAPATOS)
        archivo.printWriter().use { writer ->
            zapatos.forEach { zapato ->
                writer.println("${zapato.obtenerId()},${zapato.obtenerMarca()},${zapato.obtenerModelo()},${zapato.obtenerTalla()},${zapato.obtenerPrecio()},${zapato.obtenerIdTienda()}")
            }
        }
        println("Datos de zapatos guardados correctamente.")
    }
}
