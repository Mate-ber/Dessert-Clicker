package tat.mukhutdinov.lesson9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tat.mukhutdinov.lesson9.ui.theme.DessertClickerTheme

enum class Screen(val titleRes: Int) {
    Start(R.string.start_title),
    Entree(R.string.entree_menu_title),
    SideDish(R.string.side_dish_menu_title),
    Accompaniment(R.string.accompaniment_title),
    Checkout(R.string.checkout_title)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DessertClickerTheme {
                LunchTrayApp()
            }
        }
    }
}

@Composable
fun LunchTrayApp() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Start.name

    val currentScreen = try {
        Screen.valueOf(currentRoute)
    } catch (e: Exception) {
        Screen.Start
    }

    val canNavigateBack = navController.previousBackStackEntry != null && currentScreen != Screen.Start

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                canNavigateBack = canNavigateBack,
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { contentPadding ->
        LunchTrayNavHost(navController = navController, modifier = Modifier.padding(contentPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    canNavigateBack: Boolean,
    currentScreen: Screen,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = currentScreen.titleRes)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_up)
                    )
                }
            }
        }
    )
}

@Composable
fun LunchTrayNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.Start.name, modifier = modifier) {

        composable(Screen.Start.name) {
            StartScreen(
                onStartOrder = { navController.navigate(Screen.Entree.name) }
            )
        }

        composable(Screen.Entree.name) {
            MenuScreen(
                titleRes = Screen.Entree.titleRes,
                onNext = { navController.navigate(Screen.SideDish.name) },
                onCancel = { navController.popBackStack(Screen.Start.name, inclusive = false) }
            )
        }

        composable(Screen.SideDish.name) {
            MenuScreen(
                titleRes = Screen.SideDish.titleRes,
                onNext = { navController.navigate(Screen.Accompaniment.name) },
                onCancel = { navController.popBackStack(Screen.Start.name, inclusive = false) }
            )
        }

        composable(Screen.Accompaniment.name) {
            MenuScreen(
                titleRes = Screen.Accompaniment.titleRes,
                onNext = { navController.navigate(Screen.Checkout.name) },
                onCancel = { navController.popBackStack(Screen.Start.name, inclusive = false) }
            )
        }

        composable(Screen.Checkout.name) {
            CheckoutScreen(
                onSubmit = { navController.popBackStack(Screen.Start.name, inclusive = false) },
                onCancel = { navController.popBackStack(Screen.Start.name, inclusive = false) }
            )
        }
    }
}

@Composable
fun StartScreen(onStartOrder: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.start_welcome_text))
        Button(
            onClick = onStartOrder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.start_order))
        }
    }
}

@Composable
fun MenuScreen(titleRes: Int, onNext: () -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(titleRes))
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.next))
        }
        Button(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = stringResource(R.string.cancel))
        }
    }
}

@Composable
fun CheckoutScreen(onSubmit: () -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.checkout_title))
        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.submit))
        }
        Button(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = stringResource(R.string.cancel))
        }
    }
}
