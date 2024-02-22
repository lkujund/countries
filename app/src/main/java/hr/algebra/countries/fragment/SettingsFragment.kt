package hr.algebra.countries.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.algebra.countries.activity.APP_LOCALE
import hr.algebra.countries.databinding.FragmentSettingsBinding
import hr.algebra.countries.framework.setStringPreference
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
            requireActivity().baseContext.setStringPreference(APP_LOCALE, locale.language)
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