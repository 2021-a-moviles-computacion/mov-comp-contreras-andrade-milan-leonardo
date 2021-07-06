import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class Archivo {


    fun eliminarJugadorPorId(
        id: String
    ){
        val jugadoresEncontrados2: Jugador? =objBuscar.buscarJugadorPorId(id)
        if(jugadoresEncontrados2==null){
            println("No existe el Jugador")
        }else{
            println(
                "Desea Eliminar el Siguiente Jugador?\n" +
                        jugadoresEncontrados2.devolverString() + "\n" +
                        "1. Si\n" +
                        "2. No\n" +
                        "Opcion: "
            )
            when (leerPorTeclado1()) {
                ("1") -> {
                    eliminarArchivoLinea("Jugadores.txt",jugadoresEncontrados2.devolverString())
                    println("Se ha eliminado el Jugador con exito")
                }
                ("2") -> {
                    println("No se ha eliminado el Jugador")
                }
                else -> println("Opcion no valida")
            }
        }
    }

    fun eliminarClubPorNombre(
        nombre:String
    ) {
        val clubEncontrado =objBuscar.buscarClubPorNombre(nombre)
        if(clubEncontrado==null){
            println("No existe el club ")
        }else{
            val jugadoresEncontrados= objBuscar.buscarJugadorPorClub(nombre)
            if(jugadoresEncontrados!=null){
                println("Existen jugadores asociados a este club, no es posible eliminar este club")
            }else{

                println(
                    "Desea Eliminar el Siguiente Club?\n" +
                            clubEncontrado[0].devolverString() + "\n" +
                            "1. Si\n" +
                            "2. No\n" +
                            "Opcion: "
                )
                when (leerPorTeclado1()) {
                    ("1") -> {
                        eliminarArchivoLinea("Clubes.txt",clubEncontrado[0].devolverString())
                        println("Se ha eliminado el club con exito")
                    }
                    ("2") -> {
                        println("No se ha eliminado el club")
                    }
                    else -> println("Opcion no valida")
                }


            }
        }
    }

    private fun eliminarArchivoLinea(
        nombreArchivo: String,
        lineToRemove:String
    ){

        val file: Scanner
        val writer: PrintWriter
        //val lineToRemove = "2;1;JUGADOR1;APELLIDO2;12/05/1988;Defensa;30000;"

        val file1 =  File("copia.txt")
        file1.createNewFile()
        val file2 = File(nombreArchivo)
        try {
            file = Scanner(file2)//original
            writer = PrintWriter(file1 )//copia
            while (file.hasNext()) {
                val line = file.nextLine()

                if(line==lineToRemove){
                    //linea=data
                    //writer.write("\n")
                    //println("True: ")
                }else{
                    writer.write(line)
                    writer.write("\n")
                    //println("FALSE: ")
                }




            }
            file.close()
            writer.close()

            file2.delete()
            (file1).renameTo(file2)


            // File("Jugadores - Copy.txt").delete();
            limpiarArchivoLineasEnBlanco(nombreArchivo)
        } catch (ex: FileNotFoundException) {
            //Logger.getLogger(Test::class.java.getName()).log(Level.SEVERE, null, ex)
        }


    }



    private fun modificarArchivoLinea(
        nombreArchivo: String,
        lineToUpdate:String,
        lineUpdated:String
    ){

        val file: Scanner
        val writer: PrintWriter
        //val lineToRemove = "2;1;JUGADOR1;APELLIDO2;12/05/1988;Defensa;30000;"

        val file1 =  File("copia.txt")
        file1.createNewFile()
        val file2 = File(nombreArchivo)
        try {
            file = Scanner(file2)//original
            writer = PrintWriter(file1 )//copia
            while (file.hasNext()) {
                val line = file.nextLine()

                if(line==lineToUpdate){
                    writer.write(lineUpdated)
                    writer.write("\n")
                    println("True: ")
                }else{
                    writer.write(line)
                    writer.write("\n")
                    println("FALSE: ")
                }

            }
            file.close()
            writer.close()

            file2.delete()
            (file1).renameTo(file2)


            // File("Jugadores - Copy.txt").delete();
            limpiarArchivoLineasEnBlanco(nombreArchivo)
        } catch (ex: FileNotFoundException) {
            //Logger.getLogger(Test::class.java.getName()).log(Level.SEVERE, null, ex)
        }


    }



    private fun limpiarArchivoLineasEnBlanco(
        nombreArchivo: String,
    ){

        val file: Scanner
        val writer: PrintWriter
        //val lineToRemove = "2;1;JUGADOR1;APELLIDO2;12/05/1988;Defensa;30000;"

        val file1 =  File("copia.txt")
        file1.createNewFile()
        val file2 = File(nombreArchivo)
        try {
            file = Scanner(file2)//original
            writer = PrintWriter(file1 )//copia
            while (file.hasNext()) {
                val line = file.nextLine()

                if(!file.hasNext()){
                    writer.write(line)
                }else if(line.isEmpty()){
                    //linea=data
                    //writer.write("\n")
                    //println("True: ")
                }else{
                    writer.write(line)
                    writer.write("\n")
                    //println("FALSE: ")
                }



            }

            file.close()
            writer.close()

            file2.delete()
            (file1).renameTo(file2)
            // File("Jugadores - Copy.txt").delete();

        } catch (ex: FileNotFoundException) {
            //Logger.getLogger(Test::class.java.getName()).log(Level.SEVERE, null, ex)
        }


    }



    fun escribirArchivo(
        str:String,
        nombreArchivo:String
    ){
        try {
            FileWriter(nombreArchivo, true).use { fw ->
                BufferedWriter(fw).use { bw ->
                    PrintWriter(bw).use { out ->
                        out.print("\n"+str)
                    }
                }
            }
            //limpiarArchivoLineasEnBlanco(nombreArchivo)
        } catch (e: IOException) {
            println("Existio un error al escribir en el Archivo")
        }
    }

    private fun leerPorTeclado1(): String {
        val teclado = Scanner(System.`in`)
        val str = teclado.nextLine()
        return str.uppercase()
    }


    fun updateClubPorNombreClub(
        nombreClub: String
    ){
        val clubesEncontrado = objBuscar.buscarClubPorNombre(nombreClub)

        if(clubesEncontrado==null){
            println("No existe el club")
        }else{
            val clubEncontrado = clubesEncontrado[0]
            println("Se modificara el jugador: "+clubEncontrado.devolverString())
            //val objJugador = jugadorEncontrado
            while(true){
                println("\n++++++++++++++++++++++++++++++++++++")
                print(
                    "Update\n" +
                            "1. Presidente          \n" +
                            "2. Director Tecnico          \n" +
                            "3. Copas    \n" +
                            "4. Serie             \n" +
                            "5.Terminado                \n" +
                            "Opcion: "
                )
                when(leerPorTeclado1()){
                    ("1")->{
                        println("Valor Actual:"+ clubEncontrado.presidenteClub)
                        print("Insertar el Nombre del Presidente: ")
                        clubEncontrado.presidenteClub=leerPorTeclado1()
                    }
                    ("2")->{
                        println("Valor Actual:"+ clubEncontrado.directorTecnicoClub)
                        print("Insertar el Nombre de  Director Tecnico: ")
                        clubEncontrado.directorTecnicoClub=leerPorTeclado1()
                    }
                    ("3")->{
                        println("Valor Actual:"+ clubEncontrado.copasClub)
                        print("Insertar el numero de copas: ")
                        clubEncontrado.copasClub=leerPorTeclado1().toInt()
                    }
                    ("4")->{
                        println("Valor Actual:"+ clubEncontrado.serieClub)
                        print("Insertar la serie del equipo: ")
                        clubEncontrado.serieClub=leerPorTeclado1()

                    }

                    ("5")->break
                    else->println("Opcion no valida")
                }


            }

            println(
                "Desea Guardar los cambios?\n" +
                        clubEncontrado.devolverString() + "\n" +
                        "1. Si\n" +
                        "2. No\n" +
                        "Opcion: "
            )
            when (leerPorTeclado1()) {
                ("1") -> {
                    //eliminarArchivoLinea("Jugadores.txt",jugadoresEncontrados2.devolverString())
                    println("original: "+ objBuscar.buscarClubPorNombre(nombreClub)!![0].devolverString())
                    println("nuevo: "+clubEncontrado.devolverString())

                    modificarArchivoLinea("Clubes.txt",objBuscar.buscarClubPorNombre(nombreClub)!![0].devolverString(),clubEncontrado.devolverString())
                    println("Se ha actualizado el CLUB con exito")
                }
                ("2") -> {
                    println("No se ha actualizado el CLUB")
                }
                else -> println("Opcion no valida")
            }
        }

    }


    fun updateJugadorPorIdJugador(
        idJugador:String
    ){
        val jugadorEncontrado = objBuscar.buscarJugadorPorId(idJugador)

        if(jugadorEncontrado==null){
            println("No existe el jugador")
        }else{
            println("Se modificara el jugador: "+jugadorEncontrado.devolverString())
            //val objJugador = jugadorEncontrado

            while(true){
                println("\n++++++++++++++++++++++++++++++++++++")
                print(
                    "Update\n" +
                            "1. Primer Nombre           (si)\n" +
                            "2. Primer Apellido          (si)\n" +
                            "3. Fecha Nacimiento    (no)\n" +
                            "4. Titular             (no)\n" +
                            "5. Sueldo                  \n" +
                            "6.Terminado                \n" +
                            "Opcion: "
                )
                when(leerPorTeclado1()){
                    ("1")->{
                        println("Valor Actual:"+ jugadorEncontrado.nombreJugador)
                        print("Insertar el Primer Nombre: ")
                        jugadorEncontrado.nombreJugador=leerPorTeclado1()
                    }
                    ("2")->{
                        println("Valor Actual:"+ jugadorEncontrado.apellidoJugador)
                        print("Insertar el Primer Apellido: ")
                        jugadorEncontrado.apellidoJugador=leerPorTeclado1()
                    }
                    ("3")->{
                        println("Valor Actual:"+ jugadorEncontrado.fechaNacimientoJugador)
                        print("Insertar Fecha Nacimiento 00/00/0000: ")
                        jugadorEncontrado.fechaNacimientoJugador= SimpleDateFormat("dd/MM/yyyy").parse(leerPorTeclado1())
                    }
                    ("4")->{
                        println("Valor Actual:"+ jugadorEncontrado.titularJugador)
                        print("Es titular true/false: ")
                        jugadorEncontrado.titularJugador=leerPorTeclado1().toBoolean()

                    }
                    ("5")->{
                        println("Valor Actual:"+ jugadorEncontrado.sueldoJugador)
                        print("Sueldo del Jugador: ")
                        jugadorEncontrado.sueldoJugador=leerPorTeclado1().toDouble()

                    }
                    ("6")->break
                    else->println("Opcion no valida")
                }


            }

            println(
                "Desea Guardar los cambios?\n" +
                        jugadorEncontrado.devolverString() + "\n" +
                        "1. Si\n" +
                        "2. No\n" +
                        "Opcion: "
            )
            when (leerPorTeclado1()) {
                ("1") -> {
                    //eliminarArchivoLinea("Jugadores.txt",jugadoresEncontrados2.devolverString())
                    println("original: "+ objBuscar.buscarJugadorPorId(idJugador)!!.devolverString())
                    println("nuevo: "+jugadorEncontrado.devolverString())

                    modificarArchivoLinea("Jugadores.txt",objBuscar.buscarJugadorPorId(idJugador)!!.devolverString(),jugadorEncontrado.devolverString())
                    println("Se ha actualizado el Jugador con exito")
                }
                ("2") -> {
                    println("No se ha actualizado el Jugador")
                }
                else -> println("Opcion no valida")
            }
        }
    }



}