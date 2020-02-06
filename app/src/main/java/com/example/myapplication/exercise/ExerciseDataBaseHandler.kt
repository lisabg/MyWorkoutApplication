package com.example.myapplication.exercise

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.myapplication.entities.Exercise

val DATABASE_NAME = "MyExerciseDataBase"
val DATABASE_VERSION = 7

val EX_TABLE_NAME = "Exercises"
val EX_COL_ID = "id"
val EX_COL_NAME = "name"
val EX_COL_DESCRIPTION = "description"
val EX_COL_REPETITIONS = "repetitions"
val EX_COL_SETS = "sets"
val EX_COL_WEIGHT = "weight"


val EX_COL_WEIGHT_START = "weight_start"
val EX_COL_WEIGHT_GOAL = "weight_goal"

val EX_HIST_TABLE_NAME = "Exercise_history"
val EX_HIST_COL_ID = "id"
val EX_HIST_COL_EX_ID = "exercise_id"
val EX_HIST_COL_WEIGHT = "weight"

class ExerciseDataBaseHandler(val context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    val all: Cursor
        get() = this.writableDatabase.query(EX_TABLE_NAME, null, null, null, null, null, null)


    override fun onCreate(p0: SQLiteDatabase?) {

        val createExTable = ("CREATE TABLE " + EX_TABLE_NAME + "(" +
                EX_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EX_COL_NAME + " VARCHAR(256), " +
                EX_COL_DESCRIPTION + " VARCHAR(256), " +
                EX_COL_REPETITIONS + " LONG, " +
                EX_COL_SETS + " LONG, " +
                EX_COL_WEIGHT + " LONG, " +
                EX_COL_WEIGHT_START + " LONG, " +
                EX_COL_WEIGHT_GOAL + " LONG" +
                ");")

        val createHistTable = ("CREATE TABLE " + EX_HIST_TABLE_NAME + "(" +
                EX_HIST_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EX_HIST_COL_EX_ID + " INTEGER, " +
                EX_HIST_COL_WEIGHT + " LONG, " +
                "FOREIGN KEY (" + EX_HIST_COL_EX_ID + ") REFERENCES " + EX_TABLE_NAME + " (" + EX_COL_ID + ")" +
                ");")

        p0?.execSQL(createExTable)
        p0?.execSQL(createHistTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.run {
            execSQL("DROP TABLE IF EXISTS " + EX_TABLE_NAME)
            execSQL("DROP TABLE IF EXISTS " + EX_HIST_TABLE_NAME)
        }

        onCreate(db)
    }

    fun insertExerciseData(exercise: Exercise) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(EX_COL_NAME, exercise.name)
        cv.put(EX_COL_DESCRIPTION, exercise.description)
        cv.put(EX_COL_REPETITIONS, exercise.repetition)
        cv.put(EX_COL_SETS, exercise.sets)
        cv.put(EX_COL_WEIGHT, exercise.weight)
        cv.put(EX_COL_WEIGHT_START, exercise.weight)
        cv.put(EX_COL_WEIGHT_GOAL, exercise.weightGoal)

        db.insert(EX_TABLE_NAME, null, cv)
//        if (result == (-1).toLong()) {
//            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
//        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
    }

    fun insertWeightHistory(id: Int, weight: Long) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(EX_HIST_COL_EX_ID, id)
        cv.put(EX_HIST_COL_WEIGHT, weight)

        db.insert(EX_HIST_TABLE_NAME, null, cv)
    }

    fun readExerciseData(): MutableList<Exercise> {
        val list: MutableList<Exercise> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $EX_TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val exercise = Exercise(
                    result.getString(result.getColumnIndex(EX_COL_NAME)),
                    result.getString(result.getColumnIndex(EX_COL_DESCRIPTION)),
                    result.getString(result.getColumnIndex(EX_COL_REPETITIONS)).toLong(),
                    result.getString(result.getColumnIndex(EX_COL_SETS)).toLong(),
                    result.getString(result.getColumnIndex(EX_COL_WEIGHT)).toLong(),
                    result.getString(result.getColumnIndex(EX_COL_WEIGHT_GOAL)).toLong()
                )

                exercise.id = result.getString(result.getColumnIndex(EX_COL_ID)).toInt()
                exercise.weightStart = result.getString(result.getColumnIndex(EX_COL_WEIGHT_START)).toLong()

                list.add(exercise)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun readExerciseHistoryData(id: Int): MutableList<Long> {
        val list: MutableList<Long> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM $EX_HIST_TABLE_NAME WHERE $EX_HIST_COL_EX_ID = $id"
        val result = db.rawQuery(query, null)
        val count = result.count

        if (count > 0 && result.moveToFirst()) {
            do {
                list.add(result.getString(result.getColumnIndex(EX_HIST_COL_WEIGHT)).toLong())

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun updateExerciseData(exercise: Exercise): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(EX_COL_NAME, exercise.name)
        cv.put(EX_COL_DESCRIPTION, exercise.description)
        cv.put(EX_COL_REPETITIONS, exercise.repetition)
        cv.put(EX_COL_SETS, exercise.sets)
        cv.put(EX_COL_WEIGHT, exercise.weight)
        cv.put(EX_COL_WEIGHT_GOAL, exercise.weightGoal)

        val whereClause = "$EX_COL_ID=?"
        val whereArgs = arrayOf(exercise.id.toString())

        val _success = db.update(EX_TABLE_NAME, cv, whereClause, whereArgs)
        db.close()

        return Integer.parseInt("$_success") != -1
    }

    fun updateExerciseGoal(id: Int, start: Long, goal: Long): Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(EX_COL_WEIGHT_START, start)
        cv.put(EX_COL_WEIGHT_GOAL, goal)

        val whereClause = "$EX_COL_ID=?"
        val whereArgs = arrayOf(id.toString())

        val _success = db.update(EX_TABLE_NAME, cv, whereClause, whereArgs)
        db.close()

        return Integer.parseInt("$_success") != -1

    }


    fun deleteExerciseData(name: String): Int {
        val db = this.writableDatabase
        val result = db.delete(EX_TABLE_NAME, "$EX_COL_NAME=?", arrayOf(name))
//        if (result == -1) {
//            Toast.makeText(context, "DB Failed", Toast.LENGTH_SHORT).show()
//        } else Toast.makeText(context, "DB Success", Toast.LENGTH_SHORT).show()
        db.close()
        return result
    }


    fun logUpdatedExercise() {
        val csr = all
        val sb = StringBuilder()
        while (csr.moveToNext()) {
            sb.append("Row is " + csr.position.toString())
            sb.append("\n\tId is :").append(csr.getInt(csr.getColumnIndex(EX_COL_ID)))
            sb.append("\n\tName is :").append(csr.getString(csr.getColumnIndex(EX_COL_NAME)))
            sb.append("\n\tDescription is :").append(
                csr.getString(
                    csr.getColumnIndex(
                        EX_COL_DESCRIPTION
                    )
                )
            )
            sb.append("\n\t Repetitions are ")
                .append(csr.getLong(csr.getColumnIndex(EX_COL_REPETITIONS)).toString())
            sb.append("\n\t Sets are ")
                .append(csr.getLong(csr.getColumnIndex(EX_COL_SETS)).toString())
            Log.d("LOGDATA", sb.toString())
        }
    }
}

