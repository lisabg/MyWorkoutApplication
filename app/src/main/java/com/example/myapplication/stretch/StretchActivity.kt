package com.example.myapplication.stretch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DashboardActivity
import com.example.myapplication.DataBaseHandler
import com.example.myapplication.R
import kotlinx.android.synthetic.main.stretch_main_layout.*
import kotlinx.android.synthetic.main.stretch_new_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*


class StretchActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stretch_main_layout)

        setSupportActionBar(toolbar)
        toolbar.title = "Stretches"

        val db = DataBaseHandler(this)
        val stretchData = ArrayList<Stretch>()

/*        stretchData.add(
            Stretch(
                0, "Downward facing dog", "Place the palm of your hands on the floor centering your hands straight " +
                        "under your shoulders, place your feet flat on the ground. Try to straighten both your elbows and knees" +
                        "pushing your bottom towards the sky.", 30, 3
            ))
        stretchData.add(
            Stretch(
                0 , "Shoulder stretch", "Stretch and point one arm straight up towards the sky, then bend " +
                        "the elbow and reach your hand down towards your back. Your other hand may help push your bent" +
                        "elbow backwards.", 10, 3
            ))*/

        val data = db.readStretchData()
        for (i in 0 until (data.size)) {
            stretchData.add(data[i])
        }

        viewAdapter = StretchAdapter(stretchData)
        viewManager = LinearLayoutManager(this)

        findViewById<RecyclerView>(R.id.recycler_view_stretches).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = StretchAdapter(stretchData)
        }

        addNewStretchFunctionality(stretchData, db)
    }

    private fun addNewStretchFunctionality(data : ArrayList<Stretch>, db : DataBaseHandler) {

        add_stretch_button.setOnClickListener {
            //inflate the dialog with custom view
            val mStretchDialogView = LayoutInflater.from(this).inflate(R.layout.stretch_new_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mStretchDialogView)
                .setTitle(R.string.stretch_dialog_box_title)
            val mAlertDialog = mBuilder.show()

            mStretchDialogView.add_stretch_submit_button.setOnClickListener{
                if (!mStretchDialogView.new_stretch_title_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_description_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_time_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_sets_input.text.isBlank()) {

                    mAlertDialog.dismiss()

                    val title = mStretchDialogView.new_stretch_title_input.text.toString()
                    val description = mStretchDialogView.new_stretch_description_input.text.toString()
                    val seconds = mStretchDialogView.new_stretch_time_input.text.toString()
                    val sets = mStretchDialogView.new_stretch_sets_input.text.toString()

                    //add input to data array for display
                    val stretch = Stretch(
                        0,
                        title,
                        description,
                        seconds.toLong(),
                        sets.toLong())

                    db.insertStretchData(stretch)
                    updateViewData(db, data)

                    Toast.makeText(this, "Stretch added", Toast.LENGTH_SHORT).show()

                } else Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

            mStretchDialogView.add_stretch_cancel_button.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }

    }

    private fun updateViewData(db: DataBaseHandler, myData : ArrayList<Stretch>) {
        val data = db.readStretchData()
        var added = 0

        for (i in 0 until (data.size)) {
            var duplicates = 0
            for (j in 0 until (myData.size)) {
                if (data[i].name == myData[j].name) {
                    duplicates++
                }
            }
            if (duplicates == 0) {
                myData.add(data[i])
                added++
            }
        }
        //update display
        findViewById<RecyclerView>(R.id.recycler_view_stretches).apply {
            adapter = StretchAdapter(myData)
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
                startActivity(Intent(this@StretchActivity, DashboardActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
