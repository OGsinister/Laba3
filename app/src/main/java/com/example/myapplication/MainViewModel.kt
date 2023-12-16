package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ml.LiteModelAiyVisionClassifierBirdsV13
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.support.image.TensorImage

class MainViewModel(): ViewModel() {

    val selectedImage = mutableStateOf<Bitmap?>(null)

    var result = MutableStateFlow(Result())

    fun classifyImage(context: Context, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.Default) {
            val model = LiteModelAiyVisionClassifierBirdsV13.newInstance(context)
            val image = TensorImage.fromBitmap(bitmap)
            val outputs = model.process(image)

            val resultFromModel = outputs.probabilityAsCategoryList.apply {
                sortByDescending {
                    it.score
                }
            }

            withContext(Dispatchers.Main){
                result.value = Result(
                    label = resultFromModel[0].label,
                    score = resultFromModel[0].score
                )
            }

            model.close()
        }
    }

}

data class Result(
    var label: String? = "",
    var score: Float? = 0.0f
)