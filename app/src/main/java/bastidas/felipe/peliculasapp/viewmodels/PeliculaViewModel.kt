package bastidas.felipe.peliculasapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import bastidas.felipe.peliculasapp.modelos.Pelicula
import androidx.compose.runtime.State
import bastidas.felipe.peliculasapp.R
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

    fun addPeliculas(
        titulo: String,
        sinopsis: String,
        genero: String,
        annoLanzamiento: Int,
        duracion: String,
        uri: String?){
        val nuevoId = _peliculas.value.size + 1

        val imagenPorDefecto = if (uri != null) 0 else R.drawable.sdla
        val pel = Pelicula(nuevoId,titulo,sinopsis,genero,annoLanzamiento,duracion, imagenPorDefecto, uri)
        repo.addPeliculas(pel)

        _peliculas.value = repo.getPeliculas()
    }

    fun delPeliculas(
        titulo: String,
        sinapsis: String,
        genero: String,
        annoLanzamiento: Int,
        duracion: String,
        uri: String?

    ){
        val imagenPorDefecto = if (uri != null) 0 else R.drawable.sdla
        val pel = Pelicula(0,titulo,sinapsis,genero,annoLanzamiento,duracion, imagenPorDefecto, uri)
        repo.delPeliculas(pel)

        _peliculas.value = repo.getPeliculas()
    }
}