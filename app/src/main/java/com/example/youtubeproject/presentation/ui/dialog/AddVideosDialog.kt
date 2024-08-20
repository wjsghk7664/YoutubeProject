package com.example.youtubeproject.presentation.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.youtubeproject.R


class AddVideosDialog(
    private val onConfirm: (List<String>) -> (Unit)
) : DialogFragment() {

    private lateinit var checkedItems: BooleanArray

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val titles = arguments?.getStringArray(VIDEO_TITLES)!!
            checkedItems = BooleanArray(titles.size)

            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.add_videos_dialog_title))
                .setMultiChoiceItems(titles, checkedItems) { dialog, position, isChecked ->
                    checkedItems[position] = isChecked
                }
                .setPositiveButton(getString(R.string.dialog_confirm_text)) { dialog, id ->
                    val lists = titles.filterIndexed { index, it ->
                        checkedItems[index]
                    }

                    if(lists.isNotEmpty()) {
                        onConfirm(lists)
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton(getString(R.string.dialog_cancel_text)) { dialog, id ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        private const val VIDEO_TITLES = "VIDEO_TITLES"

        @JvmStatic
        fun newInstance(onConfirm: (List<String>) -> Unit, titles: List<String>) =
            AddVideosDialog(onConfirm).apply {
                arguments = Bundle().apply {
                    putStringArray(VIDEO_TITLES, titles.toTypedArray())
                }
            }
    }
}