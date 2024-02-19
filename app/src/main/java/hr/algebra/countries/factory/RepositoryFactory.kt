package hr.algebra.countries.factory

import android.content.Context
import hr.algebra.countries.dao.CountriesSqlHelper

fun getCountriesRepository(context: Context?) = CountriesSqlHelper(context)