package pe.edu.upeu.proyectovcmjc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import pe.edu.upeu.proyectovcmjc.ui.navigation.Destinations
import pe.edu.upeu.proyectovcmjc.ui.navigation.NavigationHost
import pe.edu.upeu.proyectovcmjc.ui.presentation.components.BottomNavigationBar
import pe.edu.upeu.proyectovcmjc.ui.presentation.components.Dialog
import pe.edu.upeu.proyectovcmjc.ui.presentation.components.Drawer
import pe.edu.upeu.proyectovcmjc.ui.presentation.components.TopBar
import pe.edu.upeu.proyectovcmjc.ui.theme.BLUE800
import pe.edu.upeu.proyectovcmjc.ui.theme.ProyectoVCMJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val darkMode = remember { mutableStateOf(false) }
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = BLUE800
                )
            }
            ProyectoVCMJCTheme(
                darkTheme = darkMode.value
            ){
                MainScreen(darkMode)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProyectoVCMJCTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen(
    darkMode: MutableState<Boolean>
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    val navigationItems = listOf(
        Destinations.Pantalla1,
        Destinations.Pantalla2,
        Destinations.Pantalla3,
        Destinations.PantallaQR,
        Destinations.Pantalla4,
    )
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar(navController = navController,
            items = navigationItems) },
        floatingActionButton = { FloatingActionButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Add, contentDescription =
            "Fab Icon")
        } },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopBar(
                scope,
                scaffoldState,
                openDialog = { openDialog.value = true },
                displaySnackBar = {
                    scope.launch {
                        val resultado =
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Nuevo SnackBar!",
                                duration = SnackbarDuration.Short,
                                actionLabel = "Aceptar"
                            )
                        when(resultado){
                            SnackbarResult.ActionPerformed -> {
                                Log.d("MainActivity", "Snackbar Accionado")
                            }
                            SnackbarResult.Dismissed -> {
                                Log.d("MainActivity", "Snackbar Ignorado")
                            }
                        }
                    }
                }
            )
        },
        drawerContent = { Drawer(scope, scaffoldState, navController,
            items = navigationItems) },
        drawerGesturesEnabled = true
    ){
        NavigationHost(navController, darkMode)
    }
    Dialog(showDialog = openDialog.value, dismissDialog = {
        openDialog.value = false })
}