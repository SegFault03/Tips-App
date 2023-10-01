package com.example.tips_app.data

import com.example.tips_app.R
import com.example.tips_app.model.Tip

class TipsRepository {
    fun loadTips(): List<Tip>{
        return listOf(
            Tip(
                R.string.tip_sn1,
                R.drawable.books,
                R.string.tip_1,
                R.string.tip_desc_1
            ),
            Tip(
                R.string.tip_sn2,
                R.drawable.introspect,
                R.string.tip_2,
                R.string.tip_desc_2
            ),
            Tip(
                R.string.tip_sn3,
                R.drawable.meditate,
                R.string.tip_3,
                R.string.tip_desc_3
            ),
            Tip(
                R.string.tip_sn4,
                R.drawable.reflect,
                R.string.tip_4,
                R.string.tip_desc_4
            ),
            Tip(
                R.string.tip_sn5,
                R.drawable.trip,
                R.string.tip_5,
                R.string.tip_desc_5
            ),
            Tip(
                R.string.tip_sn6,
                R.drawable.sunset,
                R.string.tip_6,
                R.string.tip_desc_6
            ),
            Tip(
                R.string.tip_sn7,
                R.drawable.yoga,
                R.string.tip_7,
                R.string.tip_desc_7
            )
        )
    }
}