package bastidas.felipe.peliculasapp.modelos

data class Pelicula(
    val id: Int,
    var titulo: String,
    var sinopsis: String,
    var genero: String,
    var annoLanzamiento: Int,
    var duracion: String

)