package com.example.sw2024bgr1_kgaa

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ListaTiendas : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Tienda>
    lateinit var listaTiendas: MutableList<Tienda>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_tiendas)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)

        // Obtener tiendas de la base de datos
        listaTiendas = EBaseDeDatos.tablaBD?.obtenerTiendas()?.toMutableList() ?: mutableListOf()

        // Configurar adaptador
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaTiendas)
        listView.adapter = adaptador

        // Botón para añadir tienda
        botonAnadirListView.setOnClickListener { anadirTienda() }

        // Registrar menú contextual
        registerForContextMenu(listView)
    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu2, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar2 -> {
                abrirDialogoEditar(posicionItemSeleccionado)
                true
            }
            R.id.mi_eliminar2 -> {
                abrirDialogoEliminar(posicionItemSeleccionado)
                true
            }
            R.id.ver_zapatos -> {
                irListaZapatos(posicionItemSeleccionado)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.main), texto, Snackbar.LENGTH_SHORT).show()
    }


    //1

    fun abrirDialogoEditar(posicion: Int) {
        val tiendaAEditar = listaTiendas[posicion]
        val vista = layoutInflater.inflate(R.layout.dialogo_editar_tienda, null)
        val editNombre = vista.findViewById<EditText>(R.id.edit_nombre)
        val editDueno = vista.findViewById<EditText>(R.id.edit_dueno)
        val editUbicacion = vista.findViewById<EditText>(R.id.edit_ubicacion)

        // Precargar datos actuales
        editNombre.setText(tiendaAEditar.nombre)
        editDueno.setText(tiendaAEditar.dueno)
        editUbicacion.setText(tiendaAEditar.ubicacion)

        AlertDialog.Builder(this)
            .setTitle("Editar Tienda")
            .setView(vista)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoNombre = editNombre.text.toString()
                val nuevoDueno = editDueno.text.toString()
                val nuevaUbicacion = editUbicacion.text.toString()

                if (nuevoNombre.isNotEmpty() && nuevoDueno.isNotEmpty() && nuevaUbicacion.isNotEmpty()) {
                    val resultado = EBaseDeDatos.tablaBD?.actualizarTienda(
                        tiendaAEditar.id, nuevoNombre, nuevoDueno, nuevaUbicacion
                    )
                    if (resultado == true) {
                        tiendaAEditar.nombre = nuevoNombre
                        tiendaAEditar.dueno = nuevoDueno
                        tiendaAEditar.ubicacion = nuevaUbicacion
                        adaptador.notifyDataSetChanged()
                        mostrarSnackbar("Tienda actualizada")
                    } else {
                        mostrarSnackbar("Error al actualizar")
                    }
                } else {
                    mostrarSnackbar("Todos los campos son obligatorios")
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    //2
    fun abrirDialogoEliminar(posicion: Int) {
        val tiendaAEliminar = listaTiendas[posicion]
        AlertDialog.Builder(this)
            .setTitle("¿Eliminar tienda?")
            .setMessage("Esta acción no se puede deshacer.")
            .setPositiveButton("Aceptar") { _, _ ->
                val resultado = EBaseDeDatos.tablaBD?.eliminarTienda(tiendaAEliminar.id)
                if (resultado == true) {
                    listaTiendas.removeAt(posicion)
                    adaptador.notifyDataSetChanged()
                    mostrarSnackbar("Tienda eliminada")
                } else {
                    mostrarSnackbar("Error al eliminar")
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    //3
    fun irListaZapatos(posicion: Int) {
        val tiendaSeleccionada = listaTiendas[posicion]

        val intent = Intent(this, ListaZapatos::class.java)
        intent.putExtra("tienda_id", tiendaSeleccionada.id)
        intent.putExtra("tienda_nombre", tiendaSeleccionada.nombre)
        startActivity(intent)
    }

    //Añadir
    fun anadirTienda() {
        val vista = layoutInflater.inflate(R.layout.dialogo_anadir_tienda, null)
        val editNombre = vista.findViewById<EditText>(R.id.anadir_nombre)
        val editDueno = vista.findViewById<EditText>(R.id.anadir_dueno)
        val editUbicacion = vista.findViewById<EditText>(R.id.anadir_ubicacion)

        AlertDialog.Builder(this)
            .setTitle("Añadir Tienda")
            .setView(vista)
            .setPositiveButton("Agregar") { _, _ ->
                val nombre = editNombre.text.toString()
                val dueno = editDueno.text.toString()
                val ubicacion = editUbicacion.text.toString()

                if (nombre.isNotEmpty() && dueno.isNotEmpty() && ubicacion.isNotEmpty()) {
                    val resultado = EBaseDeDatos.tablaBD?.crearTienda(nombre, dueno, ubicacion)
                    if (resultado == true) {
                        listaTiendas = EBaseDeDatos.tablaBD?.obtenerTiendas()?.toMutableList() ?: mutableListOf()
                        adaptador.clear()
                        adaptador.addAll(listaTiendas)
                        adaptador.notifyDataSetChanged()
                        mostrarSnackbar("Tienda añadida")
                    } else {
                        mostrarSnackbar("Error al añadir")
                    }
                } else {
                    mostrarSnackbar("Todos los campos son obligatorios")
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


}
