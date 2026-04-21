package bastidas.felipe.peliculasapp.vistas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import bastidas.felipe.peliculasapp.modelos.Pelicula
import bastidas.felipe.peliculasapp.viewmodels.PeliculaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeliculaScreen(viewModel: PeliculaViewModel) {
    val peliculas = viewModel.peliculas.value
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Películas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(peliculas) { pelicula ->
                PeliculaCard(
                    pelicula = pelicula,
                    onDeleteClick = {
                        viewModel.delPeliculas(
                            titulo = pelicula.titulo,
                            sinapsis = pelicula.sinopsis,
                            genero = pelicula.genero,
                            annoLanzamiento = pelicula.annoLanzamiento,
                            duracion = pelicula.duracion
                        )
                        Toast.makeText(
                            context,
                            "Película eliminada",
                            Toast.LENGTH_SHORT).show()
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (showDialog) {
            AgregarPeliculaDialog(
                onDismiss = { showDialog = false },
                onConfirm = { titulo, sinopsis, genero, anno, duracion ->
                    viewModel.addPeliculas(titulo, sinopsis, genero, anno, duracion)
                    showDialog = false
                    Toast.makeText(
                        context,
                        "Película agregada",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun PeliculaCard(pelicula: Pelicula, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = pelicula.titulo, style = MaterialTheme.typography.titleLarge)
                Text(text = "Sinopsis: ${pelicula.sinopsis}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Género: ${pelicula.genero}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Año: ${pelicula.annoLanzamiento}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Duración: ${pelicula.duracion}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar Película",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun AgregarPeliculaDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, Int, String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var anno by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Nueva Película") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = sinopsis,
                    onValueChange = { sinopsis = it },
                    label = { Text("Sinopsis") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Género") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = anno,
                    onValueChange = { newValue ->
                        if (newValue.all { char -> char.isDigit() } && newValue.length <= 4) {
                            anno = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Año de Lanzamiento") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = duracion,
                    onValueChange = { duracion = it },
                    label = { Text("Duración (ej. 2h 10min)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val annoInt = anno.toIntOrNull() ?: 0
                    onConfirm(titulo, sinopsis, genero, annoInt, duracion)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}