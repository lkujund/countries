package hr.algebra.countries

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.countries.activity.DATA_IMPORTED
import hr.algebra.countries.activity.HostActivity
import hr.algebra.countries.framework.setBooleanPreference
import hr.algebra.countries.framework.startActivity

class CountriesReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        //FOREGROUND
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()

    }
}