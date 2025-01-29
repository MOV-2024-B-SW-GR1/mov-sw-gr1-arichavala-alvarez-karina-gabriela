package com.example.sw2024bgr1_kgaa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//

import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import com.example.sw2024bgr1_kgaa.BDZapatos.Companion.anadirZapato
import com.google.android.material.snackbar.Snackbar
//

class ListaZapatos : AppCompatActivity() {

    private var arregloZapatos = mutableListOf<Zapato>()
    private lateinit var adaptador: ArrayAdapter<Zapato>
    private var posicionItemSeleccionado = -1
    private var nombreTienda = ""
    private var idTienda = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_zapatos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //

        // Recuperar datos de la tienda
        nombreTienda = intent.getStringExtra("tienda_nombre") ?: "Tienda desconocida"
        idTienda = intent.getIntExtra("tienda_id", -1)

        // Mostrar el nombre de la tienda
        val editNombreTienda = findViewById<EditText>(R.id.id_nombre_tienda)
        editNombreTienda.setText(nombreTienda)

        // Configurar la lista de zapatos
        arregloZapatos = BDZapatos.obtenerZapatosPorTienda(idTienda)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloZapatos
        )

        val listView = findViewById<ListView>(R.id.zapatos_listView)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() // Actualiza la lista si es necesario
        registerForContextMenu(listView) // Registrar menú contextual

        // Botón para añadir un zapato
        val botonAnadirZapato = findViewById<Button>(R.id.btn_anadir_zapatos)
        botonAnadirZapato.setOnClickListener {
            BDZapatos.anadirZapato() // Llama directamente a la función en BDZapatos
            actualizarLista()
        }

    } //finish on create

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
        arregloZapatos = BDZapatos.obtenerZapatosPorTienda(idTienda)
        adaptador.clear()
        adaptador.addAll(arregloZapatos)
        adaptador.notifyDataSetChanged()
    }

    private fun abrirDialogoEliminar(posicion: Int) {
        val zapatoId = arregloZapatos[posicion].id
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar este zapato?")
        builder.setMessage("Esta acción no se puede deshacer.")
        builder.setPositiveButton("Aceptar") { _, _ ->
            if (BDZapatos.eliminarZapato(zapatoId) != null) {
                actualizarLista()
                Snackbar.make(
                    findViewById(R.id.main),
                    "Zapato eliminado correctamente",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    findViewById(R.id.main),
                    "Error al eliminar el zapato",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun abrirDialogoEditar(posicion: Int) {
        val zapatoAEditar = arregloZapatos[posicion]
        val vista = layoutInflater.inflate(R.layout.dialogo_editar_zapato, null)
        val editMarca = vista.findViewById<EditText>(R.id.edit_marca)
        val editTalla = vista.findViewById<EditText>(R.id.edit_talla)
        val editPrecio = vista.findViewById<EditText>(R.id.edit_precio)

        // Precargar datos actuales
        editMarca.setText(zapatoAEditar.marca)
        editTalla.setText(zapatoAEditar.talla.toString())
        editPrecio.setText(zapatoAEditar.precio.toString())

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Zapato")
        builder.setView(vista)
        builder.setPositiveButton("Aceptar") { _, _ ->
            val nuevaMarca = editMarca.text.toString()
            val nuevaTalla = editTalla.text.toString().toIntOrNull()
            val nuevoPrecio = editPrecio.text.toString().toDoubleOrNull()

            if (!nuevaMarca.isEmpty() && nuevaTalla != null && nuevoPrecio != null) {
                if (BDZapatos.editarZapato(zapatoAEditar.id, nuevaMarca, nuevaTalla, nuevoPrecio)) {
                    actualizarLista()
                    Snackbar.make(
                        findViewById(R.id.main),
                        "Zapato actualizado correctamente",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        findViewById(R.id.main),
                        "Error al actualizar el zapato",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                Snackbar.make(
                    findViewById(R.id.main),
                    "Todos los campos son obligatorios y deben tener valores válidos",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }


}