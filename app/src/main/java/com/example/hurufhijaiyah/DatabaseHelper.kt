package com.example.hurufhijaiyah

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_user"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USER = "tbl_user"
        private const val COL_ID = "id"
        private const val COL_USERNAME = "username"
        private const val COL_PASSWORD = "password"
        private const val COL_SKOR = "skor"

        private const val COL_TOTAL_QUIZ = "total_quiz"

        private const val COL_HIGHEST_SCORE = "highest_score"

        // New for Learning History
        private const val TABLE_HISTORY = "tbl_history"
        private const val COL_H_ID = "id"
        private const val COL_H_USERNAME = "username"
        private const val COL_H_ARAB = "huruf_arab"
        private const val COL_H_LATIN = "huruf_latin"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USER (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USERNAME TEXT UNIQUE,
                $COL_PASSWORD TEXT,
                $COL_SKOR INTEGER DEFAULT 0,
                $COL_TOTAL_QUIZ INTEGER DEFAULT 0,
                $COL_HIGHEST_SCORE INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTable)

        val createTableHistory = """
            CREATE TABLE $TABLE_HISTORY (
                $COL_H_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_H_USERNAME TEXT,
                $COL_H_ARAB TEXT,
                $COL_H_LATIN TEXT
            )
        """.trimIndent()
        db.execSQL(createTableHistory)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    // 🔹 Fungsi untuk register
    fun registerUser(username: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_USERNAME, username)
            put(COL_PASSWORD, password)
        }
        val result = db.insert(TABLE_USER, null, values)
        db.close()
        return result != -1L
    }

    // 🔹 Fungsi untuk login
    fun loginUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val success = cursor.count > 0
        cursor.close()
        db.close()
        return success
    }

    fun updateQuizStats(username: String, skorQuiz: Int) {
        val db = writableDatabase

        val cursor = db.rawQuery(
            "SELECT $COL_TOTAL_QUIZ, $COL_HIGHEST_SCORE FROM $TABLE_USER WHERE $COL_USERNAME = ?",
            arrayOf(username)
        )

        var totalQuiz = 0
        var highestScore = 0

        if (cursor.moveToFirst()) {
            totalQuiz = cursor.getInt(0)
            highestScore = cursor.getInt(1)
        }
        cursor.close()

        totalQuiz += 1
        if (skorQuiz > highestScore) {
            highestScore = skorQuiz
        }

        val values = ContentValues().apply {
            put(COL_TOTAL_QUIZ, totalQuiz)
            put(COL_HIGHEST_SCORE, highestScore)
        }

        db.update(TABLE_USER, values, "$COL_USERNAME = ?", arrayOf(username))
        db.close()
    }

    fun getQuizStats(username: String): Pair<Int, Int> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_TOTAL_QUIZ, $COL_HIGHEST_SCORE FROM $TABLE_USER WHERE $COL_USERNAME = ?",
            arrayOf(username)
        )

        var totalQuiz = 0
        var highestScore = 0

        if (cursor.moveToFirst()) {
            totalQuiz = cursor.getInt(0)
            highestScore = cursor.getInt(1)
        }
        cursor.close()
        db.close()

        return Pair(totalQuiz, highestScore)
    }


    // 🔹 Fungsi untuk update skor
    fun updateSkor(username: String, skor: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_SKOR, skor)
        }
        db.update(TABLE_USER, values, "$COL_USERNAME = ?", arrayOf(username))
        db.close()
    }

    // 🔹 Fungsi untuk mendapatkan skor
    fun getSkor(username: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_SKOR FROM $TABLE_USER WHERE $COL_USERNAME = ?",
            arrayOf(username)
        )
        var skor = 0
        if (cursor.moveToFirst()) {
            skor = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return skor
    }

    // 🔹 LEARNING HISTORY METHODS

    fun addWrongAnswer(username: String, hurufArab: String, hurufLatin: String) {
        val db = writableDatabase
        // Cek dulu apakah sudah ada
        val cursor = db.rawQuery(
            "SELECT 1 FROM $TABLE_HISTORY WHERE $COL_H_USERNAME = ? AND $COL_H_ARAB = ?",
            arrayOf(username, hurufArab)
        )
        val exists = cursor.count > 0
        cursor.close()

        if (!exists) {
            val values = ContentValues().apply {
                put(COL_H_USERNAME, username)
                put(COL_H_ARAB, hurufArab)
                put(COL_H_LATIN, hurufLatin)
            }
            db.insert(TABLE_HISTORY, null, values)
        }
        db.close()
    }

    fun removeWrongAnswer(username: String, hurufArab: String) {
        val db = writableDatabase
        db.delete(TABLE_HISTORY, "$COL_H_USERNAME = ? AND $COL_H_ARAB = ?", arrayOf(username, hurufArab))
        db.close()
    }

    fun getWrongAnswers(username: String): List<Huruf> {
        val list = mutableListOf<Huruf>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_H_ARAB, $COL_H_LATIN FROM $TABLE_HISTORY WHERE $COL_H_USERNAME = ?",
            arrayOf(username)
        )
        if (cursor.moveToFirst()) {
            do {
                val arab = cursor.getString(0)
                val latin = cursor.getString(1)
                list.add(Huruf(arab, latin))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
}
