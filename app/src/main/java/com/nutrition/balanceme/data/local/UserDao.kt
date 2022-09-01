package com.nutrition.balanceme.data.local

import androidx.room.*
import com.nutrition.balanceme.domain.models.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("select * from profile where current = 1 limit 1")
    fun getProfile(): Flow<Profile>

    @Query("select * from profile where id = :id limit 1")
    fun getChef(id: String): Profile?

    @Query("select * from profile where current = 0 order by random() limit 10")
    suspend fun getChefs(): List<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(vararg profile: Profile)

    @Query("delete from profile where id != :id")
    suspend fun nukeChefs(id: String)

    @Query("delete from profile")
    suspend fun nukeProfile()

    @Transaction
    suspend fun updateChefs(id: String, chefs: List<Profile>){
        nukeChefs(id) // delete all chefs apart from the user
        addProfile(*chefs.toTypedArray()) // add the incoming chefs
    }
}