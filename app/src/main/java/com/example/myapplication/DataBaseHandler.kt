package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.myapplication.exercise.Exercise
import com.example.myapplication.stretch.Stretch

val DATABASE_NAME = "MyDataBase"
val DATABASE_VERSION = 1
val TABLE_NAME ="Exercises"

val COL_ID = "id"
val COL_NAME ="name"
val COL_DESCRIPTION ="description"
val COL_REPETITIONS ="repetitions"
val COL_SETS = "sets"


class DataBaseHandler(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createExTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_DESCRIPTION + " VARCHAR(256)," +
                COL_REPETITIONS + " LONG, " +
                COL_SETS + " LONG" + ");"

        p0?.execSQL(createExTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertExerciseData(exercise : Exercise) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COL_NAME, exercise.name)
        cv.put(COL_DESCRIPTION, exercise.description)
        cv.put(COL_REPETITIONS, exercise.repetition)
        cv.put(COL_SETS, exercise.sets)

        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
    }

    fun readExerciseData() : MutableList<Exercise> {
        var list : MutableList<Exercise> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val exercise = Exercise()
                exercise.id = result.getString(result.getColumnIndex(COL_ID)).toInt() // CAN ALSO USE COLUMN NUMBER
                exercise.name = result.getString(result.getColumnIndex(COL_NAME))
                exercise.description = result.getString(result.getColumnIndex(COL_DESCRIPTION))
                exercise.repetition = result.getString(result.getColumnIndex(COL_REPETITIONS)).toLong()
                exercise.sets = result.getString(result.getColumnIndex(COL_SETS)).toLong()

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
        cv.put(COL_NAME, exercise.name)
        cv.put(COL_DESCRIPTION, exercise.description)
        cv.put(COL_REPETITIONS, exercise.repetition)
        cv.put(COL_SETS, exercise.sets)
        db.update(TABLE_NAME, cv, "$COL_ID= ?", arrayOf(exercise.id.toString()))
        return true
    }



    fun deleteExerciseData(name: String) : Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_NAME=?", arrayOf(name))
        if (result == -1) {
            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
        db.close()
        return result
    }

}

