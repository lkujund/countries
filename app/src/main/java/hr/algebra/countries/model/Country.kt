package hr.algebra.countries.model

data class Country (
    var _id: Long?,
    var cca3: String,
    val name: String,
    val nativeName: String,
    val capital: String,
    val flag: String,
    val coatOfArmsPath: String,
    val latitude: Double,
    val longitude: Double,
    val area: Double,
    val population: Int,
    val currencies: String,
    val languages: String,
    val maps: String,
    var read: Boolean
)