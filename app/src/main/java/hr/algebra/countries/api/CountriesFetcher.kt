package hr.algebra.countries.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.countries.COUNTRIES_PROVIDER_CONTENT_URI
import hr.algebra.countries.CountriesReceiver
import hr.algebra.countries.framework.sendBroadcast
import hr.algebra.countries.model.Country
import hr.algebra.countries.handler.downloadAndStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesFetcher(private val context: Context) {

    private val countriesApi: CountriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        countriesApi = retrofit.create(CountriesApi::class.java)
    }

    fun fetchItems(count: Int) {

        val request = countriesApi.fetchItems(count = 10)

        request.enqueue(object: Callback<List<CountriesItem>> {
            override fun onResponse(
                call: Call<List<CountriesItem>>,
                response: Response<List<CountriesItem>>
            ) {
                response.body()?.let { populateItems(it) }
            }

            override fun onFailure(call: Call<List<CountriesItem>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }

        })



    }

    private fun populateItems(countriesItems: List<CountriesItem>) {
        // FOREGROUND - do not go to internet!!!
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            //BACKROUND
            countriesItems.forEach {
                val coatOfArmsPath = downloadAndStore(context, it.coatOfArms.png)

                // prepare ContentValues and insert into CP

                val values = ContentValues().apply {
                    put(Country::cca3.name, it.cca3)
                    put(Country::name.name, it.name.official)
                    put(Country::capital.name, it.capital.joinToString(", "))
                    put(Country::flag.name, it.flag)
                    put(Country::coatOfArmsPath.name, coatOfArmsPath?:"")
                    put(Country::latitude.name, it.latLng[0])
                    put(Country::longitude.name, it.latLng[1])
                    put(Country::area.name, it.area)
                    put(Country::population.name, it.population)
                    put(Country::read.name, false)
                    put(Country::languages.name, it.languages.values.joinToString(", "))
                    put(Country::maps.name, it.maps.googleMaps)
                    put(Country::nativeName.name, it.name.nativeName.values.map
                    {
                        localizedName -> localizedName.official
                    }.joinToString(", "))
                    put(Country::currencies.name, it.currencies.values.map
                    {
                            currency -> currency.name
                    }.joinToString(", "))
                }

                context.contentResolver.insert(COUNTRIES_PROVIDER_CONTENT_URI, values)


            }

            context.sendBroadcast<CountriesReceiver>()
        }
    }
}