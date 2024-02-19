package hr.algebra.countries.api

import com.google.gson.annotations.SerializedName


data class CountriesItem (
    @SerializedName("coatOfArms") val coatOfArms: CoatOfArms,
    @SerializedName("latlng") val latLng: Array<Double>,
    @SerializedName("name") val name: Name,
    @SerializedName("cca3") val cca3: String,
    @SerializedName("capital") val capital: List<String>,
    @SerializedName("area") val area: Double,
    @SerializedName("population") val population: Int,
    @SerializedName("flag") val flag: String,
    @SerializedName("currencies") val currencies: Map<String,Currencies>,
    @SerializedName("languages") val languages: Map<String,String>,
    @SerializedName("maps") val maps: Maps
) {
    data class CoatOfArms(
        @SerializedName("png") val png: String
    )
    data class Maps(
        @SerializedName("googleMaps") val googleMaps: String
    )
    data class Currencies(
        @SerializedName("name") val name: String
    )
    data class Name(
        @SerializedName("official") val official: String,
        @SerializedName("nativeName") val nativeName: Map<String, LocalizedName>
    ) {
        data class LocalizedName(
            @SerializedName("official") val official: String
        )
        }
    }