package bastidas.felipe.peliculasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import bastidas.felipe.peliculasapp.R
import bastidas.felipe.peliculasapp.modelos.Repositorio
import bastidas.felipe.peliculasapp.modelos.Usuario

class UsuarioViemModel(val repo: Repositorio): ViewModel() {

    private val _usuarios = mutableStateOf<List<Usuario>>(emptyList())
    val usuarios: State<List<Usuario>> = _usuarios

    init{
        getUsuarios()
    }

    private fun getUsuarios(){
        _usuarios.value = repo.getUsuarios()
    }

/*    fun addUsuario(nombre: String, correo: String, edad: Int, fotoUrl: String){
        val nuevoId = _usuarios.value.size + 1
        val usu = Usuario(nuevoId,nombre,correo,edad, R.drawable.avatar, fotoUrl)
        repo.addUsuario(usu)

        _usuarios.value = repo.getUsuarios()
    }*/

}