package com.example.sw2024bgr1_kgaa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapa: GoogleMap
    private var latitud: Double? = null
    private var longitud: Double? = null
    private var nombreTienda: String? = null
    private var seleccionandoUbicacion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        // Obtener datos del intent
        latitud = intent.getDoubleExtra("latitud", 0.0)
        longitud = intent.getDoubleExtra("longitud", 0.0)
        nombreTienda = intent.getStringExtra("nombre")
        seleccionandoUbicacion = intent.getBooleanExtra("seleccionar", false)

        // Inicializar el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap

        if (seleccionandoUbicacion) {
            // Permitir que el usuario seleccione una ubicación
            mapa.setOnMapClickListener { ubicacion ->
                mapa.clear()
                mapa.addMarker(MarkerOptions().position(ubicacion).title("Ubicación seleccionada"))
                latitud = ubicacion.latitude
                longitud = ubicacion.longitude

                // Devolver la ubicación al formulario de añadir tienda
                val resultadoIntent = Intent()
                resultadoIntent.putExtra("latitud", latitud)
                resultadoIntent.putExtra("longitud", longitud)
                setResult(RESULT_OK, resultadoIntent)
                finish()
            }
        } else {
            // Mostrar la ubicación guardada de la tienda
            if (latitud != null && longitud != null) {
                val ubicacion = LatLng(latitud!!, longitud!!)
                mapa.addMarker(MarkerOptions().position(ubicacion).title(nombreTienda))
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
            }
        }
    }
}
