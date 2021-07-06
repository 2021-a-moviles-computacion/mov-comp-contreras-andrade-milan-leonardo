import java.util.*

fun main() {

    println("Hola Mundo")
    //Tipo nomre = valor;
    //In endad = 12;
    var edadProfesor: Int = 32
    //var edadProsor: Int = 32; //buena practica no poner el tipo de dato.

    //Duck Typing, el duck typing viene de gringos, si es que suena como un pato, camina como pato, si nada cmo pato,
    //entonces es un pato. Entonces eso se ve como un entero, tiene los métodos de un entero, entonces le asigno un entero

    var sueldoProfesor = 1.23;
    //edadProfesor = 25.5; no puedes poner un flotante dentro de un entero.

    edadProfesor = 18
    sueldoProfesor = 23.5
    //Kotlin no es necesario colocar el nombre de las variables.


    //Variables Mutables / Inmutables.

    //Mutables (Re asignar  '=' )
    var edadCachoro: Int = 0
    edadCachoro = 1
    edadCachoro = 2
    edadCachoro = 3

    //Inmutable (No re asignar "=")
    val numeroCedula = 1755185749
    //numeroCedula = 4545454 // los vals no pueden ser reasignados

    //Nos da más errores las variables mutables, menos erroes las variables inmutables.


    //Tipos de variables son los mismos que java

    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'S'
    val fechaNacimiento: Date = Date()


    //condicionales

    if (true) {

        //veradero
    } else {

        //falso
    } //igual a java.

    //switch Estado -> S -> C ::::

    //aquí es el when
    val estadoCivilWhen: String = "S";
    when (estadoCivilWhen) {

        ("S") -> {

            println("Acercarse")
        }

        ("C") -> {

            print("Alejarse")
        }

        "Un" -> println("hablar")

        else -> println("No reconocido")
    }
//IF DE UNA SOLA línea, en otros lenguajes  //condicion ? bloque-true: bloque-false.
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No";

    imprimirNombre("Adrian")
    calcularSueldo(100.00)
    calcularSueldo(100.00, 14.00)
    calcularSueldo(100.00, 14.00,25.00)

    //Named parameteres, veamos a que se refeiren estis parametros nombrados.
    //esto lo tienen algunos lenagujes, como python y algunos otros lenguajes más,
    //no se olviden dentro del lenguaje klotin puede hacer uso de los parametros nombrados.

    calcularSueldo(
        bonoEspecial =  15.00,
        // me sate el parametro de la tasa. tasa  = 12.00
        sueldo = 150.00,

        )

    calcularSueldo(
        tasa =  14.00,
        bonoEspecial = 30.00,
        sueldo =   1000.00

    )


    //Tipos de arreglos.
    //En los arreglos dinamicos puedo agregar más ojetos en los etsaticos no.
    val arregloEstatico: Array<Int> = arrayOf(1,2,3)
    println(arregloEstatico)
    //arregloEstatico.add(13) -> no vale porque es estatico
    //no puedo añadir un número porque no lo permiten los arreglos estaticos.
    val arregloDinamico:ArrayList<Int> = arrayListOf(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico);
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico);
    //imprimirNombre()

    //Operadores -> sirven para los arreglos estaticos y dinamicos.

    //No usar los for ni los whiles, si no utlizar el operador que me ayude hacer lo que tengo que hacer.

    //For each -> unit no devuelve nada.
    //itera un arreglo.

    val respuestaForEac:Unit = arregloDinamico
        .forEach{ valorActual:Int ->
            println("Valor Actual: ${valorActual}")   //no hace falta concatenar.

        }



    arregloDinamico.forEach{
        println("Valor actual: ${it}")  //it el valor a los que vna a llegar a esta función
        //Si solamente recibe un parametro, esye se va a llamar "it"

    }

    println(respuestaForEac) //La sintaxis, podemos definir estas llaves dentro de las llaves definimos el datos que nos va a llegar.

    //MAP-> nos ayuda a cambiar un arreglo, arreglo diferente.

    //1) encamos el nuevo valor de la iteración
    //2) Nos devuelve una lista., un nuevo arreglo.

    val respuestaMap: List<Double> = arregloDinamico.map {

            valorActual:Int ->

        return@map  valorActual.toDouble() + 100.00
    }



    //Filter -> filtrar un areeglo
    //1) Devuelve una expresión (fale o true)



    val respuesmap2 = arregloDinamico.map { it +15 }
    println(respuesmap2)

    println("Que más, ${respuesmap2}");  //Map no cambia el tamaño, cambia cada valor individular, el que estaba en la posición 0
    //se cambio  a 10.0 yasí sucecivmente. Puedo modificar mi arreglo como yo guste.







    arregloDinamico
        .forEachIndexed{ indice:Int, valoractual:Int ->
            println("Hola, Valor ${valoractual} indice: ${indice}")
        }



    val respuestFilter : List<Int> = arregloDinamico.filter {

        val mayoresACINCO:Boolean = it > 5

        return@filter mayoresACINCO  //solo devuelve aquellos items del arreglo que cumpmen la expresión.-

    }



    val respuestaFilter2 = arregloDinamico.filter { it<5 }
    println(respuestaFilter2)


    println(respuestFilter)


    //ANY, ALL
    val respuestaAny: Boolean = arregloDinamico.any{
        return@any  (it > 5)
    }

    println("Hola soy any , ${respuestaAny}")

    val respuestaAll: Boolean = arregloDinamico.all{
        return@all  (it > 5)
    }

    println("Hola soy all , ${respuestaAll}")

    //REDUCE
    val respuestaReduce: Int = arregloDinamico
        .reduce {//acumulado = 0 -> SIEMPRE EMPIEZA EN 0}
                acumulado: Int, valorActual: Int ->
              return@reduce (acumulado + valorActual) // -> Logica negocio
        }
    println(respuestaReduce)

    val arregloDanio = arrayListOf<Int>(12,15,8,10)
    val respuestaReduceFold=arregloDanio
        .fold(
            100, //
            { acumulado, valorActualIteracion ->
                return@fold acumulado - valorActualIteracion
            }
        )
    println(respuestaReduceFold)


    val vidaActual: Double = arregloDinamico
        .map{it *2.3} //arreglo
        .filter {it >20}
        .fold(100.00, {acc,i->acc-i})
        .also{print(it)}
    println("Valor vida Actual ${vidaActual}")


    val ejemploUno = Suma(1,2)
    val ejemploDos = Suma(null,2)
    val ejemploTres = Suma(1,null)

    println("\nEjemplo 1:")
    println(ejemploUno.sumar())
    println(Suma.historialSumas)
    println("\nEjemplo 2:")
    println(ejemploDos.sumar())
    println(Suma.historialSumas)
    println("\nEjemplo 3:")
    println(ejemploTres.sumar())
    println(Suma.historialSumas)





} //Fin bloque main









//en java void impirmirNombre(Satring nombre){}
//e java se llama void en Klorin se llama Unit.
fun imprimirNombre(nombre: String): Unit {
    println("Nombre:  ${nombre}")


}

//las variables de arriba no pueden ser nulas.
fun calcularSueldo(
    sueldo: Double //Requerido
    , tasa: Double = 12.00, //Opcional
    //bonoEsepecial: Double?, Quiere decir que esta variable puede ser nula. algun momento el valor de nulo.
    bonoEspecial: Double? = null //Es decir pueden madar o no y puede que sea el nullo
// Indentar -> (ctrol +A) -> (ctrol + alt + l)
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial
    }
}


abstract class NumerosJava {
    protected val numeroUno: Int //Propiedad
    private val numeroDos: Int

    constructor(
        uno: Int, //Parametros Requeridos
        dos:Int //Parametros Requeridos
    ){
        numeroUno = uno
        numeroDos = dos
        println("inicializar")
    }
}


abstract class Numeros(//Constructor Primario
    protected val numeroUno: Int,
    protected val numeroDos: Int
    ){
    init{//Bloque de inicio del constructor primario
        println("Inicializar")
    }
}


class Suma (
    uno: Int, //Parametros requeridos
    dos: Int, //Parametros requeridos
        ):Numeros(//constructor papa (super)
    uno,
    dos,
) {
    init {
        this.numeroUno
        this.numeroDos
        //X -> this.uno-> NO EXISTEN
        //X -> this.dos-> NO EXISTEn
    }

    constructor(//segundo constructor
        uno: Int?,//parametros
        dos: Int//parametros
    ) : this(//llamada cosntructor primario
        if (uno == null) 0 else uno,
        dos
    )
    constructor(
        uno: Int,//parametros
        dos: Int?//parametros
    ):this(
        uno,
        if (dos == null) 0 else dos,
    )

    public fun sumar():Int{
        //val total: Int = this.numeroUno + this.numeroDos
        val total: Int = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }


    //Singleton
    companion object{
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
            //println(historialSumas)
        }
    }

}
