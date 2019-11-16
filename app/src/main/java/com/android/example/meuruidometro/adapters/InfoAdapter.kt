package com.android.example.meuruidometro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.android.example.meuruidometro.R

class InfoAdapter(private val context: Context?) : BaseAdapter() {

    private val infoOptions = arrayOf("A", "B")
    private val inflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.info_item, parent, false)
    }

    override fun getItem(position: Int): Any {
        return infoOptions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return infoOptions.size
    }
}