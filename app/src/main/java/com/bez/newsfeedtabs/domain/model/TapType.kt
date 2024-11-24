package com.bez.newsfeedtabs.domain.model

sealed class TabType(val tabName: String) {
    data object Cars : TabType("Cars")
    data object Entertainment : TabType("Entertainment")
}