# Aplicativo de Gestión de Tiendas y Zapatos

## Autor
**Nombre: **Karina Arichavala

## Asignatura
**Asignatura:** Aplicaciones Móviles

## Descripción
Este proyecto es un aplicativo de terminal desarrollado en Kotlin que permite gestionar información sobre tiendas y los zapatos que estas venden. El programa realiza operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y almacena los datos de manera persistente en archivos de texto.

## Funcionalidades
### Gestión de Tiendas
- **Agregar Tienda:** Registrar una nueva tienda con su información (ID, nombre, ubicación, estado, administrador).
- **Leer Tiendas:** Ver la lista completa de tiendas registradas.
- **Actualizar Tienda:** Modificar atributos de una tienda existente (nombre, ubicación, estado o administrador).
- **Eliminar Tienda:** Eliminar una tienda utilizando su ID.

### Gestión de Zapatos
- **Agregar Zapato:** Registrar un zapato asociado a una tienda específica (ID, marca, modelo, talla, precio, ID de tienda).
- **Leer Zapatos:** Ver la lista completa de zapatos registrados.
- **Actualizar Zapato:** Modificar atributos de un zapato existente (marca, modelo, talla, precio o tienda asociada).
- **Eliminar Zapato:** Eliminar un zapato utilizando su ID.

## Persistencia de Datos
- Los datos de tiendas se almacenan en el archivo `tiendas.txt`.
- Los datos de zapatos se almacenan en el archivo `zapatos.txt`.
- Al iniciar el programa, los datos se cargan automáticamente desde estos archivos.
- Los cambios realizados en el programa se guardan automáticamente en los archivos correspondientes.
