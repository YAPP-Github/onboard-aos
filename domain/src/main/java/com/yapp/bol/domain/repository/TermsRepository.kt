package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.TermsList
import kotlinx.coroutines.flow.Flow

interface TermsRepository {

    fun getTerms(): Flow<ApiResult<TermsList>>

    suspend fun postTerms(agree: List<String>, disagree: List<String>)
}
