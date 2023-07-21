package com.yapp.bol.presentation.firebase.analysis

import com.google.firebase.analytics.FirebaseAnalytics

// Google Analytics
class GA {

    object EventName {
        const val SELECT_CONTENT = FirebaseAnalytics.Event.SELECT_CONTENT
    }

    object Screen {
        const val SPLASH = "Splash"
        const val LOGIN = "Login"
        const val NICKNAME: String = "Nickname"

        const val NEW_GROUP = "NewGroup"

        const val GAME_RESULT = "GameResult"
        const val GAME_SELECT = "GameSelect"

        const val MEMBER_SELECT = "MemberSelect"
    }

    object Event {
        // FROM BY ACTIVITY
    }
}
