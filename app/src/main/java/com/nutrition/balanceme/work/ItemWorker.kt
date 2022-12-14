package com.nutrition.balanceme.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.nutrition.balanceme.data.local.RecipesDao
import com.nutrition.balanceme.data.remote.RecipesService
import com.nutrition.balanceme.util.Preferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class ItemWorker @AssistedInject constructor(
    private val dao: RecipesDao,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val preferences: Preferences,
    private val recipesService: RecipesService,
): CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val update = preferences.getUpdate()
            val time = SimpleDateFormat("hh:mm:ss", Locale.UK).format(Date())
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())

            dao.addItems(recipesService.getItems(update))
            preferences.setUpdate("${date}T$time")

            Result.success(workDataOf("status" to "success"))
        } catch (e: Throwable) { Result.failure(workDataOf("error" to e.message)) }
    }
}