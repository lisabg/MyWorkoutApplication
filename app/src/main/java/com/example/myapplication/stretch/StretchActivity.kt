package com.example.myapplication.stretch

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DashboardActivity
import com.example.myapplication.R
import com.example.myapplication.activityObersvers.StretchActivityObserver
import com.example.myapplication.entities.Stretch
import kotlinx.android.synthetic.main.stretch_main_layout.*
import kotlinx.android.synthetic.main.stretch_new_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*


class StretchActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var itemTouchHelperCallBack: ItemTouchHelper.SimpleCallback
    private val TAG = javaClass.simpleName

    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stretch_main_layout)

        Log.i(TAG, "Owner ON_CREATE")
        lifecycle.addObserver(StretchActivityObserver())

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.stretch_title)

        val db = StretchDataBaseHandler(this)
        val stretchData = ArrayList<Stretch>()


        val data = db.readStretchData()
        for (i in 0 until (data.size)) {
            stretchData.add(data[i])
        }

        viewAdapter = StretchRecyclerviewAdapter(stretchData, this@StretchActivity)
        viewManager = LinearLayoutManager(this)

        findViewById<RecyclerView>(R.id.recycler_view_stretches).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = StretchRecyclerviewAdapter(stretchData, this@StretchActivity)
        }

        addNewStretchFunctionality(stretchData, db)

        val itemTouchHelper = ItemTouchHelper(swipeFunctionality(db, stretchData))
        itemTouchHelper.attachToRecyclerView(recycler_view_stretches)
    }

    private fun swipeFunctionality(db: StretchDataBaseHandler, data: ArrayList<Stretch>): ItemTouchHelper.SimpleCallback {

        itemTouchHelperCallBack =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                    (viewAdapter as StretchRecyclerviewAdapter).removeStretchItem(viewHolder, db)
                }

/*                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView

                    if (dX > 0) {
                        swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    }

                    swipeBackground.draw(c)
                    c.save()

                    if (dX > 0) {
                        c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                        c.restore()
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }*/
            }
        return itemTouchHelperCallBack
    }

    private fun addNewStretchFunctionality(data : ArrayList<Stretch>, db : StretchDataBaseHandler) {

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
                    !mStretchDialogView.new_stretch_goal_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_sets_input.text.isBlank()) {

                    mAlertDialog.dismiss()

                    val title = mStretchDialogView.new_stretch_title_input.text.toString()
                    val description = mStretchDialogView.new_stretch_description_input.text.toString()
                    val seconds = mStretchDialogView.new_stretch_time_input.text.toString()
                    val goal = mStretchDialogView.new_stretch_goal_input.text.toString()
                    val sets = mStretchDialogView.new_stretch_sets_input.text.toString()

                    //add input to data array for display
                    val stretch = Stretch(
                        title,
                        description,
                        seconds.toLong(),
                        sets.toLong(),
                        goal.toLong()
                    )

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

    private fun updateViewData(db: StretchDataBaseHandler, myData : ArrayList<Stretch>) {
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
            adapter = StretchRecyclerviewAdapter(myData, this@StretchActivity)
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
