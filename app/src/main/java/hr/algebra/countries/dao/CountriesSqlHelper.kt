package hr.algebra.countries.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import hr.algebra.countries.model.Country

private const val DB_NAME = "countries.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "countries"
private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Country::_id.name} integer primary key autoincrement, " +
        "${Country::cca3.name} text not null, " +
        "${Country::name.name} text not null, " +
        "${Country::nativeName.name} text not null, " +
        "${Country::capital.name} text not null, " +
        "${Country::flag.name} text not null, " +
        "${Country::coatOfArmsPath.name} text not null, " +
        "${Country::latitude.name} real not null, " +
        "${Country::longitude.name} real not null, " +
        "${Country::area.name} real not null, " +
        "${Country::population.name} integer not null," +
        "${Country::currencies.name} text not null," +
        "${Country::languages.name} text not null," +
        "${Country::maps.name} text not null," +
        "${Country::read.name} integer not null" +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class CountriesSqlHelper(context: Context?) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
), Repository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?) = writableDatabase
        .delete(TABLE_NAME, selection, selectionArgs)

    override fun insert(values: ContentValues?) = writableDatabase.insert(
        TABLE_NAME, null, values
    )

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(
        TABLE_NAME,
        values,
        selection,
        selectionArgs
    )
}