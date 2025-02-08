package com.example.sw2024bgr1_kgaa

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ListaZapatos : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Zapato>
    lateinit var listaZapatos: MutableList<Zapato>
    var idTienda: Int = -1
    var nombreTienda: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_zapatos)

        val listView = findViewById<ListView>(R.id.zapatos_listView)
        val botonAnadirZapato = findViewById<Button>(R.id.btn_anadir_zapatos)

        // Recuperar datos de la tienda
        nombreTienda = intent.getStringExtra("tienda_nombre") ?: "Tienda desconocida"
        idTienda = intent.getIntExtra("tienda_id", -1)

        // Mostrar el nombre de la tienda
        val editNombreTienda = findViewById<EditText>(R.id.id_nombre_tienda)
        editNombreTienda.setText(nombreTienda)

        // Obtener zapatos desde la base de datos
        listaZapatos = EBaseDeDatos.tablaBD?.obtenerZapatosPorTienda(idTienda)?.toMutableList() ?: mutableListOf()

        // Configurar adaptador
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaZapatos)
        listView.adapter = adaptador

        // Botón para añadir zapato
        botonAnadirZapato.setOnClickListener { anadirZapato() }

        // Registrar menú contextual
        registerForContextMenu(listView)
    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu1, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar1 -> {
                abrirDialogoEditar(posicionItemSeleccionado)
                true
            }
            R.id.mi_eliminar1 -> {
                abrirDialogoEliminar(posicionItemSeleccionado)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    private fun actualizarLista() {
        listaZapatos = EBaseDeDatos.tablaBD?.obtenerZapatosPorTienda(idTienda)?.toMutableList() ?: mutableListOf()
        adaptador.clear()
        adaptador.addAll(listaZapatos)
        adaptador.notifyDataSetChanged()
    }


    //1
    private fun abrirDialogoEditar(posicion: Int) {
        val zapatoAEditar = listaZapatos[posicion]
        val vista = layoutInflater.inflate(R.layout.dialogo_editar_zapato, null)
        val editMarca = vista.findViewById<EditText>(R.id.edit_marca)
        val editTalla = vista.findViewById<EditText>(R.id.edit_talla)
        val editColor = vista.findViewById<EditText>(R.id.edit_color)
        val editPrecio = vista.findViewById<EditText>(R.id.edit_precio)

        // Precargar datos actuales
        editMarca.setText(zapatoAEditar.marca)
        editTalla.setText(zapatoAEditar.talla.toString())
        editColor.setText(zapatoAEditar.color)
        editPrecio.setText(zapatoAEditar.precio.toString())

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Zapato")
        builder.setView(vista)
        builder.setPositiveButton("Aceptar") { _, _ ->
            val nuevaMarca = editMarca.text.toString()
            val nuevaTalla = editTalla.text.toString().toIntOrNull()
            val nuevoColor = editColor.text.toString()
            val nuevoPrecio = editPrecio.text.toString().toDoubleOrNull()

            if (!nuevaMarca.isEmpty() && nuevaTalla != null && !nuevoColor.isEmpty() && nuevoPrecio != null) {
                val resultado = EBaseDeDatos.tablaBD?.actualizarZapato(
                    zapatoAEditar.id, nuevaMarca, nuevaTalla, nuevoColor, nuevoPrecio
                )
                if (resultado == true) {
                    actualizarLista()
                    mostrarSnackbar("Zapato actualizado correctamente")
                } else {
                    mostrarSnackbar("Error al actualizar el zapato")
                }
            } else {
                mostrarSnackbar("Todos los campos son obligatorios y deben tener valores válidos")
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    //2
    private fun abrirDialogoEliminar(posicion: Int) {
        val zapatoAEliminar = listaZapatos[posicion]
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar este zapato?")
        builder.setMessage("Esta acción no se puede deshacer.")
        builder.setPositiveButton("Aceptar") { _, _ ->
            val resultado = EBaseDeDatos.tablaBD?.eliminarZapato(zapatoAEliminar.id)
            if (resultado == true) {
                actualizarLista()
                mostrarSnackbar("Zapato eliminado correctamente")
            } else {
                mostrarSnackbar("Error al eliminar el zapato")
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    //Añadir
    private fun anadirZapato() {
        val vista = layoutInflater.inflate(R.layout.dialogo_anadir_zapato, null)
        val editMarca = vista.findViewById<EditText>(R.id.anadir_marca)
        val editTalla = vista.findViewById<EditText>(R.id.anadir_talla)
        val editColor = vista.findViewById<EditText>(R.id.anadir_color)
        val editPrecio = vista.findViewById<EditText>(R.id.anadir_precio)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Añadir Zapato")
        builder.setView(vista)
        builder.setPositiveButton("Agregar") { _, _ ->
            val marca = editMarca.text.toString()
            val talla = editTalla.text.toString().toIntOrNull()
            val color = editColor.text.toString()
            val precio = editPrecio.text.toString().toDoubleOrNull()

            if (marca.isNotEmpty() && talla != null && color.isNotEmpty() && precio != null) {
                val resultado = EBaseDeDatos.tablaBD?.crearZapato(marca, talla, color, precio, idTienda)
                if (resultado == true) {
                    actualizarLista()
                    mostrarSnackbar("Zapato añadido")
                } else {
                    mostrarSnackbar("Error al añadir")
                }
            } else {
                mostrarSnackbar("Todos los campos son obligatorios")
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.main), texto, Snackbar.LENGTH_SHORT).show()
    }
}
