package hr.algebra.countries.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.algebra.countries.R
import hr.algebra.countries.databinding.ActivityHostBinding
import android.Manifest

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel


    private val CHANNEL_ID: String = "Countries"
//    private val NOTIFICATION_ID: Int = 1
//    private val hostIntent = Intent(this, HostActivity::class.java)
//
//    val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, hostIntent, PendingIntent.FLAG_IMMUTABLE)
//
//    var builder = NotificationCompat.Builder(this, CHANNEL_ID)
//        .setSmallIcon(R.drawable.countries_splash)
//        .setContentTitle(getString(R.string.content_title))
//        .setContentText(getString(R.string.tap_text))
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        .setContentIntent(pendingIntent)
//        .setAutoCancel(true)
    override fun onStop() {
        super.onStop()
//        if (!isChangingConfigurations && !isFinishing)
//        {
//            with(NotificationManagerCompat.from(this)) {
//                if (ActivityCompat.checkSelfPermission(
//                        this@HostActivity,
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    // Consider calling
//                    // ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
//                    //                                        grantResults: IntArray)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//
//                    return@with
//                }
//                notify(NOTIFICATION_ID, builder.build())
//            }
//        }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        createNotificationChannel()
        initHamburgerMenu()
        initNavigation()
    }

private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        notificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun initNavigation() {
        val navController = Navigation.findNavController(this, R.id.navController)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                return true
            }
            R.id.menuExit -> {
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage(getString(R.string.really_exit_the_application))
            setIcon(R.drawable.exit)
            setCancelable(true)
            setNegativeButton(getString(R.string.cancel), null)
            setPositiveButton("OK") {_, _ -> finish()}
            show()
        }
    }

    private fun toggleDrawer() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}