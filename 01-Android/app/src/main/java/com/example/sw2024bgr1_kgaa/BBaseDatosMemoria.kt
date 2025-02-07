package com.example.sw2024bgr1_kgaa

class BBaseDatosMemoria {

    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1,"Adrian","a@a.com"))
            arregloBEntrenador.add(BEntrenador(2,"Vicente","b@b.com"))
            arregloBEntrenador.add(BEntrenador(3,"Carolina","c@c.com"))
        }
    }
}




/////

/*
fun abrirDialogo(posicion: Int, adaptador: ArrayAdapter<Tienda>) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("¿Qué desea hacer?")
    builder.setMessage("Seleccione una opción para la tienda.")

    // Botón para eliminar
    builder.setPositiveButton("Eliminar") { _, _ ->
        eliminarTiendaDesdeActividad(posicion)
    }

    // Botón para editar
    builder.setNeutralButton("Editar") { _, _ ->
        abrirDialogoEditar(posicion, adaptador) // Llama al método para editar
    }

    // Botón para cancelar
    builder.setNegativeButton("Cancelar", null)

    builder.show()
}
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
        registerForContextMenu(listView) // Registrar menú contextual

        // Botón para añadir un zapato
        val botonAnadirZapato = findViewById<Button>(R.id.btn_anadir_zapatos)
        botonAnadirZapato.setOnClickListener { anadirZapato() }
    }

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
            else -> super.onContextItemSelected(item)
        }
    }

    private fun anadirZapato() {
        val nuevoZapato = Zapato(
            id = BDZapatos.generarIdZapato(),
            marca = "Nueva Marca",
            talla = 40,
            precio = 49.99,
            idTienda = idTienda
        )
        BDZapatos.anadirZapato(nuevoZapato)
        arregloZapatos.add(nuevoZapato)
        adaptador.notifyDataSetChanged()
        Snackbar.make(
            findViewById(R.id.main),
            "Zapato añadido correctamente",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun abrirDialogoEliminar(posicion: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar este zapato?")
        builder.setMessage("Esta acción no se puede deshacer.")
        builder.setPositiveButton("Aceptar") { _, _ ->
            val zapatoEliminado = BDZapatos.eliminarZapato(arregloZapatos[posicion].id)
            if (zapatoEliminado != null) {
                arregloZapatos.removeAt(posicion)
                adaptador.notifyDataSetChanged()
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
                zapatoAEditar.marca = nuevaMarca
                zapatoAEditar.talla = nuevaTalla
                zapatoAEditar.precio = nuevoPrecio
                adaptador.notifyDataSetChanged()
                Snackbar.make(
                    findViewById(R.id.main),
                    "Zapato actualizado correctamente",
                    Snackbar.LENGTH_SHORT
                ).show()
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

//

class BDZapatos {
    companion object {
        val arregloZapatos = arrayListOf<Zapato>()

        init {
            // Agregar datos iniciales de los zapatos
            arregloZapatos.add(Zapato(id = 1, marca = "Nike", talla = 42, color = "Negro", precio = 59.99))
            arregloZapatos.add(Zapato(id = 2, marca = "Adidas", talla = 40, color = "Blanco", precio = 49.99))
            arregloZapatos.add(Zapato(id = 3, marca = "Puma", talla = 43, color = "Rojo", precio = 69.99))
        }

        // Obtener los zapatos asociados a una tienda
        fun obtenerZapatosPorTienda(idTienda: Int): MutableList<Zapato> {
            return arregloZapatos.filter { it.idTienda == idTienda }.toMutableList()
        }

        // Generar un nuevo ID para un zapato
        fun generarIdZapato(): Int {
            return if (arregloZapatos.isEmpty()) 1 else arregloZapatos.maxOf { it.id } + 1
        }

        // Añadir un zapato al listado
        fun anadirZapato(zapato: Zapato) {
            arregloZapatos.add(zapato)
        }

        // Eliminar un zapato
        fun eliminarZapato(idZapato: Int): Zapato? {
            val zapato = arregloZapatos.find { it.id == idZapato }
            zapato?.let {
                arregloZapatos.remove(it)
            }
            return zapato
        }

        // Editar un zapato
        fun editarZapato(idZapato: Int, nuevaMarca: String, nuevaTalla: Int, nuevoPrecio: Double): Boolean {
            val zapato = arregloZapatos.find { it.id == idZapato }
            zapato?.let {
                it.marca = nuevaMarca
                it.talla = nuevaTalla
                it.precio = nuevoPrecio
                return true
            }
            return false
        }
    }
}

 */

