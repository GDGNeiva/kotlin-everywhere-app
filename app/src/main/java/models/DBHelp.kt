package models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?,
    val table: String
) : SQLiteOpenHelper(context, "db_kotlin_everywhere.db", factory, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
        """CREATE TABLE product (
            id INTEGER PRIMARY KEY,
            code TEXT,
            name TEXT,
            price NUMERIC,
            img TEXT,
            CONSTRAINT unique_code UNIQUE (code))"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS product")
        onCreate(db)
    }

    fun insert(values: ContentValues): Boolean {
        val db = this.writableDatabase
        val success = db.insert(table, null, values)
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun update(values: ContentValues, id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.update(table, values, "id=?", arrayOf(id.toString())).toLong()
        db.close()
        return (success >= 1)
    }

    fun delete(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(table, "id=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun select(query: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }
}