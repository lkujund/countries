package hr.algebra.countries.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.countries.COUNTRIES_PROVIDER_CONTENT_URI
import hr.algebra.countries.R
import hr.algebra.countries.model.Country
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class CountriesPagerAdapter (
    private val context: Context,
    private val countries: MutableList<Country>
) : RecyclerView.Adapter<CountriesPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val _km2: String = " km\u00B2"
        private val ivCoatOfArms = itemView.findViewById<ImageView>(R.id.ivCoatOfArms)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)
        val btnMaps = itemView.findViewById<TextView>(R.id.btnMaps)

        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvCca3 = itemView.findViewById<TextView>(R.id.tvCca3)
        private val tvNativeName = itemView.findViewById<TextView>(R.id.tvNativeName)
        private val tvCapital = itemView.findViewById<TextView>(R.id.tvCapital)
        private val tvLatitude = itemView.findViewById<TextView>(R.id.tvLatitude)
        private val tvLongitude = itemView.findViewById<TextView>(R.id.tvLongitude)
        private val tvArea = itemView.findViewById<TextView>(R.id.tvArea)
        private val tvPopulation = itemView.findViewById<TextView>(R.id.tvPopulation)
        private val tvLanguages = itemView.findViewById<TextView>(R.id.tvLanguages)
        private val tvCurrency = itemView.findViewById<TextView>(R.id.tvCurrency)

        fun bind(country: Country) {
            tvName.text = country.name
            tvCca3.text = country.cca3
            tvNativeName.text = country.nativeName
            tvCapital.text = country.capital
            tvLatitude.text = country.latitude.toBigDecimal().toPlainString()
            tvLongitude.text = country.longitude.toBigDecimal().toPlainString()
            tvLanguages.text = country.languages
            tvCurrency.text = country.currencies
            btnMaps.tag = country.maps
            tvArea.text = buildString {
                append(country.area.toBigDecimal().toPlainString())
                append(_km2)
            }
            tvPopulation.text = country.population.toString()
            Picasso.get()
                .load(File(country.coatOfArmsPath))
                .error(R.drawable.whiteflag)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivCoatOfArms)

            ivRead.setImageResource(if(country.read) R.drawable.green_flag else R.drawable.red_flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ivRead.setOnClickListener {
            updateCountry(position)
        }


        holder.bind(countries[position])

        holder.btnMaps.setOnClickListener(object: OnClickListener{
            override fun onClick(v: View?) {
                var mapsUri = Uri.parse(v!!.tag.toString())
                var intent = Intent(Intent.ACTION_VIEW)
                intent.data = mapsUri
                context.startActivity(intent)
            }
        })
    }

    private fun updateCountry(position: Int) {
        val country = countries[position]
        country.read = !country.read
        context.contentResolver.update(
            ContentUris.withAppendedId(COUNTRIES_PROVIDER_CONTENT_URI, country._id!!),
            ContentValues().apply {
                put(Country::read.name, country.read)
            },
            null,
            null
        )
        notifyItemChanged(position)
    }
}