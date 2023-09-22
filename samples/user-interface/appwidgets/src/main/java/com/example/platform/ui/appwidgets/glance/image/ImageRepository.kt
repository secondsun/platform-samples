/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.platform.ui.appwidgets.glance.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.File


class ImageRepository(val context: Context,val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val TAG: String = "ImageRepository"

    sealed interface ImageInfo {
        object Loading : ImageInfo

        data class Available(
            val image: Bitmap,
        ) : ImageInfo

        data class Unavailable(val message: String) : ImageInfo
    }

    val _image = MutableStateFlow<ImageInfo>(ImageInfo.Loading)
    val image : StateFlow<ImageInfo> get() = _image
    fun loadImage() {
        dispatcher.run {
            val file = File(context.cacheDir, "widget_image.png");

            if (!file.exists()) {
                _image.value = ImageInfo.Unavailable("File Does Not Exist")

            } else {

            try {
                file.inputStream().use { fileStream ->
                    val bitmap = BitmapFactory.decodeStream(fileStream)
                    _image.value =  ImageInfo.Available(bitmap)
                }
                } catch (e: Exception) {
                    Log.e(TAG, e.message, e)
                    _image.value =  ImageInfo.Unavailable(e.message ?: "Exception")
                }
            }
        }
    }
}