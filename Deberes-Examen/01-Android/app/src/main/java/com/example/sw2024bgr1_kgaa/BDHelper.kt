package com.example.sw2024bgr1_kgaa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDHelper(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "TiendaZapatosDB", // Nombre de la base de datos
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla Tiendas con dueño y ubicación
        val scriptCrearTablaTiendas = """
            CREATE TABLE Tiendas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                dueno TEXT NOT NULL,
                ubicacion TEXT NOT NULL,
                coordenadas TEXT
            ) 
        """.trimIndent()
        db?.execSQL(scriptCrearTablaTiendas)

        // Crear tabla Zapatos con idTienda como clave foránea
        val scriptCrearTablaZapatos = """
            CREATE TABLE Zapatos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                marca TEXT NOT NULL,
                talla INTEGER NOT NULL,
                color TEXT NOT NULL,
                precio REAL NOT NULL,
                idTienda INTEGER NOT NULL,
                FOREIGN KEY (idTienda) REFERENCES Tiendas(id) ON DELETE CASCADE
            )
        """.trimIndent()
        db?.execSQL(scriptCrearTablaZapatos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Zapatos")
        db?.execSQL("DROP TABLE IF EXISTS Tiendas")
        onCreate(db)
    }

    // ---------------- CRUD PARA TIENDAS ----------------

    fun crearTienda(nombre: String, dueno: String, ubicacion: String, coordenadas: String): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("dueno", dueno)
            put("ubicacion", ubicacion)
            put("coordenadas", coordenadas) // NUEVO CAMPO
        }
        val resultado = db.insert("Tiendas", null, valores)
        db.close()
        return resultado.toInt() != -1
    }


    fun eliminarTienda(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete("Tiendas", "id=?", arrayOf(id.toString()))
        db.close()
        return resultado != -1
    }



    fun actualizarTienda(id: Int, nuevoNombre: String, nuevoDueno: String, nuevaUbicacion: String, nuevasCoordenadas: String): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nuevoNombre)
            put("dueno", nuevoDueno)
            put("ubicacion", nuevaUbicacion)
            put("coordenadas", nuevasCoordenadas) // NUEVO CAMPO
        }
        val resultado = db.update("Tiendas", valores, "id=?", arrayOf(id.toString()))
        db.close()
        return resultado != -1
    }


    fun consultarTiendaPorId(id: Int): Tienda? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Tiendas WHERE id = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val tienda = Tienda(
                cursor.getInt(0), // id
                cursor.getString(1), // nombre
                cursor.getString(2), // dueño
                cursor.getString(3), // ubicación
                cursor.getString(4)  // NUEVO: coordenadas
            )
            cursor.close()
            db.close()
            tienda
        } else {
            cursor.close()
            db.close()
            null
        }
    }


    fun obtenerTiendas(): List<Tienda> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Tiendas", null)
        val listaTiendas = mutableListOf<Tienda>()

        if (cursor.moveToFirst()) {
            do {
                val tienda = Tienda(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)  // NUEVO: coordenadas
                )
                listaTiendas.add(tienda)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaTiendas
    }


    // ---------------- CRUD PARA ZAPATOS ----------------

    fun crearZapato(marca: String, talla: Int, color: String, precio: Double, idTienda: Int): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("marca", marca)
            put("talla", talla)
            put("color", color)
            put("precio", precio)
            put("idTienda", idTienda)
        }
        val resultado = db.insert("Zapatos", null, valores)
        db.close()
        return resultado.toInt() != -1
    }

    fun eliminarZapato(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete("Zapatos", "id=?", arrayOf(id.toString()))
        db.close()
        return resultado != -1
    }

    fun actualizarZapato(id: Int, nuevaMarca: String, nuevaTalla: Int, nuevoColor: String, nuevoPrecio: Double): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("marca", nuevaMarca)
            put("talla", nuevaTalla)
            put("color", nuevoColor)
            put("precio", nuevoPrecio)
        }
        val resultado = db.update("Zapatos", valores, "id=?", arrayOf(id.toString()))
        db.close()
        return resultado != -1
    }

    fun obtenerZapatosPorTienda(idTienda: Int): List<Zapato> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Zapatos WHERE idTienda=?", arrayOf(idTienda.toString()))
        val listaZapatos = mutableListOf<Zapato>()

        if (cursor.moveToFirst()) {
            do {
                val zapato = Zapato(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getInt(5)
                )
                listaZapatos.add(zapato)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaZapatos
    }
}
