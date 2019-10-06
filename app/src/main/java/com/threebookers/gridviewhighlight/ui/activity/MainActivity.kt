package com.threebookers.gridviewhighlight.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.threebookers.gridviewhighlight.R
import com.threebookers.gridviewhighlight.ui.adapter.GridViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_input_content.*

class MainActivity : AppCompatActivity() {

    private lateinit var dialog : AlertDialog
    //抓取輸入的行和列
    private var rowNumber = String()
    private var columnNumber = String()
    //選取的Index
    private var selectedIndex = -1
    private var mSelectedView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDialog()
    }

    private fun initDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle(R.string.dialog_title)
            setView(R.layout.dialog_input_content)
            setFinishOnTouchOutside(false)
            setPositiveButton(this@MainActivity.getString(R.string.dialog_positive_button_text_view), null)
            setCancelable(false)
        }

        dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (checkParams()) {
                    createGridView()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "請輸入行和列", Toast.LENGTH_SHORT).show()
                }
            }
        }
        dialog.show()
    }

    private fun checkParams() : Boolean {
        columnNumber = dialog.input_column_edit_text.editableText.toString()
        rowNumber = dialog.input_row_edit_text.editableText.toString()
        Log.d("peter", "columnNumber=$columnNumber, rowNumber=$rowNumber")
        root_title.text = String.format(this.getString(R.string.root_title_text_view), columnNumber, rowNumber)
2
        if (columnNumber.isNotBlank() && rowNumber.isNotBlank()) {
            return true
        }
        return false
    }

    private fun createGridView() {
        val testArray = ArrayList<String>()
        for (i in 0 until columnNumber.toInt()) {
            testArray.add("測試$i")
        }
        val gridViewAdapter = GridViewAdapter(this, columnNumber.toInt(), rowNumber.toInt(), testArray)

        root_grid_view.apply {
            numColumns = columnNumber.toInt()
            stretchMode = GridView.STRETCH_COLUMN_WIDTH
            setOnItemClickListener { adapterView, view, i, l ->
                if (selectedIndex != i) {
                    resetSelection()
                    selectedIndex = i
                    view.setBackgroundResource(R.drawable.shape_gridview_border)
                    mSelectedView = view
                } else {
                    resetSelection()
                }
            }
            adapter = gridViewAdapter
        }
    }

    private fun resetSelection() {
        if (mSelectedView != null) {
            mSelectedView?.setBackgroundResource(android.R.color.white)
        }
        mSelectedView = null

    }
}
