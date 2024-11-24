package com.example.helloworld

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class FeedReaderDbHelper (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES);
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    fun insertData(title: String, description: String, image: Int, price: Float): Long {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title)
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESC, description)
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE, image)
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRICE, price)
        return db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, cv)
    }

    fun updateData(id: Long, title: String, description: String, image: String, price: Float): Int {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title)
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESC, description)
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE, image)
        cv.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRICE, price)
        return db.update(FeedReaderContract.FeedEntry.TABLE_NAME, cv, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    @SuppressLint("Range")
    fun readData(): Array<Product> {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null)

        val products = Array(cursor.count) { Product(
            id = null,
            name = "",
            title = "",
            price = 0f,
            description = "",
            image = 0
        ) }

        cursor.moveToFirst()
        var i = 0
        while (!cursor.isAfterLast) {
            products[i] = Product(
                id = cursor.getLong(cursor.getColumnIndex("_id")),
                name = cursor.getString(cursor.getColumnIndex("title")),
                title = cursor.getString(cursor.getColumnIndex("title")),
                price = cursor.getFloat(cursor.getColumnIndex("price")),
                description = cursor.getString(cursor.getColumnIndex("description")),
                image = cursor.getInt(cursor.getColumnIndex("imageUrl"))
            )
            cursor.moveToNext()
            i++
        }

        cursor.close()
        return products
    }


    fun deleteData(id: Long): Int {
        val db = writableDatabase
        return db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} TEXT," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_DESC} TEXT," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE} INTEGER," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_PRICE} FLOAT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"
    }

}