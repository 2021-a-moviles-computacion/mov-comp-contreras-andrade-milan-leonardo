class Buscar {

    fun buscarJugadorPorNombre(
        nombre: Array<String>
    ):ArrayList<Jugador>?{
        var resultado:Boolean
        val jugadoresEncontrados: ArrayList<Jugador>
        when{
            (nombre.size>2)->{
                return null
            }

            (nombre.size==2)->{
                jugadoresEncontrados = objLeer.leerJugadores().filter {
                    return@filter ((it.nombreJugador == nombre[0])
                            or (it.nombreJugador == nombre[1])
                            or (it.apellidoJugador == nombre[0])
                            or (it.apellidoJugador == nombre[1]))
                }as ArrayList<Jugador>

                return if(jugadoresEncontrados.size==0){
                    null
                }else{
                    jugadoresEncontrados
                }
            }

            (nombre.size==1)->{
                jugadoresEncontrados = objLeer.leerJugadores().filter {
                    resultado=((it.nombreJugador==nombre[0])
                            or (it.apellidoJugador==nombre[0]))
                    return@filter resultado
                }as ArrayList<Jugador>

                return if(jugadoresEncontrados.size==0){
                    null
                }else{
                    jugadoresEncontrados
                }
            }

            else -> {
                return null
            }
        }
    }

    fun buscarJugadorPorId(
        id: String
    ):Jugador?{
        val jugadoresEncontrados: ArrayList<Jugador> = objLeer.leerJugadores()
            .filter {
                return@filter (it.idJugador == id)
            }as ArrayList<Jugador>

        return if(jugadoresEncontrados.size==0){
            null
        }else{
            jugadoresEncontrados[0]
        }


    }

    fun buscarJugadorPorClub(
        nombreClub: String
    ):ArrayList<Jugador>?{
        val arregloClubs: ArrayList<Club> = objLeer.leerClubs()
        val club1 =arregloClubs.filter {
            return@filter (it.nombreClub==nombreClub)
        }

        if(club1.isEmpty()){
            return null
        }else{
            val resultadoJugadores=(objLeer.leerJugadores().filter {
                return@filter (it.idClubJugador== club1[0].idClub)
            }as ArrayList<Jugador>)
            return if(resultadoJugadores.isEmpty()){
                null
            }else{
                resultadoJugadores
            }

        }
    }

    fun buscarClubPorNombre(
        nombreClub:String
    ): ArrayList<Club>?{

        val resultado=objLeer.leerClubs().filter {
            return@filter (it.nombreClub==nombreClub.uppercase())
        }as ArrayList<Club>
        return if(resultado.isEmpty()){
            null
        }else{
            resultado
        }

    }



}