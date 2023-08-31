package com.yapp.bol.data.datasource.terms

import com.yapp.bol.data.model.terms.TermsRequest
import com.yapp.bol.data.model.terms.TermsResponse
import com.yapp.bol.data.remote.TermsApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TermsDataSourceImpl @Inject constructor(
    private val termsApi: TermsApi,
) : BaseRepository(), TermsDataSource {

    override fun getTerms(): Flow<ApiResult<TermsResponse>> = flow {
        val result = safeApiCall { termsApi.getTerms() }
        emit(result)
    }

    override suspend fun postTerms(termsRequest: TermsRequest) {
        termsApi.postTerms(termsRequest)
    }
}
