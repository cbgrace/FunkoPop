package com.example.funkopop

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AddFunkoFragment : DialogFragment() {

    lateinit var nameEditText: EditText
    lateinit var groupEditText: EditText
    lateinit var numberEditText: EditText
    lateinit var priceEditText: EditText
    lateinit var sizeEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = this.requireActivity().layoutInflater
        val builder = AlertDialog.Builder(this.requireActivity())

        val dialogFragmentView = inflater.inflate(R.layout.fragment_add_funko,
            null, false)

        this.nameEditText = dialogFragmentView.findViewById(R.id.add_name_edit_text)
        this.groupEditText = dialogFragmentView.findViewById(R.id.add_group_edit_text)
        this.numberEditText = dialogFragmentView.findViewById(R.id.add_number_edit_text)
        this.priceEditText = dialogFragmentView.findViewById(R.id.add_price_edit_text)
        this.sizeEditText = dialogFragmentView.findViewById(R.id.add_size_edit_text)

        val saveButton: Button = dialogFragmentView.findViewById(R.id.add_save_button)
        saveButton.setOnClickListener { saveButtonHandler() }

        val cancelButton: Button = dialogFragmentView.findViewById(R.id.add_cancel_button)
        cancelButton.setOnClickListener { cancelButtonHandler() }

        builder.setView(dialogFragmentView).setMessage(R.string.add_fragment_message)
        return builder.create()
    }

    private fun saveButtonHandler() {
        if (this.validationCheck()) {
            val name = this.nameEditText.text.toString()
            val group = this.groupEditText.text.toString()
            val number = this.numberEditText.text.toString().toInt()
            val price = this.priceEditText.text.toString().toDouble()
            val size = this.sizeEditText.text.toString().toInt()

            val newFunko = Funko(name, group, number, price, size)
            val callingActivity = this.activity as MainActivity
            callingActivity.setFunko(newFunko)
            this.dismiss()
        }
    }

    private fun cancelButtonHandler() {
        this.dismiss()
    }

    private fun validationCheck(): Boolean {
        val name = this.nameEditText.text
        val group = this.groupEditText.text
        val number = this.numberEditText.text
        val price = this.priceEditText.text
        val size = this.sizeEditText.text

        if (name.isEmpty()) {
            Toast.makeText(this.activity, R.string.add_field_blank_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (group.isEmpty()) {
            Toast.makeText(this.activity, R.string.add_field_blank_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (number.isEmpty()) {
            Toast.makeText(this.activity, R.string.add_field_blank_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (number.toString().toInt() <= 0) {
            Toast.makeText(this.activity, R.string.add_neg_number_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (price.isEmpty()) {
            Toast.makeText(this.activity, R.string.add_field_blank_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (price.toString().toDouble() <= 0.0) {
            Toast.makeText(this.activity, R.string.add_neg_price_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (size.isEmpty()) {
            Toast.makeText(this.activity, R.string.add_field_blank_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else if (!(size.toString().toInt() == 1 || size.toString().toInt() == 3 ||
                    size.toString().toInt() == 6 || size.toString().toInt() == 10 ||
                    size.toString().toInt() == 18)) {
            Toast.makeText(this.activity, R.string.add_bad_size_toast_message, Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }
}