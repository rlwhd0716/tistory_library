package com.example.webviewexam.lib.ext

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*

inline fun <reified T> Context.repeatAlarmTimer(requestCode: Int = 17009, SECOND: Long = 60) {
    logD("repeatAlarmTimer")
    val sender = PendingIntent.getBroadcast(
        this,
        requestCode,
        Intent(this, T::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )
    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).also { aM ->
        (1000 * SECOND).let {
            aM.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 1000,
                it,
                sender
            )
        }
    }
}

inline fun <reified T> Context.stopRepeatAlarmTimer(requestCode: Int = 17009) {
    logD("stopRepeatAlarmTimer")
    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).also { aM ->
        aM.cancel(
            PendingIntent.getBroadcast(
                this,
                requestCode,
                Intent(this, T::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}

fun Context.repeatAlarmTimerJava(cls: Class<*>, requestCode: Int = 17009, SECOND: Long = 60) {
    logD("repeatAlarmTimerJava")
    val sender = PendingIntent.getBroadcast(
        this,
        requestCode,
        Intent(this, cls),
        PendingIntent.FLAG_IMMUTABLE
    )
    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).also { aM ->
        (1000 * SECOND).let {
            aM.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 1000,
                it,
                sender
            )
        }
    }
}

fun Context.stopRepeatAlarmTimerJava(cls: Class<*>, requestCode: Int = 17009) {
    logD("stopRepeatAlarmTimerJava")
    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).also { aM ->
        aM.cancel(
            PendingIntent.getBroadcast(
                this,
                requestCode,
                Intent(this, cls),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T> Context.setAlarmTimer(amount: Int = 1) {
    logD("setAlarmTimer")
    val sender = PendingIntent.getBroadcast(
        this,
        0,
        Intent(this, T::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )

    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).apply {
        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.SECOND, amount)
        }.timeInMillis.let { set(AlarmManager.RTC_WAKEUP, it, sender) }
    }
}

@SuppressLint("ObsoleteSdkInt")
fun Context.setAlarmTimerJava(cls: Class<*>, amount: Int = 1) {
    logD("setAlarmTimer")
    val sender =
        PendingIntent.getBroadcast(this, 0, Intent(this, cls), PendingIntent.FLAG_IMMUTABLE)

    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).apply {
        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.SECOND, amount)
        }.timeInMillis.let { set(AlarmManager.RTC_WAKEUP, it, sender) }
    }
}

fun Context.actionApplicationDetailsSettings(block: Intent.() -> Unit = {}) =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:$packageName"))
        .apply(block)

fun Context.sendBroadcast(block: Intent.() -> Unit) {
    sendBroadcast(Intent().apply(block))
}

@Suppress("DEPRECATION")
@SuppressLint("InvalidWakeLockTag", "WakelockTimeout")
fun Context.acquire() {
    (getSystemService(Context.POWER_SERVICE) as PowerManager).newWakeLock(
        PowerManager.SCREEN_BRIGHT_WAKE_LOCK or
                PowerManager.ACQUIRE_CAUSES_WAKEUP or
                PowerManager.ON_AFTER_RELEASE, "service"
    ).apply {
        acquire(1000)
    }
}

fun Context.callPhone(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    startActivity(intent)
}

@OptIn(DelicateCoroutinesApi::class)
fun AppCompatActivity.showKeyboard() {
    GlobalScope.launch {
        delay(100)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(window.decorView, 0)
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun AppCompatActivity.hideKeyboard() {
    GlobalScope.launch {
        delay(100)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }
}


@SuppressLint("HardwareIds")
fun Context.uuid(): String {
    val guid =
        ByteBuffer.wrap(UUID.randomUUID().toString().toByteArray(charset("UTF-8"))).long.toString(
            Character.MAX_RADIX
        )
    val uuid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    return uuid.takeUnless { it.isEmpty() } ?: run { guid }
}

@SuppressLint("HardwareIds")
fun Context.line1Number() = if (
    ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_SMS
    ) != PackageManager.PERMISSION_GRANTED
    && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_PHONE_NUMBERS
    ) != PackageManager.PERMISSION_GRANTED
    && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_PHONE_STATE
    ) != PackageManager.PERMISSION_GRANTED
) uuid() else (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).line1Number.let {
    it?.run {
        when {
            it.isBlank() -> uuid()
            else -> it.replace("+82", "0")
        }
    } ?: uuid()
}

fun Context.deviceUuid() = line1Number()

inline fun <reified T> Context.simpleSelectableListItem(block: ArrayAdapter<T>.() -> Unit) =
    ArrayAdapter<T>(this, R.layout.simple_selectable_list_item).apply(block)

//inline fun <reified T> Context.showListAlertDialog(title: String = "목록", cancelText: String = "취소", list: List<T>, crossinline map: (T) -> String, crossinline selected: (T) -> Unit) {
//    showAlertDialogApply {
//        setTitle(title)
//        setNegativeButton(cancelText) { d, _ -> d.dismiss() }
//        setAdapter(simpleSelectableListItem<String> { addAll(list.map(map)) }) { d, w ->
//            selected.invoke(list[w])
//            d.dismiss()
//        }
//    }
//}

@Suppress("DEPRECATION")
inline fun <reified T> Context.firstNotRunningTask(block: () -> Unit) {
    (getSystemService(Activity.ACTIVITY_SERVICE) as? ActivityManager)?.run {
        if (T::class.qualifiedName != getRunningTasks(
                1
            )[0].topActivity?.className
        ) block()
    }
}

@Suppress("DEPRECATION")
inline fun <reified T> Context.findRunningService() =
    (getSystemService(Activity.ACTIVITY_SERVICE) as? ActivityManager)?.run {
        getRunningServices(Int.MAX_VALUE).forEach { s -> if (T::class.qualifiedName == s.service.className) return@run true }
        return@run false
    } ?: false

@Suppress("DEPRECATION")
fun Context.findRunningServiceJava(cls: Class<*>) =
    (getSystemService(Activity.ACTIVITY_SERVICE) as? ActivityManager)?.run {
        getRunningServices(Int.MAX_VALUE).forEach { s -> if (cls.kotlin.qualifiedName == s.service.className) return@run true }
        return@run false
    } ?: false


inline fun <reified T : Context> Context.startActivity(
    noinline block: (Intent.() -> Unit)? = null,
    noinline action: (() -> Unit)? = null
) {
    startActivity(Intent(this, T::class.java).apply {
        block?.let { this.apply(block) }
    })
    action?.invoke()
}