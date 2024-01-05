import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Layout
import com.aburakkontas.wallet.screens.ForgotPassword
import com.aburakkontas.wallet.screens.History
import com.aburakkontas.wallet.screens.Home
import com.aburakkontas.wallet.screens.Login
import com.aburakkontas.wallet.screens.Profile
import com.aburakkontas.wallet.screens.Register
import com.aburakkontas.wallet.screens.Send

@Composable
fun AppNavigation(liveData: LiveData) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
    composable("login") {
        Login(navController = navController, liveData = liveData)
    }
    composable("register") {
        Register(navController = navController, liveData = liveData)
    }
    composable("forgot_password") {
        ForgotPassword(navController = navController, liveData = liveData)
    }
    composable("send") {
        Layout(navController = navController, liveData = liveData) {
            Send(liveData = liveData)
        }
    }
    composable("history") {
        Layout(navController = navController, liveData = liveData) {
            History(liveData = liveData)
        }
    }
    composable("profile") {
        Layout(navController = navController, liveData = liveData) {
            Profile(liveData = liveData)
        }
    }
    composable("home") {
            Layout(navController = navController, liveData = liveData) {
                Home(liveData = liveData)
            }
    }
    }
}