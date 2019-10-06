package com.threebookers.gridviewhighlight.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.threebookers.gridviewhighlight.R
import kotlinx.android.synthetic.main.view_grid_content.view.*

class GridViewAdapter(val context: Context, val columnNumber: Int, val rowNumber: Int, val testArray: ArrayList<String>) : BaseAdapter() {

    private var randomColumn = (Math.random() * columnNumber).toInt()
    private var randomRow = (Math.random() * (rowNumber-1)).toInt()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.view_grid_content, p2, false)
        if (p0 == randomColumn) {
            SpecialGridViewHolder(view)
        } else {
            GridViewHolder(view)
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return testArray[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return testArray.size
    }

    inner class GridViewHolder(itemView: View) {
        init {
            itemView.grid_view_grid_layout.apply {
                columnCount = 1
                rowCount = rowNumber
            }

            for (i in 0 until rowNumber - 1) {
                val textView = TextView(context)
                textView.setBackgroundResource(R.drawable.shape_gridlayout_default_border)
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.width = GridLayout.LayoutParams.MATCH_PARENT
                layoutParams.setGravity(Gravity.CENTER)
                textView.layoutParams = layoutParams
                itemView.grid_view_grid_layout.addView(textView)
            }

            itemView.grid_view_grid_layout.addView(getGridViewButton())
        }
    }

    inner class SpecialGridViewHolder(itemView: View) {
        init {
            itemView.grid_view_grid_layout.apply {
                columnCount = 1
                rowCount = rowNumber
            }

            for (i in 0 until rowNumber - 1) {
                if (i == randomRow) {
                    itemView.grid_view_grid_layout.addView(getSpecialTextView())
                    continue
                }

                val textView = TextView(context)
                textView.setBackgroundResource(R.drawable.shape_gridlayout_default_border)
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.width = GridLayout.LayoutParams.MATCH_PARENT
                layoutParams.setGravity(Gravity.CENTER)
                textView.layoutParams = layoutParams
                itemView.grid_view_grid_layout.addView(textView)
            }

            itemView.grid_view_grid_layout.addView(getGridViewButton())
        }

        private fun getSpecialTextView() : TextView {
            val textView = TextView(context)
            textView.setBackgroundResource(R.drawable.shape_gridlayout_default_border)
            textView.text = context.getString(R.string.gridViewRandom)
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.setSingleLine()
            val layoutParams = GridLayout.LayoutParams()
            layoutParams.width = GridLayout.LayoutParams.MATCH_PARENT
            layoutParams.setGravity(Gravity.CENTER_HORIZONTAL)
            textView.layoutParams = layoutParams
            return textView
        }
    }

    private fun getGridViewButton() : Button {
        val button = LayoutInflater.from(context).inflate(R.layout.button_material_default, null) as MaterialButton
        button.isClickable = false
        button.isFocusable = false
        return button
    }

}