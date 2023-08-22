package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.terms.TermsDataSource
import com.yapp.bol.data.mapper.TermsMapper.toTermsDomain
import com.yapp.bol.data.model.login.TermsRequest
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.TermsList
import com.yapp.bol.domain.repository.TermsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TermsRepositoryImpl @Inject constructor(
    private val termsDataSource: TermsDataSource,
) : TermsRepository {

    override fun getTerms(): Flow<ApiResult<TermsList>> {
        return termsDataSource.getTerms().map { it.toTermsDomain() }
    }

    override suspend fun postTerms(agree: List<String>, disagree: List<String>) {
        termsDataSource.postTerms(TermsRequest(agree, disagree.ifEmpty { null }))
    }
}
