package com.chow.alebeer.other

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object FileUtils {
    private const val OUTPUT_DIRECTORY = "BeerImage"

    fun saveToInternalStorage(
        bitmapInput: Bitmap,
    ): String? {
        val cw = ContextWrapper(Resources.context)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir(OUTPUT_DIRECTORY, Context.MODE_PRIVATE)
        // Create imageDir
        val output = File(
            directory,
            "${System.currentTimeMillis()}.png"
        )
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(output)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapInput.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return output.path
    }
}