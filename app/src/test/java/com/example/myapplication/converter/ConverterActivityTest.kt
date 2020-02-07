package com.example.myapplication.converter

import org.junit.Assert.*
import org.junit.Test

class ConverterActivityTest {

    val kg = "kg"
    val g = "g"
    val lbs = "lbs"

    val value1 = 13000.0
    val value2 = 23.0

    @Test
    fun testFromKgtoG() {
        assertEquals(23000.0, convertNumber(kg, g, value2), 0.1)
    }

    @Test
    fun testFromKgtoKg() {
        assertEquals(23.0, convertNumber(kg, kg, value2), 0.1)
    }

    @Test
    fun testFromKgtoLbs() {
        assertEquals(50.706320303, convertNumber(kg, lbs, value2), 0.1)
    }




    @Test
    fun testFromGtoG() {
        assertEquals(value1, convertNumber(g, g, value1), 0.1)
    }

    @Test
    fun testFromGtoKg() {
        assertEquals(13.0, convertNumber(g, kg, value1), 0.1)
    }

    @Test
    fun testFromGtoLbs() {
        assertEquals(28.660094084, convertNumber(g, lbs, value1), 0.1)
    }




    @Test
    fun testFromLbstoG() {
        assertEquals(10432.62451, convertNumber(lbs, g, value2), 0.1)
    }

    @Test
    fun testFromLbstoKg() {
        assertEquals(10.43262451, convertNumber(lbs, kg, value2), 0.1)
    }

    @Test
    fun testFromLbsToLbs() {
        assertEquals(value2, convertNumber(lbs, lbs, value2), 0.1)
    }




    private fun convertNumber(from: String, to: String, num: Double):  Double{
            return when (from) {
                "kg" -> convertFromKg(to, num)
                "g" -> convertFromG(to, num)
                "lbs" -> convertFromLb(to, num)
                else -> 0.0
            }
    }

    private fun convertFromKg(to: String, num: Double): Double {
        return when (to) {
            "kg" -> num
            "g" -> num * 1000
            "lbs" -> num / 0.45359237
            else -> 0.0
        }
    }

    private fun convertFromG(to: String, num: Double): Double {
        return when (to) {
            "kg" -> num / 1000
            "g" -> num
            "lbs" -> num / 453.59237
            else -> 0.0
        }
    }

    private fun convertFromLb(to: String, num: Double): Double {
        return when (to) {
            "kg" -> num * 0.45359237
            "g" -> num * 453.59237
            "lbs" -> num
            else -> 0.0
        }
    }
}