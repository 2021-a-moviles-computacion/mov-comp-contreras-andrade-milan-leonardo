import java.util.*


val objLeer = Leer()
val objBuscar=Buscar()
val objImprimir=Imprimir()
val objArchivo= Archivo()
//fun main(args: Array<String>) {
fun main() {

    var opcion: String //variable para menus

//    println("\n++++++++++++++++++++++++++++++++++++")
//    print(
//        "Selecione una opcion\n" +
//                "1. Probar      (si)\n" +
//                "2. Final        (si)\n" +
//                "opcion: "
//    )
//    opcion = leerPorTeclado()
//when(opcion){


   // ("2")->{
        //when (opcion)

        //Menu 0
        println("\n++++++++++++++++++++++++++++++++++++")
        print(
            "Selecione una opcion\n" +
                    "1. Create      \n" +
                    "2. Read        \n" +
                    "3. Update      \n" +
                    "4. Delete      \n" +
                    "opcion: "
        )
        opcion = leerPorTeclado()
        //Menu0 - when
        when (opcion) {
            //Create
            ("1") -> {
                //Menu1.1
                println("\n++++++++++++++++++++++++++++++++++++")
                print(
                    "Selecione una opcion\n" +
                            "1. Club          \n" +
                            "2. Jugador       \n" +
                            "opcion: "
                )
                opcion = leerPorTeclado()
                //Menu1.1 - when
                var datosEntrada: String
                var valorActual: String
                when (opcion) {
                    //Crear club
                    ("1") -> {
                        print("Ingresar el Nombre del club: ")
                        valorActual = leerPorTeclado()
                        if (objBuscar.buscarClubPorNombre(valorActual) == null) {
                            datosEntrada = "$valorActual;"
                            print("Ingresar el Nombre del presidente del club: ")
                            datosEntrada = datosEntrada + leerPorTeclado() + ";"
                            print("Ingresar el Nombre del Director Tecnico del club: ")
                            datosEntrada = datosEntrada + leerPorTeclado() + ";"
                            print("Ingresar el Numero de Copas del club: ")
                            datosEntrada = datosEntrada + leerPorTeclado() + ";"
                            print("Ingresar la Serie del club: ")
                            datosEntrada = datosEntrada + leerPorTeclado() + ";"

                            println(
                                "Desea Insertar los Siguientes datos?\n" +
                                        datosEntrada + "\n" +
                                        "1. Si\n" +
                                        "2. No\n" +
                                        "Opcion: "
                            )
                            when (leerPorTeclado()) {
                                ("1") -> {
                                    //generar idClub
                                    val newId: Int = ((objLeer.leerClubs().last().idClub).toInt() + 1)
                                    datosEntrada = "$newId;$datosEntrada"
                                    objArchivo.escribirArchivo(datosEntrada, "Clubes.txt")
                                }
                                ("2") -> {
                                    println("No se ha ingresado el Jugador")
                                }
                                else -> println("Opcion no valida")
                            }

                        } else {
                            println("Ya existe este club")
                        }
                    }
                    //Crear Jugador
                    ("2") -> {
                        print("Ingresar la cedula del Jugador: ")
                        valorActual = leerPorTeclado()
                        if (objBuscar.buscarJugadorPorId(valorActual) == null) {
                            datosEntrada = "$valorActual;"
                            print("Ingresar el Nombre del Club del Jugador: ")
                            valorActual = leerPorTeclado()
                            val clubEncontrado = objBuscar.buscarClubPorNombre(valorActual)
                            if (clubEncontrado != null) {
                                datosEntrada = datosEntrada + clubEncontrado[0].idClub + ";"
                                print("Ingresar el Primer Nombre del Jugador: ")
                                datosEntrada = datosEntrada + leerPorTeclado() + ";"

                                print("Ingresar el Primer Apellido del Jugador: ")
                                datosEntrada = datosEntrada + leerPorTeclado() + ";"

                                print("Ingresar la fecha de nacimeinto del Jugador: ")
                                datosEntrada = datosEntrada + leerPorTeclado() + ";"

                                print("EL jugador es titular? true/false: ")
                                datosEntrada = datosEntrada + leerPorTeclado().lowercase() + ";"

                                print("Ingresar el Sueldo del Jugador: ")
                                datosEntrada = (datosEntrada)+ leerPorTeclado().toDouble() + ";"



                                println(
                                    "Desea Insertar los Siguientes datos?\n" +
                                            datosEntrada + "\n" +
                                            "1. Si\n" +
                                            "2. No\n" +
                                            "Opcion: "
                                )
                                when (leerPorTeclado()) {
                                    ("1") -> {
                                        objArchivo.escribirArchivo(datosEntrada, "Jugadores.txt")
                                    }
                                    ("2") -> {
                                        println("No se ha ingresado el Jugador")
                                    }
                                    else -> println("Opcion no valida")
                                }

                            } else {
                                println("No existe este Club")
                            }
                        } else {
                            println("Ya existe este Jugador")
                        }


                    }

                }


            }
            //Read
            ("2") -> {
                //Menu2.1
                println("\n++++++++++++++++++++++++++++++++++++")
                print(
                    "Selecione una opcion\n" +
                            "1. Leer Jugadores                  \n" +
                            "2. Leer Clubs                      \n" +
                            "3. Leer Jugadores + nombre Club    \n" +
                            "4. Buscar                          \n" +
                            "opcion: "
                )
                opcion = leerPorTeclado()
                //Menu2.1-when
                when (opcion) {
                    //Leer Jugadores
                    ("1") -> {
                        objImprimir.imprimirEncabezadoJugador()
                        objImprimir.imprimirArregloJugadores(objLeer.leerJugadores())
                    }
                    //Leer Clubs
                    ("2") -> {
                        //println("error aqui")
                        objImprimir.imprimirEncabezadoClub()
                        objImprimir.imprimirArregloClubs(objLeer.leerClubs())
                        //println(objLeer.leerClubs())
                    }
                    //Leer Jugadores + nombre Club
                    ("3") -> {
                        objImprimir.imprimirEncabezadoJugadorMasClub()
                        objImprimir.imprimirJugadoresMasNombreClub(objLeer.leerJugadores())
                    }
                    //Buscar
                    ("4") -> {
                        println("\n++++++++++++++++++++++++++++++++++++")
                        print(
                            "Selecione una opcion\n" +
                                    "1. Buscar Jugador Por Nombre           \n" +
                                    "2. Buscar Jugador Por Equipo           \n" +
                                    "3. Buscar Club Por Nombre              \n" +
                                    "opcion: "
                        )
                        opcion = leerPorTeclado()
                        when (opcion) {

                            //Buscar Jugador Por Nombre
                            ("1") -> {
                                //Escribir nombre del Jugador
                                println(
                                    "Nota: Si se va a escribir el apellido separar el nombre y apellido por una coma," +
                                            "sin espacios en blanco ejm: Nombre1,Apellido1" +
                                            "Nombre del Jugador: "
                                )

                                //imprimirEncabezadoJugadorMasClub()
                                //imprimirJugadoresMasNombreClub(jugadoresEncontrados)

                                val entrada=leerPorTeclado().uppercase().split(",").toTypedArray()
                                objImprimir.imprimirEncabezadoJugador()
                                objImprimir.imprimirArregloJugadores(objBuscar.buscarJugadorPorNombre(entrada))
                            }
                            //Buscar Jugador Por Equipo
                            ("2") -> {
                                print("Club del Jugador: ")
                                objImprimir.imprimirEncabezadoJugador()
                                objImprimir.imprimirArregloJugadores(objBuscar.buscarJugadorPorClub(leerPorTeclado().uppercase()))
                            }
                            //Buscar Club Por Nombre
                            ("3") -> {
                                print("Nombre Del Club: ")
                                objImprimir.imprimirEncabezadoClub()
                                objImprimir.imprimirArregloClubs(objBuscar.buscarClubPorNombre(leerPorTeclado().uppercase()))

                            }
                            else -> println("Opcion no valida")
                        }

                    }
                    else -> println("Opcion no valida")

                }
            }
            //Update
            ("3") -> {
                println("\n++++++++++++++++++++++++++++++++++++")
                print(
                    "Update: \n" +
                            "1. Jugador      \n" +
                            "2. Club         \n" +
                            "opcion: "
                )
                opcion = leerPorTeclado()
                when(opcion){
                        //Update Jugador
                    ("1")->{
                        println("\n++++++++++++++++++++++++++++++++++++")
                        print(
                            "Selecione una opcion\n" +
                                    "1. Buscar Jugador Por Nombre           \n" +
                                    "2. Buscar Jugador Por Equipo           \n" +
                                    "opcion: "
                        )

                        when (leerPorTeclado()) {
                            //Buscar Jugador Por Nombre
                            ("1") -> {
                                //Escribir nombre del Jugador
                                println(
                                    "Nota: Si se va a escribir el apellido separar el nombre y apellido por una coma," +
                                            "sin espacios en blanco ejm: Nombre1,Apellido1" +
                                            "Nombre del Jugador: "
                                )
                                val entrada =leerPorTeclado().uppercase().split(",").toTypedArray()
                                objImprimir.imprimirEncabezadoJugador()
                                objImprimir.imprimirArregloJugadores(
                                    objBuscar.buscarJugadorPorNombre(
                                        entrada
                                    )
                                )

                                println("Insertar el id del jugador: ")
                                val idJugador = leerPorTeclado()
                                objArchivo.updateJugadorPorIdJugador(idJugador)


                            }
                            //Buscar Jugador Por Equipo
                            ("2") -> {
                                print("Club del Jugador: ")
                                val club = leerPorTeclado().uppercase()
                                val jugadoresEncontrados = objBuscar.buscarJugadorPorClub(club)
                                objImprimir.imprimirEncabezadoJugadorMasClub()
                                objImprimir.imprimirJugadoresMasNombreClub(jugadoresEncontrados)

                                println("Insertar el id del jugador: ")
                                val idJugador = leerPorTeclado()
                                objArchivo.updateJugadorPorIdJugador(idJugador)
                            }

                            else -> println("Opcion no valida")
                        }




                    }
                    //Update Club
                    ("2")->{
                        objImprimir.imprimirEncabezadoClub()

                        objImprimir.imprimirArregloClubs(objLeer.leerClubs())


                        print("Insertar el nombre del club a actualizar: ")

                        val idClub = leerPorTeclado()
                        objArchivo.updateClubPorNombreClub(idClub)
                    }

                    else->println("Seleccion no valida")
                }



            }
            //Delete
            ("4") -> {
                println("\n++++++++++++++++++++++++++++++++++++")
                print(
                    "Selecione una opcion\n" +
                            "1. Club          \n" +
                            "2. Jugador       \n" +
                            "opcion: "
                )
                opcion= leerPorTeclado()
                val teclado:String
                when(opcion){
                     //Eliminar Club
                    ("1")->{
                        objImprimir.imprimirEncabezadoClub()
                        objImprimir.imprimirArregloClubs(objLeer.leerClubs())
                        println("++++++++++++++++++++++++++++++++++++++++++++++++")
                        print("Ingrese el nombre del Club: ")
                        objArchivo.eliminarClubPorNombre(leerPorTeclado())
                    }
                    //Eliminar Jugador
                    ("2")->{
                        objImprimir.imprimirEncabezadoJugadorMasClub()
                        objImprimir.imprimirJugadoresMasNombreClub(objLeer.leerJugadores())
                        println("++++++++++++++++++++++++++++++++++++++++++++++++")

                        println("\n++++++++++++++++++++++++++++++++++++")
                        print(
                            "Buscar Jugador Por\n" +
                                    "1. Nombre     \n" +
                                    "2. Club       \n" +
                                    "opcion: "
                        )
                        opcion=leerPorTeclado()

                        when(opcion){
                            ("1")->{
                                print("Ingrese el nombre del Jugador: ")
                                teclado = leerPorTeclado()

                                //Escribir nombre del Jugador
                                println(
                                    "Nota: Si se va a escribir el apellido separar el nombre y apellido por una coma," +
                                            "sin espacios en blanco ejm: Nombre1,Apellido1" +
                                            "Nombre del Jugador: "
                                )
                                //val nombre = leerPorTeclado().uppercase()
                                val partes: Array<String> = teclado.split(",").toTypedArray()
                                val jugadoresEncontrados: ArrayList<Jugador>? = objBuscar.buscarJugadorPorNombre(partes)
                                objImprimir.imprimirEncabezadoJugadorMasClub()
                                objImprimir.imprimirJugadoresMasNombreClub(jugadoresEncontrados)
                                //objImprimir.imprimirEncabezadoJugador()
                                //objImprimir.imprimirArregloJugadores(jugadoresEncontrados)

                                print("Ingrese el Id del jugador que desea eliminar: ")

                                objArchivo.eliminarJugadorPorId(leerPorTeclado())
                            }
                            //Buscar Por CLub
                            ("2")->{
                                print("Ingrese el nombre del Club: ")
                                val club: String = leerPorTeclado().uppercase()
                                objImprimir.imprimirEncabezadoJugadorMasClub()

                                val jugadoresEncontrados = objBuscar.buscarJugadorPorClub(club)
                                if(jugadoresEncontrados==null){
                                    print("No existen jugadoress")
                                }else{
                                    objImprimir.imprimirJugadoresMasNombreClub(jugadoresEncontrados)

                                    print("Ingrese el Id del jugador que desea eliminar: ")
                                    objArchivo.eliminarJugadorPorId(leerPorTeclado())
                                }

                            }
                            else->println("Opcion no valida")
                        }



                    }
                }


            }
            else -> println("Opcion no valida")
        }

    }
//}
//}


fun leerPorTeclado(): String {
    val teclado = Scanner(System.`in`)
    val str = teclado.nextLine()
    return str.uppercase()
}






