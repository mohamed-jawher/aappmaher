package com.example.helloworld

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "products"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DESC = "description"
        const val COLUMN_NAME_IMAGE = "imageUrl"
        const val COLUMN_NAME_PRICE = "price"

    }
}