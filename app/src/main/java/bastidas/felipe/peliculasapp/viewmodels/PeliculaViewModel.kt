package bastidas.felipe.peliculasapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import bastidas.felipe.peliculasapp.modelos.Pelicula
import androidx.compose.runtime.State
import bastidas.felipe.peliculasapp.modelos.PeliculaRepositorio

class PeliculaViewModel(val repo: PeliculaRepositorio): ViewModel() {

    private val _peliculas = mutableStateOf<List<Pelicula>>(emptyList())

    val peliculas: State<List<Pelicula>> = _peliculas

    init {
        getPeliculas()
    }

    private fun getPeliculas() {
        _peliculas.value = repo.getPeliculas()
    }
}