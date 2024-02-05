package com.example.webviewexam.lib.ext

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * EditText 글자수 카운팅 및 제한
 */
fun setOnTextWatcher(
    maxCount: Int = 100,
    counter: ((String) -> Unit)? = null
): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}


        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            val bytes: ByteArray?
            try {
                bytes = (text?.toString()?.trim() ?: "").toByteArray(Charset.forName("KSC5601"))
                counter?.invoke("(${bytes.size}/${maxCount})")
            } catch (e: UnsupportedEncodingException) {
                Log.e("onTextChanged", "$e")
            }
        }

        override fun afterTextChanged(s: Editable?) {
            val str = s?.toString()?.trim() ?: ""
            try {
                if (str.contains("'")) {
                    s?.delete(s.length - 1, s.length)
                } else {
                    val strBytes = str.toByteArray(Charset.forName("KSC5601"))
                    if (strBytes.size > maxCount) {
                        s?.delete(s.length - 2, s.length - 1)
                        counter?.invoke("(${maxCount}/${maxCount})")
                    }
                }
            } catch (e: UnsupportedEncodingException) {
                Log.e("afterTextChanged", "$e")
            }
        }
    }
}


//fun setOnTextWatcher(maxByte: Int = 20, block: (Boolean) -> Unit): TextWatcher {
//    return object : TextWatcher {
//        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}
//
//
//        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
//            val bytes: ByteArray?
//            try {
//                bytes = (text?.toString()?.trim() ?: "").toByteArray(Charset.forName("KSC5601"))
////                block.invoke()
//            } catch (e: UnsupportedEncodingException) {
//                Log.e("onTextChanged", "$e")
//            }
//        }
//
//        override fun afterTextChanged(s: Editable?) {
//
//        }
//    }
//}