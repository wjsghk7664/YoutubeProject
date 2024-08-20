package com.example.youtubeproject.presentation.adapter

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
    private val items: List<String>
) : ArrayAdapter<String>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 스피너에 선택된 항목이 표시될 때는 아이콘을 표시
        return createViewFromResource(convertView, parent, position, true)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 드롭다운 목록에서는 텍스트만 표시하고, 배경색을 검은색으로 설정
        return createViewFromResource(convertView, parent, position, false).apply {
            setBackgroundColor(context.resources.getColor(android.R.color.black, null))
            val textView: TextView = findViewById(R.id.spinner_item_text)
            textView.setTextColor(context.resources.getColor(android.R.color.white, null))
        }
    }

    private fun createViewFromResource(convertView: View?, parent: ViewGroup, position: Int, showIcon: Boolean): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)
        val textView: TextView = view.findViewById(R.id.spinner_item_text)
        val imageView: ImageView = view.findViewById(R.id.spinner_item_icon)

        textView.text = items[position]

        // 드롭다운 목록에서는 아이콘을 숨기기
        imageView.visibility = if (showIcon) View.VISIBLE else View.GONE

        return view
    }
}
