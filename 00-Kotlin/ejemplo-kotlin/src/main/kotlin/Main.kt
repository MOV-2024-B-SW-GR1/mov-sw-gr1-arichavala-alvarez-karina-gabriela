import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    // INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "Karina";
    // inmutable = "Vicente" // Error!
    // MUTABLES
    var mutable: String = "Gabriela"
    mutable = "Karina" // Ok
    // VAL > VAR

    // Duck Typing
    val ejemploVariable = " Karima Gabriela "
    ejemploVariable.trim()
    val edadEjemplo: Int = 12
    // ejemploVariable = edadEjemplo // Error!
    // Variables Primitivas
    val nombreProfesor: String = "Karina Gabriela"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad:Boolean = true
    // Clases en Java
    val fechaNacimiento: Date = Date()


    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito


    imprimirNombre("Karina Arichavala")
    calcularSueldo(10.00) // solo paramtro requerido
    calcularSueldo(10.00,15.00,20.00) // parametro requerido y sobreescribir parametros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00) // usando el parametro bonoEspecial en 2da posicion
    // gracias a los parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa=14.00)
    // usando el parametro bonoEspecial en 1ra posicion
    // usando el parametro sueldo en 2da posicion
    // usando el parametro tasa en 3era posicion
    // gracias a los parametros nombrados

    //CLASES USO:
    //4 instancias usando todos los constructores
    val sumaA = Suma(1,1)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null,null)


    //Usamos la función sumar dentro de cada instancia
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()

    //Uso de component object como static
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    /*
    Semana 7
    *14/11/2024
    Tema: Arreglos
    */

    //inicio
    //--
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);

    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

    val arregloNuevo : ArrayList<Char> = arrayListOf<Char>('a','b')

    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)
    println(arregloNuevo)

    //--
    // For each = > Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("valorActual: $valorActual");
        }

    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach { println( "Valor Actual (it): ${it}")}

    // Map -> Muta (Modifica cambio) el arreglo
    // 1. enviamos el nuevo valor a la iteracion
    // 2. nos devuelve un nuevo Arreglo con valores
    // de las iteracionnes
    val respuestaMap:List<Double> = arregloDinamico
        .map {valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println("Map 1 " + respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println("Map 2: " + respuestaMapDos)

    //--
    // Filter -> filtrar el arreglo
    // 1) Devolver una expresion (true o false)
    // 2) nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //expresion o condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter{ it <= 5}
    println("FILTER: "+respuestaFilter)
    println(respuestaFilterDos)

    // OR -> ANY (Alguno cumple?)
    // AND → ALL? (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }

    println("ANY: " + respuestaAny)

    // AND → ALL (TODOS JUNTOS?)
    println(respuestaAny) // true
    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) // false

    //fin
}

//Funciones
fun imprimirNombre(nombre:String): Unit{ //Unit es opcional, es similar al void
    fun otraFuncionAdentro(){
        println("Otra funcion adentro")
    }
    println("Nombre: $nombre") // Uso sin llaves
    println("Nombre: ${nombre}") // Uso con llaves opcional
    println("Nombre: ${nombre + nombre}") // Uso con llaves (concatenado)
    println("Nombre: ${nombre.uppercase()}") // uso con llaves (funcion)
    println("Nombre: $nombre.upppercase()") // INCORRECTO!
    // (no pueden usar sin llaves)
    otraFuncionAdentro()
}
fun calcularSueldo(
    sueldo:Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial:Double? = null // Opcional (nullable)
    // Variable? -> "?" Es Nullable (osea que puede en algun momento ser nulo)
):Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}
//***********CLASES**********//
//JAVA
abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    
    constructor(uno:Int,dos:Int){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}


//KOTLIN

//CLASE PRINCIPAL
abstract class Numeros( // Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int , (parametro (sin modificador acceso))

    // Caso 2) Parametro y propiedad (atributo) (protected)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    parametroNoUsadoNoPropiedadDeLaClase:Int? = null
){
    init { // bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando clase padre Numeros")
    }
}
//PRUEBA DE HERENCIA
class Suma( unoParametro: Int, dosParametro: Int): Numeros(unoParametro, dosParametro){ //pasar parametros de Suma al padre Números

    //Modificadores de Acceso
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico implicito" //por defecto en kotlin las variable son públicas

    //CONSTRUCTORES

    // 1. Bloque constructor primario
    init{
        this.numeroUno //Heredado de números
        this.numeroDos
        numeroUno //this. OPCIONAL (propiedades, metodos)
        numeroDos //this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito
    }
    //Constructores secundarios
    //1
    constructor(
        uno: Int?, //Entero nullable
        dos: Int,
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        //OPCIONAL
        //Bloque de código de constructor secundario
    }
    //2
    constructor(
        uno: Int,
        dos: Int?, //Entero nullable
    ):this(
        uno,
        if(dos==null) 0 else dos,
    )
    //3
    constructor(
        uno: Int?,//Entero nullable
        dos: Int?,//Entero nullable
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos
    )

    //Declaración de métodos: similar a las funciones
    fun sumar():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object{ //Comparte entre todas las instancias, similar al STATIC
        //funciones, variables
        //NombreClase.metodo, NombreClase.funcion =>
        //Suma.pi
        val pi = 3.14
        //Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num*num}
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}
