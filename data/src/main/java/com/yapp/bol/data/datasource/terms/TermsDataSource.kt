package com.yapp.bol.data.datasource.terms

import com.yapp.bol.data.model.terms.TermsRequest
import com.yapp.bol.data.model.terms.TermsResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface TermsDataSource {

    fun getTerms(): Flow<ApiResult<TermsResponse>>

    suspend fun postTerms(termsRequest: TermsRequest)
}
