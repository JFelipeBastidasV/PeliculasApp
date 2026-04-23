package bastidas.felipe.peliculasapp.vistas

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import bastidas.felipe.peliculasapp.modelos.Pelicula
import bastidas.felipe.peliculasapp.viewmodels.PeliculaViewModel
import coil3.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeliculaScreen(viewModel: PeliculaViewModel) {
    val peliculas = viewModel.peliculas.value
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    var pelicula_editable by remember { mutableStateOf<Pelicula?>(null) }

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
                    onLongClick = {
                        pelicula_editable = pelicula
                    },
                    onDeleteClick = {
                        viewModel.delPeliculas(
                            titulo = pelicula.titulo,
                            sinopsis = pelicula.sinopsis,
                            genero = pelicula.genero,
                            annoLanzamiento = pelicula.annoLanzamiento,
                            duracion = pelicula.duracion,
                            pelicula.fotoUri
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
                onConfirm = { titulo, sinopsis, genero, anno, duracion, uri->
                    viewModel.addPeliculas(
                        titulo,
                        sinopsis,
                        genero,
                        anno,
                        duracion,
                        uri)
                    showDialog = false
                    Toast.makeText(
                        context,
                        "Película agregada",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }

        pelicula_editable?.let { pelicula ->
            EditarPeliculaDialog(
                pelicula = pelicula,
                onDismiss = { pelicula_editable = null},
                onConfirm = { id , titulo, sinopsis, genero, annoInt, duracion, fotoUri  ->
                    viewModel.editarPelicula(id,titulo,sinopsis,genero,annoInt,duracion,fotoUri)
                    pelicula_editable = null
                }
            )

        }
    }
}

@Composable
fun PeliculaCard(pelicula: Pelicula, onLongClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().combinedClickable(
            onClick = {},
            onLongClick = onLongClick
        ),
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

                if (pelicula.fotoUri != null) {
                    AsyncImage(
                        model = pelicula.fotoUri,
                        contentDescription = "Layer",
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Image(
                        painter = painterResource(pelicula.foto),
                        contentDescription = "Avatar",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Text(
                    text = pelicula.titulo,
                    style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Sinopsis: ${pelicula.sinopsis}",
                    style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Género: ${pelicula.genero}",
                    style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Año: ${pelicula.annoLanzamiento}",
                    style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Duración: ${pelicula.duracion}",
                    style = MaterialTheme.typography.bodySmall)
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
    onConfirm: (String, String, String, Int, String, String?) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var anno by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf<Uri?>(null) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()

    ) { uri: Uri? ->
        foto = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Nueva Película") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable{
                            launcher.launch("image/*")
                        }
                ) {
                    if (foto != null){
                        AsyncImage(
                            model = foto,
                            contentDescription =  "Layer",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Layer",
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }
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
                    onConfirm(titulo, sinopsis, genero, annoInt, duracion, foto?.toString())
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


@Composable
fun EditarPeliculaDialog(
    pelicula: Pelicula,
    onDismiss: () -> Unit,
    onConfirm: (Int, String, String, String, Int, String, String?) -> Unit
) {
    var titulo by remember { mutableStateOf(pelicula.titulo) }
    var sinopsis by remember { mutableStateOf(pelicula.sinopsis) }
    var genero by remember { mutableStateOf(pelicula.genero) }
    var anno by remember { mutableStateOf(pelicula.annoLanzamiento.toString()) }
    var duracion by remember { mutableStateOf(pelicula.duracion) }
    var foto by remember { mutableStateOf<Uri?>(pelicula.fotoUri?.let { Uri.parse(it) }) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()

    ) { uri: Uri? ->
        foto = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar pelicula") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable{
                            launcher.launch("image/*")
                        }
                ) {
                    if (foto != null){
                        AsyncImage(
                            model = foto,
                            contentDescription =  "Layer",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Layer",
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }
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
                    onConfirm(pelicula.id, titulo, sinopsis, genero, annoInt, duracion, foto?.toString())
                }
            ) {
                Text("Editar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}