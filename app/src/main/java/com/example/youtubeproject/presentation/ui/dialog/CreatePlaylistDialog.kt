package com.example.youtubeproject.presentation.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.youtubeproject.R

class CreatePlaylistDialog(
    private val onConfirm: (String) -> (Unit)
): DialogFragment() {

    private val titleEditText by lazy {
        EditText(requireContext()).apply {
            textSize = 18F
            hint = "플레이 리스트 제목"
        }
    }
    companion object {
        const val TAG = "CREATE_PLAYLIST_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.create_playlist_dialog_title))
                .setView(titleEditText)
                .setPositiveButton(getString(R.string.dialog_confirm_text)) { dialog, id ->
                    if(titleEditText.text.isNotBlank()) {
                        onConfirm(titleEditText.text.toString())
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton(getString(R.string.dialog_cancel_text)) { dialog, id ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}