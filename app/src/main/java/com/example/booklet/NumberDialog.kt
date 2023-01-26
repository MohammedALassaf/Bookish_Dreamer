package com.example.booklet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.ContentView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class NumberDialog(val onDoneListener: (pageNumber: String) -> Unit) :
    DialogFragment() {
    var edittext: EditText? = null
    var button: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_text_layout, container, false)
        isCancelable = true
        edittext = view.findViewById(R.id.et_editText)

        edittext?.setOnClickListener {
            edittext?.showKeyboard(requireActivity())
        }
        button = view.findViewById(R.id.et_button)
        edittext?.showKeyboard(requireActivity())
        button?.setOnClickListener {
            onDoneListener(edittext?.text.toString())
            dismiss()
        }


        return view
    }


}