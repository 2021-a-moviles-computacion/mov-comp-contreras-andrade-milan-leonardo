import java.text.SimpleDateFormat

class Imprimir {

    fun imprimirEncabezadoJugador(){
        System.out.format("\n===============  JUGADORES ================\n")
        println("\n+-----------+----------+----------+----------+-----------------+----------+--------+")
        System.out.format("%-12s|%-10s|%-10s|%-10s|%-17s|%-10s|%-10s","ID","ID CLUB","NOMBRE","APELLIDO","FECHA NAC","TITULAR","SUELDO")
        println("\n+-----------+----------+----------+----------+-----------------+----------+--------+")
    }

    fun imprimirEncabezadoJugadorMasClub(){
        System.out.format("\n===============  JUGADORES + CLUB ================\n")
        println("\n+-----------+----------+----------+----------+-----------------+----------+----------+------+")
        System.out.format("%-12s|%-10s|%-10s|%-10s|%-17s|%-10s|%-10s|%s","ID","ID CLUB","NOMBRE","APELLIDO","FECHA NAC","TITULAR","SUELDO","CLUB")
        println("\n+-----------+----------+----------+----------+-----------------+----------+----------+------+")
    }

    fun imprimirEncabezadoClub(){//falta Hacer
        System.out.format("\n===============  CLUB ================\n")
        println("\n+---+------------------------------+--------------------+--------------------+-----+-------+")
        System.out.format("%-4s|%-30s|%-20s|%-20s|%-6s|%-6s|","ID","CLUB","PRESIDENTE","DT","COPAS","SERIE")
        println("\n+---+------------------------------+--------------------+--------------------+-----+-------+")
    }



    fun imprimirArregloJugadores(
        arregloJugadores: ArrayList<Jugador>?
    ) {
        if(arregloJugadores==null){
            println("No existen jugadores")
        }else{

            arregloJugadores.forEach {
                System.out.format("%-12s|%-10s|%-10s|%-10s|%-17s|%-10s|%-10s\n",
                    it.idJugador,
                    it.idClubJugador,
                    it.nombreJugador,
                    it.apellidoJugador,
                    SimpleDateFormat("dd/MM/yyyy").format(it.fechaNacimientoJugador),
                    it.titularJugador,
                    it.sueldoJugador)
            }
        }

    }

    fun imprimirArregloClubs(
        arregloClubs: ArrayList<Club>?
    ){
        if(arregloClubs==null){
            println("No existen Clubs")
        }else arregloClubs.forEach {
            System.out.format("%-4s|%-30s|%-20s|%-20s|%-6s|%-6s|\n",
                it.idClub,
                it.nombreClub,
                it.presidenteClub,
                it.directorTecnicoClub,
                it.copasClub,
                it.serieClub
            )
        }

    }

    fun imprimirJugadoresMasNombreClub(
        arregloJugadores: ArrayList<Jugador>?
        //arregloClubs: ArrayList<Club>
    ){
        if(arregloJugadores==null){
            println("No existen jugadores")
        }else{
            imprimirJugadoresMasClub2(arregloJugadores)
        }

    }

    private fun imprimirJugadoresMasClub2(
        arregloJugadores:ArrayList<Jugador>?
    ){

        if(arregloJugadores==null){
            println("No existen Jugadores")
        }else{
            val arregloClubs: ArrayList<Club> =objLeer.leerClubs()
            arregloJugadores.forEach {arregloJugador->
                val club= arregloClubs
                    .filter {
                        return@filter(it.idClub==arregloJugador.idClubJugador)
                    }

                //System.out.format("%-4s|%-10s|%-10s|%-10s|%-17s|%-10s|%-10s|%s\n",
                System.out.format("%-12s|%-10s|%-10s|%-10s|%-17s|%-10s|%-10s|%s\n",
                    arregloJugador.idJugador,
                    arregloJugador.idClubJugador,
                    arregloJugador.nombreJugador,
                    arregloJugador.apellidoJugador,
                    SimpleDateFormat("dd/MM/yyyy").format(arregloJugador.fechaNacimientoJugador),
                    arregloJugador.titularJugador,
                    arregloJugador.sueldoJugador,
                    club[0].nombreClub
                    //(club[0] as Club).nombreClub
                )
            }
        }



    }






    /*
    fun imprimirJugadoresMasNombreClub(
        nombreClub: String?
    ){
        //var resultado:Boolean
        if(nombreClub==null){
            println("No existen jugadores")
        }else{

            val arregloClubs=objLeer.leerClubs()
            val club1=arregloClubs.filter {
                return@filter(it.nombreClub==nombreClub)
            }

            val club2 = objBuscar.buscarClubPorNombre(nombreClub)
            val jugadoresEncontrados:ArrayList<Jugador>?
            if(club2==null){
                jugadoresEncontrados = null
            }else{
                jugadoresEncontrados = objLeer.leerJugadores().filter {
                    //return@filter(it.idClubJugador==(club1[0]as Club).idClub)
                    return@filter(it.idClubJugador==club2[0].idClub)
                }as ArrayList<Jugador>
            }


            imprimirJugadoresMasClub2(jugadoresEncontrados)


        }

    }
*/



}