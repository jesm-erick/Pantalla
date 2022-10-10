package pe.edu.upeu.proyectovcmjc.ui.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Pantalla1: Destinations("pantalla1", "Pantalla 1",
        Icons.Filled.Home)
    object Pantalla2: Destinations("pantalla2/?newText={newText}",
        "Pantalla 2", Icons.Filled.Settings) {
        fun createRoute(newText: String) = "pantalla2/?newText=$newText"
    }
    object Pantalla3: Destinations("pantalla3", "Pantalla 3", Icons.Filled.Favorite)
    object PantallaQR: Destinations("pantallaQR", "Pantalla QR", Icons.Filled.Deck)

    object Pantalla4: Destinations("pantalla4", "Pantalla4", Icons.Filled.Deck)

    object PersonaUI: Destinations("Persona", "PErsona", Icons.Filled.Deck)
}
