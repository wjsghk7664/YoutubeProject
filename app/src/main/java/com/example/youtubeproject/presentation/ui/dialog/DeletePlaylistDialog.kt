package com.example.youtubeproject.presentation.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.youtubeproject.R

class DeletePlaylistDialog(
    private val onConfirm: () -> (Unit)
): DialogFragment() {

    companion object {
        const val TAG = "DELETE_PLAYLIST_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.delete_playlist_dialog_title))
                .setPositiveButton(getString(R.string.dialog_confirm_text)) { dialog, id ->
                    onConfirm()
                    dismiss()
                }
                .setNegativeButton(getString(R.string.dialog_cancel_text)) { dialog, id ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}