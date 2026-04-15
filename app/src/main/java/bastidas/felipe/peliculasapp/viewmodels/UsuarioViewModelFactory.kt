package bastidas.felipe.peliculasapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bastidas.felipe.peliculasapp.modelos.Repositorio

class UsuarioViewModelFactory(private val repo: Repositorio): ViewModelProvider.Factory {

    override fun <T: ViewModel > create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViemModel::class.java)){
            return UsuarioViemModel(repo) as T
        }
        throw IllegalArgumentException("Desconocido")
    }
}