package hr.algebra.countries.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CountriesWorker (
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        CountriesFetcher(context).fetchItems(10)
        return Result.success()
    }


}