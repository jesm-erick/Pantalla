package pe.edu.upeu.proyectovcmjc.ui.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import pe.edu.upeu.proyectovcmjc.ui.navigation.Destinations.*
import pe.edu.upeu.proyectovcmjc.ui.presentation.screens.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import pe.edu.upeu.proyectovcmjc.ui.presentation.screens.Pantalla3
import pe.edu.upeu.proyectovcmjc.ui.presentation.screens.persona.PersonaForm
import pe.edu.upeu.proyectovcmjc.ui.presentation.screens.persona.PersonaUI
import pe.edu.upeu.proyectovcmjc.ui.presentation.screens.PantallaQR as PantallaQR

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    darkMode: MutableState<Boolean>
) {
    NavHost(
        navController = navController, startDestination = Destinations.Pantalla1.route
    ) {


        composable(Destinations.Pantalla1.route) {
            Pantalla1(
                navegarPantalla2 = { newText ->
                    navController.navigate(Destinations.Pantalla2.createRoute(newText))
                }
            )
        }

        composable(
            Destinations.Pantalla2.route,
            arguments = listOf(navArgument("newText") {
                defaultValue =
                    "Pantalla 2"
            })
        ) { navBackStackEntry ->
            var newText =
                navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
            Pantalla2(newText, darkMode)
        }


        composable(Destinations.Pantalla3.route) {
            Pantalla3()
        }


        composable(Destinations.PantallaQR.route) {
            PantallaQR(navController)
        }

        composable(Pantalla4.route) {
            Pantalla4()
        }

        composable(Destinations.PersonaUI.route){
            PersonaUI(navegarEditarPer = { newText ->
                navController.navigate(Destinations.PersonaForm.passId(newText))
            } )
        }
        composable(
            Destinations.PersonaForm.route,
            arguments = listOf(navArgument("perId") {
                defaultValue =
                    "perId"
            })
        ) { navBackStackEntry ->
            var perId =
                navBackStackEntry.arguments?.getString("perId")
            requireNotNull(perId)
            PersonaForm(perId, darkMode, navController)
        }

    }
}