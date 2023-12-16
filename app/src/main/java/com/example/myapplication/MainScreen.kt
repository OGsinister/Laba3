package com.example.myapplication

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun MainScreen(
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    viewModel: MainViewModel
) {
    var showPhotoPicker = remember{ mutableStateOf(false) }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_waves)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier
            .background(Color.Black.copy(0.89f))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        if(showPhotoPicker.value) {
            PickPhoto(photoPicker)
            showPhotoPicker.value = !showPhotoPicker.value
        }

        Box{
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    showPhotoPicker.value = !showPhotoPicker.value
                }) {
                Text("click")
            }
        }
    }
}

@Composable
fun PickPhoto(photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>){
    photoPicker.launch(
        PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
    )
}

