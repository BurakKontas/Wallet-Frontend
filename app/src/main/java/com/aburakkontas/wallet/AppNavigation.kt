import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val liveData = LiveData(context, navController)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(liveData = liveData)
        }
        composable("register") {
            Register(liveData = liveData)
        }
        composable("send") {
            Layout(liveData = liveData) {
                Send(liveData = liveData)
            }
        }
        composable("deposit") {
            Layout(liveData = liveData) {
                Deposit(liveData = liveData)
            }
        }
        composable("withdraw") {
            Layout(liveData = liveData) {
                Withdraw(liveData = liveData)
            }
        }
        composable("settings") {
            Layout(liveData = liveData) {
                Settings(liveData = liveData)
            }
        }
        composable("home") {
            Layout(liveData = liveData) {
                Home(liveData = liveData)
            }
        }
        composable("transactions") {
            Layout(liveData = liveData) {
                Transactions(liveData = liveData)
            }
        }

    }
}