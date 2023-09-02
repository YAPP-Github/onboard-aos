package com.yapp.bol.data.datasource.match

import com.yapp.bol.data.model.match.MatchApiRequest
import com.yapp.bol.data.remote.MatchApi
import com.yapp.bol.domain.handle.BaseRepository
import javax.inject.Inject

class MatchDataSourceImpl @Inject constructor(
    private val matchApi: MatchApi,
) : BaseRepository(), MatchDataSource {

    override suspend fun postMatch(matchApiRequest: MatchApiRequest) {
        matchApi.postMatch(matchApiRequest)
    }
}
