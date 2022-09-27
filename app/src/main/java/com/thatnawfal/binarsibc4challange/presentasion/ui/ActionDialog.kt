package com.thatnawfal.binarsibc4challange.presentasion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.thatnawfal.binarsibc4challange.databinding.DialogActionBinding

class ActionDialog(
    private val listener: buttonClickListener
): DialogFragment() {

    private lateinit var binding: DialogActionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogActionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnActionDialogEdit.setOnClickListener {
            dismiss()
            listener.actionEdit()
        }
        binding.btnActionDialogDelete.setOnClickListener {
            listener.actionDelete()
        }
    }
}

interface buttonClickListener {
    fun actionEdit()
    fun actionDelete()
}