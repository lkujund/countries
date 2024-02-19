package hr.algebra.countries.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.fragment.app.Fragment
import hr.algebra.countries.R
import hr.algebra.countries.activity.HostActivity
import hr.algebra.countries.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rgLanguage.setOnCheckedChangeListener { group, checkedId ->
            val configuration = resources.configuration
            val locale: Locale = if (binding.rbEn.isChecked) {
            Locale("en")
        } else {
            Locale("hr")
        }
            Locale.setDefault(locale)

            val resources = requireContext().resources

            configuration.locale = locale
            configuration.setLayoutDirection(locale)

            resources.updateConfiguration(configuration, resources.displayMetrics)


            requireFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit()
        }
    }
}