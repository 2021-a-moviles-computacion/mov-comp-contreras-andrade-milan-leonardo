import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class Leer {

    //Leer          ========================================================================================================
    private fun leerArchivo(
        nombreArchivo: String
    ):ArrayList<ArrayList<String>>{
        val jugador = ArrayList<String>()
        val jugadores = arrayListOf(ArrayList<String>())
        //val matrix5 = arrayListOf(Array)

        try {
            val myObj = File(nombreArchivo)
            val myReader = Scanner(myObj)
            myReader.nextLine()
            while (myReader.hasNextLine()) {
                val data: String = myReader.nextLine()
                val st = StringTokenizer(data, ";")
                while (st.hasMoreTokens()) {
                    jugador.add(st.nextToken())
                }

                jugadores.add(jugador.clone() as ArrayList<String>)
                //jugadores.add(jugador.clone() as ArrayList<String>)
                jugador.clear()

            }
            myReader.close()
        } catch (e: FileNotFoundException) {
            println("An error occurred.")
            e.printStackTrace()
        }
        jugadores.removeAt(0)
        return jugadores
    }

    fun leerJugadores():ArrayList<Jugador>{
        val arregloJugadores = ArrayList<Jugador>()

        leerArchivo("Jugadores.txt").forEach {

            arregloJugadores.add(Jugador(
                it[0],
                it[1],
                it[2],
                it[3],
                SimpleDateFormat("dd/MM/yyyy").parse(it[4]),
                it[5].toBoolean(),
                it[6].toDouble()))
        }
        return arregloJugadores
    }

    fun leerClubs():ArrayList<Club>{
        val arregloClubs = ArrayList<Club>()
        //println("entro 1")
        leerArchivo("Clubes.txt").forEach {
            arregloClubs.add(Club(
                it[0].toString(),
                it[1].toString(),
                it[2].toString(),
                it[3].toString(),
                it[4].toInt(),
                it[5].toString()
            )
            )
        }
        //println(arregloClubs)
        return arregloClubs
    }


}