package finki.ukim.mk.cookitup.domain.search

import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

interface RemoteRecipeDataSource {
    suspend fun search(query: String): List<RecipeApi>
}