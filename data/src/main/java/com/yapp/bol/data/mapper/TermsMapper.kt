package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.terms.TermsDTO
import com.yapp.bol.data.model.terms.TermsResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.domain.model.TermsList

object TermsMapper {

    fun ApiResult<TermsResponse>.toTermsDomain(): ApiResult<TermsList> {
        return when (this) {
            is ApiResult.Success -> {
                ApiResult.Success(TermsList(data.contents.map { it.toItem() }))
            }

            is ApiResult.Error -> {
                ApiResult.Error(exception)
            }
        }
    }

    private fun TermsDTO.toItem(): TermsItem {
        return TermsItem(
            code = this.code,
            title = this.title,
            url = this.url,
            isRequired = this.isRequired
        )
    }
}
