class Club (
    id:String,
    nombre:String,
    presidente:String,
    directorTecnico: String,
    copas:Int,
    serie:String
){
    var idClub:String = id
    var nombreClub:String = nombre
    var presidenteClub:String= presidente
    var directorTecnicoClub:String = directorTecnico
    var copasClub:Int = copas
    var serieClub:String=serie

    fun devolverString():String{
        return (idClub+";"+
                nombreClub+";"+
                presidenteClub+";"+
                directorTecnicoClub+";"+
                copasClub.toString()+";"+
                serieClub+";")
    }

}