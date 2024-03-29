package hr.algebra.countries.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.countries.R
import hr.algebra.countries.api.CountriesWorker
import hr.algebra.countries.databinding.ActivitySplashScreenBinding
import hr.algebra.countries.framework.applyAnimation
import hr.algebra.countries.framework.callDelayed
import hr.algebra.countries.framework.getBooleanPreference
import hr.algebra.countries.framework.getStringPreference
import hr.algebra.countries.framework.isOnline
import hr.algebra.countries.framework.startActivity
import java.util.Locale

private const val DELAY = 4000L

const val DATA_IMPORTED = "hr.algebra.countries.data_imported"
const val APP_LOCALE = "hr.algebra.countries.app_locale"


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        val locale = Locale(getStringPreference(APP_LOCALE)!!)
        Locale.setDefault(locale)
        val configuration = resources.configuration
        val resources = context!!.resources

        configuration.locale = locale
        configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }


    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.rotate)
        binding.tvSplash.applyAnimation(R.anim.blink)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) {
                startActivity<HostActivity>()
            }
        } else {
            if (isOnline()) {

                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(CountriesWorker::class.java)
                    )
                }

                callDelayed(DELAY) {
                    startActivity<HostActivity>()
                }

            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) {
                    finish()
                }
            }
        }
    }

}