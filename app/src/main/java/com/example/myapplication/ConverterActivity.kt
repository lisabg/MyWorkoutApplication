package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_converter.*
import kotlin.math.roundToInt

class ConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        //setSupportActionBar(toolbar)

        val myArray = resources.getStringArray(R.array.converter_options)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray)

        converter_spinner_from.adapter = adapter
        converter_spinner_to.adapter = adapter

/*
        converter_spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
*/


        convert_button.setOnClickListener {
            val contentInput = findViewById<EditText>(R.id.converter_input_number)
            val displayResult = findViewById<TextView>(R.id.converter_result_content_id)

            val convertedNum = convertNumber(contentInput.text.toString())
            displayResult.setText(convertedNum)
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


    // kg to pounds
    private fun convertNumber(number: String) : String {

        var num = number.toDoubleOrNull()

        if (num !== null) {
            num *= 2.2046226218
            return num.roundToInt().toString()
        }

        return "0"

    }
}
