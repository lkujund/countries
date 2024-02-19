package hr.algebra.countries.activity

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.countries.adapter.CountriesPagerAdapter
import hr.algebra.countries.databinding.ActivityItemPagerBinding
import hr.algebra.countries.framework.fetchItems
import hr.algebra.countries.model.Country


const val POSITION = "hr.algebra.countries.item_pos"

class CountriesPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemPagerBinding

    private lateinit var countries: MutableList<Country>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        countries = fetchItems().filter { country: Country -> country.name.uppercase().contains(
            FILTER) }.toMutableList()
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = CountriesPagerAdapter(this, countries)
        binding.viewPager.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private var FILTER: String = ""

        fun setFilter(query: String?) {
            FILTER = query!!.uppercase()
        }
    }


}