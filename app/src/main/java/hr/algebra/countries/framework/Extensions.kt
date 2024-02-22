package hr.algebra.countries.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.countries.COUNTRIES_PROVIDER_CONTENT_URI
import hr.algebra.countries.model.Country

fun View.applyAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(
        Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
inline fun <reified T : Activity> Context.startActivity(key: String, value: Int) =
    startActivity(
        Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key, value)
        })

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean = true)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .edit()
    .putBoolean(key, value)
    .apply()

fun Context.getBooleanPreference(key: String)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .getBoolean(key, false)

fun Context.getStringPreference(key: String)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .getString(key, "en")

fun Context.setStringPreference(key: String, value: String)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .edit()
    .putString(key, value)
    .apply()

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let {network ->
        connectivityManager.getNetworkCapabilities(network)?.let {networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

@SuppressLint("Range")
fun Context.fetchItems() : MutableList<Country> {
    val items = mutableListOf<Country>()

    val cursor = contentResolver.query(
        COUNTRIES_PROVIDER_CONTENT_URI, null, null, null, null
    )
    while (cursor != null && cursor.moveToNext()){
        items.add(Country(
            cursor.getLong(cursor.getColumnIndexOrThrow(Country::_id.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::cca3.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::name.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::nativeName.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::capital.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::flag.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::coatOfArmsPath.name)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(Country::latitude.name)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(Country::longitude.name)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(Country::area.name)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Country::population.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::currencies.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::languages.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::maps.name)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Country::read.name))==1
        ))
    }

    return items
}