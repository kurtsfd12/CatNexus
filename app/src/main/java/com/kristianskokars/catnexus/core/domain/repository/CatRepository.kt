package com.kristianskokars.catnexus.core.domain.repository

import com.kristianskokars.catnexus.core.domain.model.Cat
import com.kristianskokars.catnexus.core.domain.model.ServerError
import com.kristianskokars.catnexus.lib.Result
import com.kristianskokars.catnexus.lib.Success
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    val cats: Flow<List<Cat>>
    fun getCat(id: String): Flow<Cat>
    fun saveCatImage(cat: Cat)
    suspend fun refreshCats(shouldClearPrevious: Boolean = false): Result<ServerError, Success>
}
