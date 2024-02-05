package com.example.webviewexam.lib.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface

fun getOrientationOfImage(filepath : String): Int? {
    var exif : ExifInterface? = null
    var result: Int? = null

    try{
        exif = ExifInterface(filepath)
    }catch (e: Exception){
        e.printStackTrace()
        return -1
    }

    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)
    if(orientation != -1){
        result = when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    }
    return result
}

fun Bitmap?.rotatedBitmap(filepath: String): Bitmap? {
    val matrix = Matrix()
    var resultBitmap : Bitmap? = null

    when(getOrientationOfImage(filepath)){
        0 -> matrix.setRotate(0F)
        90 -> matrix.setRotate(90F)
        180 -> matrix.setRotate(180F)
        270 -> matrix.setRotate(270F)
    }

    resultBitmap = try{
        this?.let { Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true) }
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
    return resultBitmap
}