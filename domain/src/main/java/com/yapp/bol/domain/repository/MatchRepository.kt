package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.MatchItem

interface MatchRepository {

    suspend fun postMatch(matchItem: MatchItem)
}
