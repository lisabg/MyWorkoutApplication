package com.example.myapplication.converter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.myapplication.DashboardActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.converter_layout.*
import java.math.RoundingMode
import java.text.DecimalFormat

class ConverterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.converter_layout)

        val myArray = resources.getStringArray(R.array.converter_options)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray)

        converter_spinner_from.adapter = adapter
        converter_spinner_to.adapter = adapter

        converter_spinner_from.onItemSelectedListener = this
        converter_spinner_to.onItemSelectedListener = this


        //listen to 'Convert' button and convert number according to EditText and Spinners selected and apply to
        // TextView
        convert_button.setOnClickListener {
            val inputNum = converter_input_number.text.toString()

            if (inputNum.isBlank()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            } else {
                val convertedNum = formatNum(inputNum)
                converter_result_content_id.text = convertedNum
            }
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


    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val text = p0!!.getItemAtPosition(p2).toString()
        if (text != "units") Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


    private fun formatNum(inputNum : String) : String {
        val convertedNum = convertNumber(inputNum)
        val df = DecimalFormat("######.00")
        df.roundingMode = RoundingMode.CEILING
        return df.format(convertedNum).toString()
    }


    //TODO("not implemented") //Add more converting functionality


    private fun convertNumber(number: String): Double {
        val num = number.toDoubleOrNull()
        if (num !== null) {
            return when (converter_spinner_from.selectedItem.toString()) {
                "kg" -> convertFromKg(num)
                "g" -> convertFromG(num)
                "lb" -> convertFromLb(num)
                "oz" -> convertFromOz(num)
                else -> 0.0
            }

        }
        return 0.0
    }

    private fun convertFromKg(num: Double): Double {
        return when (converter_spinner_to.selectedItem.toString()) {
            "kg" -> num
            "g" -> num / 1000
            "lb" -> num / 0.45359237
            "oz" -> num / 0.02834952
            else -> 0.0
        }
    }

    private fun convertFromG(num: Double): Double {
        return when (converter_spinner_to.selectedItem.toString()) {
            "kg" -> num * 1000
            "g" -> num
            "lb" -> num * 2.2046226218
            "oz" -> num / 453.59237
            else -> 0.0
        }
    }

    private fun convertFromLb(num: Double): Double {
        return when (converter_spinner_to.selectedItem.toString()) {
            "kg" -> num * 0.45359237
            "g" -> num * 453.59237
            "lb" -> num
            "oz" -> num * 16
            else -> 0.0
        }
    }

    private fun convertFromOz(num: Double): Double {
        return when (converter_spinner_to.selectedItem.toString()) {
            "kg" -> num * 0.02834952
            "g" -> num * 28.34952
            "lb" -> num / 16
            "oz" -> num
            else -> 0.0
        }
    }
}
