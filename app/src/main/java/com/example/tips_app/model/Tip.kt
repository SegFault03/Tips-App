package com.example.tips_app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Tip(
    @StringRes val tipSnRes: Int,
    @DrawableRes val tipImageRes: Int,
    @StringRes val tipNameRes: Int,
    @StringRes val tipDescRes: Int
)
