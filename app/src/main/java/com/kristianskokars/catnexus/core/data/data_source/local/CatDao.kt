package com.kristianskokars.catnexus.core.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kristianskokars.catnexus.core.CAT_TABLE_NAME
import com.kristianskokars.catnexus.core.domain.model.Cat
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM $CAT_TABLE_NAME ORDER BY fetchedDateInMillis ASC")
    fun getCats(): Flow<List<Cat>>

    @Query("SELECT * FROM $CAT_TABLE_NAME WHERE id = :catId")
    fun getCat(catId: String): Flow<Cat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCats(cats: List<Cat>)

    @Query("DELETE FROM $CAT_TABLE_NAME WHERE id NOT IN (:catIds)")
    suspend fun clearCatsNotIn(catIds: List<String>)

    @Transaction
    suspend fun insertNewCats(newCats: List<Cat>, clearPrevious: Boolean = false) {
        addCats(newCats)
        clearCatsNotIn(newCats.map { it.id })
    }
}
