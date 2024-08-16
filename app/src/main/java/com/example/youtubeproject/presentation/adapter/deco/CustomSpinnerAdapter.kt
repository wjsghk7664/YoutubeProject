package com.example.youtubeproject.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.youtubeproject.R

class CustomSpinnerAdapter(
    context: Context,
    private val items: List<String>,
    private val icons: List<Int>
) : ArrayAdapter<String>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    private fun createViewFromResource(convertView: View?, parent: ViewGroup, position: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)
        val textView: TextView = view.findViewById(R.id.spinner_item_text)
        val imageView: ImageView = view.findViewById(R.id.spinner_item_icon)

        textView.text = items[position]
        imageView.setImageResource(icons[position])

        return view
    }
}
