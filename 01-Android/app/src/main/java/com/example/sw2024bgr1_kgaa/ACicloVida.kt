package com.example.sw2024bgr1_kgaa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mostrarSnackbar("onCreate")
    }

    override fun onStart(){
        super.onStart()
        mostrarSnackbar("OnStart")
    }

    override fun onResume(){
        super.onResume()
        mostrarSnackbar("OnResume")
    }

    override fun onRestart(){
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onPause(){
        super.onPause()
        mostrarSnackbar("onPause")
    }

    override fun onStop(){
        super.onStop()
        mostrarSnackbar("onStop")
    }



    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            putString("variableTextoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Recuperar las variables
        val textoRecuperado: String? = savedInstanceState
            .getString("variableTextoGuardado")
        if(textoRecuperado != null){
            // textoGlobal = textoRecuperado
            mostrarSnackbar(textoRecuperado) // ya guarda el texto global
        }
    }


    var textoGlobal = ""
    fun mostrarSnackbar(text: String){
        textoGlobal += text
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE

        )
        snack.show()

    }
}