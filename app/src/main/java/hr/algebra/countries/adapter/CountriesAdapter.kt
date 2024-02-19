package hr.algebra.countries.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.countries.COUNTRIES_PROVIDER_CONTENT_URI
import hr.algebra.countries.R
import hr.algebra.countries.activity.CountriesPagerActivity
import hr.algebra.countries.activity.POSITION
import hr.algebra.countries.factory.getCountriesRepository
import hr.algebra.countries.framework.startActivity
import hr.algebra.countries.model.Country
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class CountriesAdapter(
    private val context: Context,
    private var countries: MutableList<Country>
) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>(){
    private val unfilteredCountries = countries
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFlag = itemView.findViewById<TextView>(R.id.tvFlag)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        fun bind(country: Country) {
            tvName.text = country.name
            tvFlag.text = country.flag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnLongClickListener {
            //delete
            val country = countries[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(COUNTRIES_PROVIDER_CONTENT_URI, country._id!!),
                null, null
            )
            File(country.coatOfArmsPath).delete()
            countries.removeAt(position)
            notifyDataSetChanged()

            true
        }

        holder.itemView.setOnClickListener {
            // edit
            context.startActivity<CountriesPagerActivity>(POSITION, position)
        }


        holder.bind(countries[position])
    }

    fun filter(query: String?) {
        countries = if (query.isNullOrEmpty()){
            unfilteredCountries
        } else {
            countries.filter{ country: Country -> country.name.uppercase().contains(query.uppercase())
            }.toMutableList()
        }
        CountriesPagerActivity.setFilter(query)
        notifyDataSetChanged()
        return
    }
}