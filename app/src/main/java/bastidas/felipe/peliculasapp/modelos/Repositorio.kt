package bastidas.felipe.peliculasapp.modelos

class Repositorio {

    private val usuarios = mutableListOf(
        Usuario(1,"Sebastian","a00227588@tec.mx",20),
        Usuario(5,"Ara","a01255000@tec.mx",20),
        Usuario(2,"LPP","a01254900@tec.mx",20),
        Usuario(3,"PayDay","a01250000@tec.mx",20),
        Usuario(4,"Nico","a01255150@tec.mx",20)
    )
    fun getUsuarios(): List<Usuario>{
        return usuarios.toList()
    }

    fun addUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }
}