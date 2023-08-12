package com.yapp.bol.data.datasource.match

import com.yapp.bol.data.model.match.MatchApiRequest

interface MatchDataSource {

    suspend fun postMatch(matchApiRequest: MatchApiRequest)
}
