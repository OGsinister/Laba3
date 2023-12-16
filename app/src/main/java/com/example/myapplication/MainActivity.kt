package com.example.myapplication

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.Screens
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                val photoPicker = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia()
                ) {
                    if (it != null) {
                        viewModel.selectedImage.value = BitmapFactory.decodeStream(
                            context.contentResolver.openInputStream(it)
                        )
                        navController.navigate(Screens.ResultScreen.route)
                    } else {}
                }

                NavGraph(navController, photoPicker, viewModel)
            }
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    viewModel: MainViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ){
        composable(Screens.MainScreen.route){
            MainScreen(photoPicker = photoPicker, viewModel = viewModel)
        }
        
        composable(Screens.ResultScreen.route){
            ResultScreen(viewModel)
        }
    }
}
