package bastidas.felipe.peliculasapp.vistas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import bastidas.felipe.peliculasapp.modelos.Usuario
import bastidas.felipe.peliculasapp.viewmodels.UsuarioViemModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource

@Composable
fun UsuarioScreen(viewModel: UsuarioViemModel){
    val usuarios = viewModel.usuarios.value
    val context = LocalContext.current

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    /*Toast.makeText(context, "Click here", Toast.LENGTH_SHORT).show()*/
                    Log.d("text", "hola")
                }
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
            items(usuarios){ usuario ->
                UsuarioCard(usuario)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}





@Composable
fun UsuarioCard(usuario: Usuario){

    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
/*            Image(
                painter = painterResource(usuario.foto),
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxWidth()
            )*/
            Text(text = usuario.nombre)
            Text(text = usuario.correo)
            Text(text = usuario.edad.toString())

        }
    }
}