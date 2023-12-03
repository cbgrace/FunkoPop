package com.example.funkopop


import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.text.NumberFormat


class ViewFunkoFragment : DialogFragment() {

    private var currentFunko = Funko()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.requireActivity())
        val inflater = this.requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_view_funko, null, false)

        val currencyFormat = NumberFormat.getCurrencyInstance()

        view.findViewById<TextView>(R.id.view_name_text_view).text =
            this.getString(R.string.view_name_text_view_text, this.currentFunko.name)
        view.findViewById<TextView>(R.id.view_group_text_view).text =
            this.getString(R.string.view_group_text_view_text, this.currentFunko.group)
        view.findViewById<TextView>(R.id.view_number_text_view).text =
            this.getString(R.string.view_number_text_view_text, this.currentFunko.number)
        view.findViewById<TextView>(R.id.view_price_text_view).text =
            this.getString(R.string.view_price_text_view_text,
                currencyFormat.format(this.currentFunko.price))
        view.findViewById<TextView>(R.id.view_size_text_view).text =
            this.getString(R.string.view_size_text_view_text, this.currentFunko.size)

        val okButton: Button = view.findViewById(R.id.view_ok_button)
        okButton.setOnClickListener { okHandler() }
        builder.setView(view).setMessage(R.string.view_selected_funko_text)
        return builder.create()
    }

    private fun okHandler() {
        this.dismiss()
    }

    public fun setFunko(funko: Funko) {
        this.currentFunko = funko
    }

}