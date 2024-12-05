import java.util.Scanner

//*************** MAIN ***************//
fun main() {

    //Archivo
    println("Cargando datos...")
    val tiendas = ArchivoGestor.cargarTiendas().toMutableList()
    val zapatos = ArchivoGestor.cargarZapatos().toMutableList()
    println("Datos cargados correctamente.")
    //

    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n=== Menú Principal ===")
        println("1. Operaciones con Tiendas")
        println("2. Operaciones con Zapatos")
        println("3. Salir")
        print("Seleccione una opción: ")

        when (scanner.nextLine().toIntOrNull()) {
            1 -> menuTiendas(scanner, tiendas)
            2 -> menuZapatos(scanner, zapatos)
            3 -> {
                println("Saliendo del programa...")
                break
            }
            else -> println("Opción no válida, intente nuevamente.")
        }
    }
}

//*************** MENUS ***************//
fun menuTiendas(scanner: Scanner, tiendas: MutableList<Tienda>) {
    while (true) {
        println("\n=== Menú Tiendas ===")
        println("1. Agregar Tienda")
        println("2. Leer Tiendas")
        println("3. Actualizar Tienda")
        println("4. Eliminar Tienda")
        println("5. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (scanner.nextLine().toIntOrNull()) {
            1 -> agregarTienda(scanner, tiendas)
            2 -> leerTiendas(tiendas)
            3 -> actualizarTienda(scanner, tiendas)
            4 -> eliminarTienda(scanner, tiendas)
            5 -> return
            else -> println("Opción no válida, intente nuevamente.")
        }
    }
}


fun menuZapatos(scanner: Scanner, zapatos: MutableList<Zapato>) {
    while (true) {
        println("\n=== Menú Zapatos ===")
        println("1. Agregar Zapato")
        println("2. Leer Zapatos")
        println("3. Actualizar Zapato")
        println("4. Eliminar Zapato")
        println("5. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (scanner.nextLine().toIntOrNull()) {
            1 -> agregarZapato(scanner, zapatos)
            2 -> leerZapatos(zapatos)
            3 -> actualizarZapato(scanner, zapatos)
            4 -> eliminarZapato(scanner, zapatos)
            5 -> return
            else -> println("Opción no válida, intente nuevamente.")
        }
    }
}

//*************** ACCIONES DE TIENDA ***************//
fun agregarTienda(scanner: Scanner, tiendas: MutableList<Tienda>) {
    print("Ingrese el ID: ")
    val id = scanner.nextLine().toInt()
    print("Ingrese el nombre: ")
    val nombre = scanner.nextLine()
    print("Ingrese la ubicación: ")
    val ubicacion = scanner.nextLine()
    val activa = leerBooleano(scanner)
    print("Ingrese el administrador: ")
    val administrador = scanner.nextLine()

    val tienda = Tienda(id, nombre, ubicacion, activa, administrador)
    tiendas.add(tienda)
    ArchivoGestor.guardarTiendas(tiendas)
    println("Tienda agregada correctamente.")
}

fun leerTiendas(tiendas: List<Tienda>) {
    if (tiendas.isEmpty()) {
        println("No hay tiendas registradas.")
    } else {
        println("Lista de tiendas:")
        tiendas.forEach { println(it) }
    }
}

fun actualizarTienda(scanner: Scanner, tiendas: MutableList<Tienda>) {
    print("Ingrese el ID de la tienda a actualizar: ")
    val id = scanner.nextLine().toInt()

    // Buscar la tienda en la lista
    val tienda = tiendas.find { it.obtenerId() == id }
    if (tienda == null) {
        println("Tienda con ID $id no encontrada.")
        return
    }

    while (true) {
        println("\n=== Menú Actualizar Tienda ===")
        println("1. Actualizar Nombre")
        println("2. Actualizar Ubicación")
        println("3. Cambiar Estado (Activa/Inactiva)")
        println("4. Actualizar Administrador")
        println("5. Volver al Menú Tiendas")
        print("Seleccione una opción: ")

        when (scanner.nextLine().toIntOrNull()) {
            1 -> {
                print("Ingrese el nuevo nombre: ")
                val nuevoNombre = scanner.nextLine()
                tienda.actualizarNombre(nuevoNombre)
                println("Nombre actualizado correctamente.")
            }
            2 -> {
                print("Ingrese la nueva ubicación: ")
                val nuevaUbicacion = scanner.nextLine()
                tienda.actualizarUbicacion(nuevaUbicacion)
                println("Ubicación actualizada correctamente.")
            }
            3 -> {
                val nuevaActiva = leerBooleano(scanner)
                tienda.actualizarEstadoActivo(nuevaActiva)
                println("Estado actualizado correctamente.")
            }
            4 -> {
                print("Ingrese el nuevo administrador: ")
                val nuevoAdministrador = scanner.nextLine()
                tienda.actualizarAdministrador(nuevoAdministrador)
                println("Administrador actualizado correctamente.")
            }
            5 -> {
                println("Volviendo al menú Tiendas...")
                ArchivoGestor.guardarTiendas(tiendas) // Sincronizar cambios al archivo
                return
            }
            else -> println("Opción no válida, intente nuevamente.")
        }
    }
}


fun eliminarTienda(scanner: Scanner, tiendas: MutableList<Tienda>) {
    print("Ingrese el ID de la tienda a eliminar: ")
    val id = scanner.nextLine().toInt()

    val eliminado = tiendas.removeIf { it.obtenerId() == id }
    if (eliminado) {
        ArchivoGestor.guardarTiendas(tiendas)
        println("Tienda eliminada correctamente.")
    } else {
        println("Tienda con ID $id no encontrada.")
    }
}


// LEER BOOLEANO
fun leerBooleano(scanner: Scanner): Boolean {
    while (true) {
        print("¿Está activa? (s/n): ")
        val entrada = scanner.nextLine().trim().lowercase()
        when (entrada) {
            "s" -> return true
            "n" -> return false
            else -> println("Entrada no válida. Por favor ingrese 'S' para Sí o 'N' para No.")
        }
    }
}
//*************** ACCIONES DE ZAPATO ***************//

fun agregarZapato(scanner: Scanner, zapatos: MutableList<Zapato>) {
    print("Ingrese el ID: ")
    val id = scanner.nextLine().toInt()
    print("Ingrese la marca: ")
    val marca = scanner.nextLine()
    print("Ingrese el modelo: ")
    val modelo = scanner.nextLine()
    print("Ingrese la talla: ")
    val talla = scanner.nextLine().toDouble()
    print("Ingrese el precio: ")
    val precio = scanner.nextLine().toDouble()
    print("Ingrese el ID de la tienda asociada: ")
    val idTienda = scanner.nextLine().toInt()

    val zapato = Zapato(id, marca, modelo, talla, precio, idTienda)
    zapatos.add(zapato)
    ArchivoGestor.guardarZapatos(zapatos)
    println("Zapato agregado correctamente.")
}

fun leerZapatos(zapatos: List<Zapato>) {
    if (zapatos.isEmpty()) {
        println("No hay zapatos registrados.")
    } else {
        println("Lista de zapatos:")
        zapatos.forEach { println(it) }
    }
}

//Actualizar Zapato

fun actualizarZapato(scanner: Scanner, zapatos: MutableList<Zapato>) {
    print("Ingrese el ID del zapato a actualizar: ")
    val id = scanner.nextLine().toInt()

    // Buscar el zapato en la lista
    val zapato = zapatos.find { it.obtenerId() == id }
    if (zapato == null) {
        println("Zapato con ID $id no encontrado.")
        return
    }

    while (true) {
        println("\n=== Menú Actualizar Zapato ===")
        println("1. Actualizar Marca")
        println("2. Actualizar Modelo")
        println("3. Actualizar Talla")
        println("4. Actualizar Precio")
        println("5. Actualizar ID de la Tienda Asociada")
        println("6. Volver al Menú Zapatos")
        print("Seleccione una opción: ")

        when (scanner.nextLine().toIntOrNull()) {
            1 -> {
                print("Ingrese la nueva marca: ")
                val nuevaMarca = scanner.nextLine()
                zapato.actualizarMarca(nuevaMarca)
                println("Marca actualizada correctamente.")
            }
            2 -> {
                print("Ingrese el nuevo modelo: ")
                val nuevoModelo = scanner.nextLine()
                zapato.actualizarModelo(nuevoModelo)
                println("Modelo actualizado correctamente.")
            }
            3 -> {
                print("Ingrese la nueva talla: ")
                val nuevaTalla = scanner.nextLine().toDouble()
                zapato.actualizarTalla(nuevaTalla)
                println("Talla actualizada correctamente.")
            }
            4 -> {
                print("Ingrese el nuevo precio: ")
                val nuevoPrecio = scanner.nextLine().toDouble()
                zapato.actualizarPrecio(nuevoPrecio)
                println("Precio actualizado correctamente.")
            }
            5 -> {
                print("Ingrese el nuevo ID de la tienda asociada: ")
                val nuevoIdTienda = scanner.nextLine().toInt()
                zapato.actualizarIdTienda(nuevoIdTienda)
                println("ID de la tienda asociada actualizado correctamente.")
            }
            6 -> {
                println("Volviendo al menú Zapatos...")
                ArchivoGestor.guardarZapatos(zapatos) // Guardar lista actualizada al salir del menú
                return
            }
            else -> println("Opción no válida, intente nuevamente.")
        }

        // Guardar cambios en el archivo después de cada actualización
        ArchivoGestor.guardarZapatos(zapatos)
    }
}


fun eliminarZapato(scanner: Scanner, zapatos: MutableList<Zapato>) {
    print("Ingrese el ID del zapato a eliminar: ")
    val id = scanner.nextLine().toInt()

    val eliminado = zapatos.removeIf { it.obtenerId() == id }
    if (eliminado) {
        ArchivoGestor.guardarZapatos(zapatos)
        println("Zapato eliminado correctamente.")
    } else {
        println("Zapato con ID $id no encontrado.")
    }
}



