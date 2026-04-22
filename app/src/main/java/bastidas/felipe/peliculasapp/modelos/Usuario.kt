package bastidas.felipe.peliculasapp.modelos

class Usuario(
    val id: Int,
    var nombre: String,
    var correo: String,
    var edad: Int,
    var foto: Int? = null,
    var fotoUrl: String? = null
)