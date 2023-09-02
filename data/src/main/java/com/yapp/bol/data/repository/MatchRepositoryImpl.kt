package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.match.MatchDataSource
import com.yapp.bol.data.mapper.MatchMapper.toMatchDomain
import com.yapp.bol.domain.model.MatchItem
import com.yapp.bol.domain.repository.MatchRepository
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchDataSource: MatchDataSource,
) : MatchRepository {

    override suspend fun postMatch(matchItem: MatchItem) {
        matchDataSource.postMatch(matchItem.toMatchDomain())
    }
}
