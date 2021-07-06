import java.text.SimpleDateFormat
import java.util.*

class Jugador (
    id:String,
    idClub:String,
    nombre:String,
    apellido:String,
    fechaNacimiento: Date,
    titular:Boolean,
    sueldo:Double
){
    val formato = SimpleDateFormat("dd/MM/yyyy")
    var idJugador:String = id
    var idClubJugador:String = idClub
    var nombreJugador: String = nombre
    var apellidoJugador:String = apellido
    var fechaNacimientoJugador:Date = fechaNacimiento
    var titularJugador:Boolean = titular
    var sueldoJugador: Double = sueldo

    fun devolverString():String{
        /*
        var sueldoJ = ""
        if("." !in (sueldoJugador.toBigDecimal().toPlainString())){
            sueldoJ=sueldoJugador.toBigDecimal().toPlainString()+".0"
        }

         */
    val formato = SimpleDateFormat("dd/MM/yyyy")
        return (idJugador+";"+
                idClubJugador+";"+
                nombreJugador+";"+
                apellidoJugador +";"+
                formato.format(fechaNacimientoJugador)+";"+
                titularJugador+";"+
                sueldoJugador.toString()+";")
    }
}