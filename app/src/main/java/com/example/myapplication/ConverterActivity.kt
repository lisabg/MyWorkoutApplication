package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_converter.*
import kotlin.math.roundToInt

class ConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        val myArray = resources.getStringArray(R.array.converter_options)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray)

        converter_spinner_from.adapter = adapter
        converter_spinner_to.adapter = adapter

/*
        // action on Spinner selections
        converter_spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
*/

        //listen to 'Convert' button and convert number according to EditText and Spinners selected and apply to
        // TextView
        convert_button.setOnClickListener {
            val convertedNum = convertNumber(converter_input_number.text.toString())
            converter_result_content_id.text = convertedNum
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_dashboard_button -> {
                startActivity(Intent(this@ConverterActivity, DashboardActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //TODO("not implemented") //Add more converting functionality


    // kg to pounds
    private fun convertNumber(number: String) : String {
        val from = converter_spinner_from.selectedItem.toString()

        return when(from) {
            "kg" -> convertFromKg(number.toDoubleOrNull())
            "g" -> convertFromG(number.toDoubleOrNull())
            "lb" -> convertFromLb(number.toDoubleOrNull())
            "oz" -> convertFromOz(number.toDoubleOrNull())
            else -> ""
        }
    }

    private fun convertFromKg(num: Double?) : String {
        if (num !== null) {

            when (converter_spinner_to.selectedItem.toString()) {
                "kg" -> return num.toString()

                "g" -> {
                    val convertedNum = num * 1000
                    return convertedNum.toInt().toString() }

                "lb" -> {
                    val convertedNum = num / 0.45359237
                    return convertedNum.toInt().toString() }

                "oz" -> {
                    val convertedNum = num / 0.02834952
                    return convertedNum.toInt().toString() }
            }
        }
        return "0"
    }

    private fun convertFromG(num : Double?) : String {
        if (num !== null) {

            when (converter_spinner_to.selectedItem.toString()) {
                "kg" -> {
                    val convertedNum = num / 1000
                    return convertedNum.toInt().toString() }

                "g" -> return num.toString()

                "lb" -> {
                    val convertedNum = num * 2.2046226218
                    return convertedNum.toInt().toString() }

                "oz" -> {
                    val convertedNum = num / 453.59237
                    return convertedNum.toInt().toString() }
            }
        }
        return "0"
    }

    private fun convertFromLb(num: Double?): String {
        if (num !== null) {

            when (converter_spinner_to.selectedItem.toString()) {
                "kg" -> {
                    val convertedNum = num * 0.45359237
                    return convertedNum.toInt().toString() }

                "g" -> {
                    val convertedNum = num * 453.59237
                    return convertedNum.toInt().toString() }

                "lb" -> return num.toString()

                "oz" -> {
                    val convertedNum = num * 16
                    return convertedNum.toInt().toString() }
            }
        }
        return "0"
    }

    private fun convertFromOz(num: Double?) : String {
        if (num !== null) {

            when (converter_spinner_to.selectedItem.toString()) {
                "kg" -> {
                    val convertedNum = num * 0.02834952
                    return convertedNum.toInt().toString() }

                "g" -> {
                    val convertedNum = num * 28.34952
                    return convertedNum.toInt().toString() }

                "lb" -> {
                    val convertedNum = num / 16
                    return convertedNum.toInt().toString() }

                "oz" -> return num.toString()
            }
        }
        return "0"
    }
}
