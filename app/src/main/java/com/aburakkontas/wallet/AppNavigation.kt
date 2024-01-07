import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Layout
import com.aburakkontas.wallet.screens.Deposit
import com.aburakkontas.wallet.screens.Home
import com.aburakkontas.wallet.screens.Login
import com.aburakkontas.wallet.screens.Register
import com.aburakkontas.wallet.screens.Send
import com.aburakkontas.wallet.screens.Settings
import com.aburakkontas.wallet.screens.Transactions
import com.aburakkontas.wallet.screens.Withdraw

@RequiresApi(Build.VERSION_CODES.O)
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
        composable("send") {
            Layout(navController = navController, liveData = liveData) {
                Send(liveData = liveData, navController = navController)
            }
        }
        composable("deposit") {
            Layout(navController = navController, liveData = liveData) {
                Deposit(liveData = liveData, navController = navController)
            }
        }
        composable("withdraw") {
            Layout(navController = navController, liveData = liveData) {
                Withdraw(liveData = liveData, navController = navController)
            }
        }
        composable("settings") {
            Layout(navController = navController, liveData = liveData) {
                Settings(liveData = liveData, navController = navController)
            }
        }
        composable("home") {
            Layout(navController = navController, liveData = liveData) {
                Home(liveData = liveData, navController = navController)
            }
        }
        composable("transactions") {
            Layout(navController = navController, liveData = liveData) {
                Transactions(liveData = liveData, navController = navController)
            }
        }

    }
}