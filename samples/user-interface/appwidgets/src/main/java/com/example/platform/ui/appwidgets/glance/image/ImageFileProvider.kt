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

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.lang.IllegalStateException

class ImageFileProvider : ContentProvider() {
    companion object
    {
        val T:String = ImageFileProvider::class.simpleName.toString()
    }

    override fun onCreate(): Boolean {
        return true;
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        throw IllegalStateException("Not Implemented")
    }

    override fun getType(uri: Uri): String? {
        return "image/png"
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val context: Context = this.context ?: return null
        val file =  File(context.cacheDir, "widget_image.png");

        if (!file.exists()) {
            Log.w(T, "File $file does not exist") // todo: remove logging?
            return null
        }
        Log.v(T, "Opening file $file")
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalStateException("Not Implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalStateException("Not Implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        throw IllegalStateException("Not Implemented")
    }

}