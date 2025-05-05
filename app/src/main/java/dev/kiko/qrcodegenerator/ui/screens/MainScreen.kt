package dev.kiko.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kiko.qrcodegenerator.ui.icons.History
import dev.kiko.qrcodegenerator.ui.icons.QrCode
import dev.kiko.qrcodegenerator.ui.icons.QrReader
import dev.kiko.qrcodegenerator.ui.icons.ValkyrieIcons
import dev.kiko.qrcodegenerator.ui.screens.generator.GeneratorScreen
import dev.kiko.qrcodegenerator.ui.screens.history.HistoryScreen
import dev.kiko.qrcodegenerator.ui.screens.scanner.ScannerScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    
    val items = listOf(
        NavigationItem("Генератор", ValkyrieIcons.QrCode, "generator"),
        NavigationItem("Сканер", ValkyrieIcons.QrReader, "scanner"),
        NavigationItem("История", ValkyrieIcons.History, "history")
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "generator") {
                composable("generator") {
                    GeneratorScreen()
                }
                composable("scanner") {
                    ScannerScreen()
                }
                composable("history") {
                    HistoryScreen()
                }
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)
