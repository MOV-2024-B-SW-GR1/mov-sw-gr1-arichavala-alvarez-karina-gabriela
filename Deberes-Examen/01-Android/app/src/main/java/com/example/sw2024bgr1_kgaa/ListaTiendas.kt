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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ListaTiendas : AppCompatActivity() {
    val arreglo = BDTiendas.arregloTiendas
    lateinit var adaptador: ArrayAdapter<Tienda> // Declaración global del adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_tiendas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el adaptador global
        adaptador = ArrayAdapter(
            this, // contexto
            android.R.layout.simple_list_item_1, // XML que vamos a usar
            arreglo
        )

        val listView = findViewById<ListView>(R.id.lv_list_view)
        listView.adapter = adaptador // Usa el adaptador global
        adaptador.notifyDataSetChanged() // Actualiza la lista si es necesario

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView.setOnClickListener { anadirTienda(adaptador) }

        registerForContextMenu(listView) // Registrar el menú contextual
    }


    var posicionItemSeleccionado = -1 // VARIABLE GLOBAL
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        // llenamos opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
        // obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    //09-01
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar2 -> {
                abrirDialogoEditar(posicionItemSeleccionado, adaptador) // Abrir diálogo para editar
                true
            }
            R.id.mi_eliminar2 ->{
                mostrarSnackbar("${posicionItemSeleccionado}")
                abrirDialogoEliminar(posicionItemSeleccionado)
                return true
            }
            R.id.ver_zapatos -> { // Cambiar al ID correcto
                irListaZapatos(posicionItemSeleccionado) // Ir a la lista de zapatos
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    //Parte última
    fun mostrarSnackbar(texto: String){
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    //Diálogo de eliminar
    fun abrirDialogoEliminar(posicion: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar esta tienda?")
        builder.setMessage("Esta acción no se puede deshacer.")
        builder.setPositiveButton("Aceptar") { _, _ ->
            eliminarTiendaDesdeActividad(posicion)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }


    //Dialogo de Edición
    fun abrirDialogoEditar(posicion: Int, adaptador: ArrayAdapter<Tienda>) {
        val tiendaAEditar = BDTiendas.arregloTiendas[posicion]

        // Crear vista personalizada para el diálogo
        val vista = layoutInflater.inflate(R.layout.dialogo_editar_tienda, null)
        val editNombre = vista.findViewById<EditText>(R.id.edit_nombre)
        val editDueno = vista.findViewById<EditText>(R.id.edit_dueno)
        val editUbicacion = vista.findViewById<EditText>(R.id.edit_ubicacion)

        // Precargar datos actuales
        editNombre.setText(tiendaAEditar.nombre)
        editDueno.setText(tiendaAEditar.dueno)
        editUbicacion.setText(tiendaAEditar.ubicacion)

        // Crear el cuadro de diálogo
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Tienda")
        builder.setView(vista)

        // Botón "Aceptar" para guardar los cambios
        builder.setPositiveButton("Aceptar") { _, _ ->
            val nuevoNombre = editNombre.text.toString()
            val nuevoDueno = editDueno.text.toString()
            val nuevaUbicacion = editUbicacion.text.toString()

            if (nuevoNombre.isNotEmpty() && nuevoDueno.isNotEmpty() && nuevaUbicacion.isNotEmpty()) {
                // Actualizar datos de la tienda
                tiendaAEditar.nombre = nuevoNombre
                tiendaAEditar.dueno = nuevoDueno
                tiendaAEditar.ubicacion = nuevaUbicacion

                // Actualizar el adaptador
                adaptador.notifyDataSetChanged()

                Snackbar.make(
                    findViewById(R.id.main),
                    "Tienda '${tiendaAEditar.nombre}' actualizada correctamente",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    findViewById(R.id.main),
                    "Todos los campos son obligatorios",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        // Botón "Cancelar" para cerrar el diálogo
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }



    //MANIPULACION DE DATOS


    fun anadirTienda(adaptador: ArrayAdapter<Tienda>){
        arreglo.add(Tienda(8, "Tiendas del Sur", "Laura Fernández", "Av. Sur 456"))
        adaptador.notifyDataSetChanged()
    }

    // Eliminar
    fun eliminarTiendaDesdeActividad(posicion: Int) {
        val tiendaEliminada = BDTiendas.eliminarTienda(posicion)
        if (tiendaEliminada != null) {
            // Actualizar el adaptador del ListView
            val listView = findViewById<ListView>(R.id.lv_list_view)
            val adaptador = listView.adapter as ArrayAdapter<Tienda>
            adaptador.notifyDataSetChanged()

            // Mostrar mensaje de confirmación
            Snackbar.make(
                findViewById(R.id.main),
                "La tienda '${tiendaEliminada.nombre}' ha sido eliminada",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            Snackbar.make(
                findViewById(R.id.main),
                "Error: No se pudo eliminar la tienda",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun irListaZapatos(posicion: Int) {
        val tiendaSeleccionada = BDTiendas.arregloTiendas[posicion]

        // Crear un Intent para abrir la actividad ListaZapatos
        val intent = Intent(this, ListaZapatos::class.java)

        // Pasar datos de la tienda seleccionada
        intent.putExtra("tienda_id", tiendaSeleccionada.id)
        intent.putExtra("tienda_nombre", tiendaSeleccionada.nombre)

        // Iniciar la actividad
        startActivity(intent)
    }


}