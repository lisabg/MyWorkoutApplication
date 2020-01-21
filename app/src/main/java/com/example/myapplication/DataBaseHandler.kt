package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.myapplication.exercise.Exercise
import com.example.myapplication.stretch.Stretch

val DATABASE_NAME = "MyDataBase"
val DATABASE_VERSION = 2

val EX_TABLE_NAME ="Exercises"
val EX_COL_ID = "id"
val EX_COL_NAME ="name"
val EX_COL_DESCRIPTION ="description"
val EX_COL_REPETITIONS ="repetitions"
val EX_COL_SETS = "sets"

val ST_TABLE_NAME ="Stretches"
val ST_COL_ID = "id"
val ST_COL_NAME ="name"
val ST_COL_DESCRIPTION ="description"
val ST_COL_SECONDS ="seconds"
val ST_COL_SETS = "sets"

class DataBaseHandler(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val all: Cursor
        get() = this.writableDatabase.query(ST_TABLE_NAME, null, null, null, null, null, null)


    override fun onCreate(p0: SQLiteDatabase?) {

        val createExTable = ("CREATE TABLE " + EX_TABLE_NAME + "(" +
                EX_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EX_COL_NAME + " VARCHAR(256), " +
                EX_COL_DESCRIPTION + " VARCHAR(256), " +
                EX_COL_REPETITIONS + " LONG, " +
                EX_COL_SETS + " LONG" + ");")

        val createStTable = ("CREATE TABLE " + ST_TABLE_NAME + "(" +
                ST_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ST_COL_NAME + " VARCHAR(256), " +
                ST_COL_DESCRIPTION + " VARCHAR(256), " +
                ST_COL_SECONDS + " LONG, " +
                ST_COL_SETS + " LONG" + ");")

        p0?.execSQL(createExTable)
        p0?.execSQL(createStTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.run {
            execSQL("DROP TABLE IF EXISTS " + EX_TABLE_NAME)
            execSQL("DROP TABLE IF EXISTS " + ST_TABLE_NAME)
        }

        onCreate(db)
    }

    // Exercise functions
    fun insertExerciseData(exercise : Exercise) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(EX_COL_NAME, exercise.name)
        cv.put(EX_COL_DESCRIPTION, exercise.description)
        cv.put(EX_COL_REPETITIONS, exercise.repetition)
        cv.put(EX_COL_SETS, exercise.sets)

        val result = db.insert(EX_TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
    }

    fun readExerciseData() : MutableList<Exercise> {
        val list : MutableList<Exercise> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $EX_TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val exercise = Exercise()
                exercise.id = result.getString(result.getColumnIndex(EX_COL_ID)).toInt() // CAN ALSO USE COLUMN NUMBER
                exercise.name = result.getString(result.getColumnIndex(EX_COL_NAME))
                exercise.description = result.getString(result.getColumnIndex(EX_COL_DESCRIPTION))
                exercise.repetition = result.getString(result.getColumnIndex(EX_COL_REPETITIONS)).toLong()
                exercise.sets = result.getString(result.getColumnIndex(EX_COL_SETS)).toLong()

                list.add(exercise)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun updateExerciseData(exercise: Exercise) : Boolean  {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(EX_COL_NAME, exercise.name)
        cv.put(EX_COL_DESCRIPTION, exercise.description)
        cv.put(EX_COL_REPETITIONS, exercise.repetition)
        cv.put(EX_COL_SETS, exercise.sets)

        val whereClause = "$ST_COL_ID=?"
        val whereArgs = arrayOf(exercise.id.toString())

        val _success = db.update(EX_TABLE_NAME, cv, whereClause, whereArgs)
        db.close()

        return Integer.parseInt("$_success") != -1
    }

    fun deleteExerciseData(name: String) : Int {
        val db = this.writableDatabase
        val result = db.delete(EX_TABLE_NAME, "$EX_COL_NAME=?", arrayOf(name))
        if (result == -1) {
            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
        db.close()
        return result
    }

    // Stretch functions

    fun insertStretchData(stretch: Stretch) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(ST_COL_NAME, stretch.name)
        cv.put(ST_COL_DESCRIPTION, stretch.description)
        cv.put(ST_COL_SECONDS, stretch.seconds)
        cv.put(ST_COL_SETS, stretch.sets)

        val result = db.insert(ST_TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
    }

    fun readStretchData() : MutableList<Stretch> {
        val list : MutableList<Stretch> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $ST_TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val stretch = Stretch()
                stretch.id = result.getString(result.getColumnIndex(ST_COL_ID)).toInt() // CAN ALSO USE COLUMN NUMBER
                stretch.name = result.getString(result.getColumnIndex(ST_COL_NAME))
                stretch.description = result.getString(result.getColumnIndex(ST_COL_DESCRIPTION))
                stretch.seconds = result.getString(result.getColumnIndex(ST_COL_SECONDS)).toLong()
                stretch.sets = result.getString(result.getColumnIndex(ST_COL_SETS)).toLong()

                list.add(stretch)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun updateStretchData(stretch: Stretch) : Boolean  {

        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(ST_COL_NAME, stretch.name)
        cv.put(ST_COL_DESCRIPTION, stretch.description)
        cv.put(ST_COL_SECONDS, stretch.seconds)
        cv.put(ST_COL_SETS, stretch.sets)

        val whereClause = "$ST_COL_ID=?"
        val whereArgs = arrayOf(stretch.id.toString())

        val _success = db.update(ST_TABLE_NAME, cv, whereClause, whereArgs)
        db.close()

        return Integer.parseInt("$_success") != -1
    }

    fun deleteStretchData(name: String) : Int {
        val db = this.writableDatabase
        val result = db.delete(ST_TABLE_NAME, "$ST_COL_NAME=?", arrayOf(name))
        if (result >= 1) {
            Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
        db.close()
        return result
    }

    fun logUpdatedStretch() {
        val csr = all
        val sb = StringBuilder()
        while (csr.moveToNext()) {
            sb.append("Row is " + csr.position.toString())
            sb.append("\n\tId is :").append(csr.getInt(csr.getColumnIndex(ST_COL_ID)))
            sb.append("\n\tName is :").append(csr.getString(csr.getColumnIndex(ST_COL_NAME)))
            sb.append("\n\tDescription is :").append(csr.getString(csr.getColumnIndex(ST_COL_DESCRIPTION)))
            sb.append("\n\t Seconds is ").append(csr.getLong(csr.getColumnIndex(ST_COL_SECONDS)).toString())
            sb.append("\n\t Sets is ").append(csr.getLong(csr.getColumnIndex(ST_COL_SETS)).toString())
            Log.d("LOGDATA", sb.toString())
        }
    }
}

