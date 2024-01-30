package com.example.cleanarchitecturehilt.lib.ext

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.cleanarchitecturehilt.lib.util.getUriPath
import com.example.cleanarchitecturehilt.lib.util.rotatedBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun MutableList<String>.createFormData(): Array<MultipartBody.Part> =
    mutableListOf<MultipartBody.Part>().apply {
        this@createFormData.forEach { data ->
            val file = File(data)
//            val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            logE("file - contentType: ${file.path}")
            logE("file - name: ${file.name}")
            logE("body - contentType: ${body.contentType()}")
            logE("body - contentLength: ${body.contentLength()}")
            add(MultipartBody.Part.createFormData("FILE", file.name, body))
        }
    }.toTypedArray()

fun String.createFormData(parm: String, name: String): MultipartBody.Part {
    val file = File(this@createFormData)
    val body = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    logE("file - contentType: ${file.path}")
    logE("file - name: $name")
    logE("body - contentType: ${body.contentType()}")
    logE("body - contentLength: ${body.contentLength()}")
    return MultipartBody.Part.createFormData(parm, name, body)
}

fun Context.createFormData(path: String?, parm: String, name: String): MultipartBody.Part? {
    val imgUri = Uri.parse(path)

    var file: File? = File(this@createFormData.cacheDir, name)
    file?.createNewFile()

    file = getRealPathFromURI(this, imgUri)


    file?.let {
        when {
            it.length() > (50 * 1024 * 1024) -> {
                file = resizeFile(it, imgUri.getUriPath(this) ?: "", name, 20, 20)
            }

            it.length() > (25 * 1024 * 1024) -> {
                file = resizeFile(it, imgUri.getUriPath(this) ?: "", name, 10, 10)
            }

            it.length() > (10 * 1024 * 1024) -> {
                file = resizeFile(it, imgUri.getUriPath(this) ?: "", name, 5, 5)
            }

            it.length() > (5 * 1024 * 1024) -> {
                file = resizeFile(it, imgUri.getUriPath(this) ?: "", name, 2, 2)
            }
        }

        logE("fileLength = ${it.length()}")
    }

    val body = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(parm, name, body!!)
}

fun getRealPathFromURI(context: Context, contentUri: Uri): File? {
    var cursor: Cursor? = null
    try {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, projection, null, null, null)
        Log.e("TEST", "1")
        if (cursor != null && cursor.moveToFirst()) {

            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            Log.e("TEST", "3 = " + cursor.getString(columnIndex))
            val contentUri = cursor.getString(columnIndex)
            val file = File(contentUri)
            Log.e("TEST", "file1 = ${file}")
            Log.e("TEST", "file2 = ${file.length()}")
            return file
        }
    } finally {
        cursor?.close()
    }
    return null
}


fun Context.resizeFile(file: File, uriString: String, name: String, w: Int, h: Int): File {
    var bitmap: Bitmap? = null
    try {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitmap = BitmapFactory.decodeStream(FileInputStream(file), null, options)

        bitmap = bitmap?.rotatedBitmap(uriString)

        bitmap = bitmap?.let {
            Bitmap.createScaledBitmap(
                it,
                (it.width / w),
                (it.height / h),
                false
            )
        }


    } catch (e: Exception) {
        logE("$e")
    }

    val tempFile = File(this.cacheDir, name)
    tempFile.createNewFile()

    val bos = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    val bitmapData = bos.toByteArray()

    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(tempFile)
    } catch (e: FileNotFoundException) {
        Log.e("createFormData1", "$e")
    }
    try {
        fos?.write(bitmapData)
        fos?.flush()
        fos?.close()
    } catch (e: IOException) {
        Log.e("createFormData2", "$e")
    }

    return tempFile
}
