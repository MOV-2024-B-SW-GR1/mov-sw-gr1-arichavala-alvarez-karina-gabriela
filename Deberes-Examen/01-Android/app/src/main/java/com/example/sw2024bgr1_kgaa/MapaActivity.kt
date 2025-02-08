package com.example.sw2024bgr1_kgaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapaActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
    val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        solicitarPermisos()
        inicializarMapa()
    }

    fun solicitarPermisos() {
        if (!tengoPermisos()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    fun tengoPermisos(): Boolean {
        val contexto = applicationContext
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        return permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
    }

    fun inicializarMapa() {
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            mapa = googleMap
            establecerConfiguracionMapa()
            mostrarUbicacionTienda()
        }
    }

    @SuppressLint("MissingPermission")
    fun establecerConfiguracionMapa() {
        with(mapa) {
            if (tengoPermisos()) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun mostrarUbicacionTienda() {
        // Obtener latitud y longitud desde el intent
        val latitud = intent.getStringExtra("latitud")?.toDoubleOrNull() ?: 0.0
        val longitud = intent.getStringExtra("longitud")?.toDoubleOrNull() ?: 0.0
        val nombreTienda = intent.getStringExtra("nombre_tienda") ?: "Ubicación"

        val ubicacionTienda = LatLng(latitud, longitud)

        if (latitud != 0.0 && longitud != 0.0) {
            val marcador = anadirMarcador(ubicacionTienda, nombreTienda)
            marcador.tag = nombreTienda
            moverCamaraConZoom(ubicacionTienda)
        } else {
            mostrarSnackbar("No hay coordenadas disponibles para esta tienda")
        }
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 18f) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    fun anadirMarcador(latLng: LatLng, title: String): Marker {
        return mapa.addMarker(MarkerOptions().position(latLng).title(title))!!
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
