package com.example.webviewexam.lib.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow


//suspend fun List<String>.ConvertWhenList(context : Context) : List<Bitmap> {
//    val result = mutableListOf<String>()
//    val hi = CoroutineScope(Dispatchers.Default).async {
//        for (i in this@ConvertWhenList) {
//            val job = async(Dispatchers.IO) {
//                i.resizeImage(context)
//            }.also { it.join() }
//
//            result.add(job.await())
//        }
//        result
//    }
//    return hi.await()
//}

fun String.ConvertWhenSingle(context : Context) : Bitmap {
    return this.resizeImage(context)
}

private fun String.resizeImage(context : Context) : Bitmap {
    return Bitmap.createScaledBitmap(this.convertUriToBitmap(context), 1920, 1080, true)

//    File(
//        Bitmap.createScaledBitmap(this.convertUriToBitmap(context), 1920, 1080, true)
//            .convertToUri(context).toString()).path
}

private fun String.convertUriToBitmap(context: Context) : Bitmap {
    return if(Build.VERSION.SDK_INT >= 28) ImageDecoder.decodeBitmap(ImageDecoder.createSource(File(this)))
    else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, this.toUri())
    }
}

private fun Bitmap.convertToUri(context : Context) : Uri {
    return Uri.parse(MediaStore.Images.Media.insertImage(context.contentResolver, this, this.toString(), null))
}