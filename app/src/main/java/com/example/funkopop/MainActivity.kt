package com.example.funkopop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import java.text.NumberFormat

class MainActivity : AppCompatActivity(), TextWatcher {

    private var funkoList = ArrayList<Funko>()
    private var funkoAdapter = FunkoAdapter(this, this.funkoList)
    lateinit var funkoCount: TextView
    lateinit var totalPrice: TextView
    val currencyFormat = NumberFormat.getCurrencyInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = this.findViewById<RecyclerView>(R.id.funko_recycler_view)
        val layoutManager = LinearLayoutManager(this.applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = funkoAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this,
            LinearLayoutManager.VERTICAL))

        this.funkoCount = this.findViewById(R.id.current_number_of_funko_text_view)
        this.totalPrice = this.findViewById(R.id.current_funko_total_price_text_view)

        val searchEditText: EditText = this.findViewById(R.id.funko_search_edit_text)
        searchEditText.addTextChangedListener(this)

        val newFunkoFab = this.findViewById<FloatingActionButton>(R.id.new_funko_fab)
        newFunkoFab.setOnClickListener { newFunkoHandler() }

        this.updateTextViews()
    }

    override fun onPause() {
        super.onPause()
        Serializer(this.applicationContext).save(this.funkoList)
    }

    override fun onResume() {
        super.onResume()
        val serializer = Serializer(this.applicationContext)
        try {
            val list = serializer.load()
            this.funkoList.clear()
            this.funkoList.addAll(list)
            this.funkoAdapter.notifyDataSetChanged()
        } catch (ex: IOException) {
            // ??
        }
        this.updateTextViews()
    }

    fun showFunko(positionIndex: Int) {
        val dialog = ViewFunkoFragment()
        dialog.setFunko(this.funkoList[positionIndex])
        dialog.show(this.supportFragmentManager, "")
    }

    fun setFunko(funko: Funko) {
        this.funkoList.add(funko)
        this.funkoAdapter.notifyDataSetChanged()
        this.updateTextViews()
    }

    private fun newFunkoHandler() {
        val dialog = AddFunkoFragment()
        dialog.show(this.supportFragmentManager, "")
    }

    private fun filterFunkos(userInput: String) {
        val filteredFunkoList = ArrayList<Funko>()

        for (funko in this.funkoList) {
            if (funko.name.uppercase() == userInput ||
                funko.group.uppercase() == userInput) {
                filteredFunkoList.add(funko)
            }
        }
        this.funkoAdapter.filterList(filteredFunkoList)

        this.funkoCount.text = this.getString(R.string.total_funkos_text_view_text,
            filteredFunkoList.size.toString())
        this.totalPrice.text = this.getString(R.string.total_cost_text_view_text,
            this.currencyFormat.format(this.getTotalPrice(filteredFunkoList)))

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!(s.isNullOrEmpty())) {
            val userInput = s.toString()
            this.filterFunkos(userInput.uppercase())
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.isNullOrEmpty()) {
            this.funkoAdapter.filterList(this.funkoList)
            this.updateTextViews()
        }
    }

    private fun getTotalPrice(filteredList: List<Funko>): Double {
        var totalPrice = 0.0
        if (filteredList.isNotEmpty()) {
            for (funko in filteredList) {
                totalPrice += funko.price
            }
        }
        return totalPrice
    }

    private fun updateTextViews() {

        if (this.funkoList.isNotEmpty()) {
            this.funkoCount.text = this.getString(R.string.total_funkos_text_view_text,
                this.funkoList.size.toString())
            this.totalPrice.text = this.getString(R.string.total_cost_text_view_text,
                this.currencyFormat.format(this.getTotalPrice(funkoList)))
        } else {
            this.funkoCount.text = this.getString(R.string.total_funkos_text_view_text, '0')
            this.totalPrice.text = this.getString(R.string.total_cost_text_view_text,
                this.currencyFormat.format(0.0))
        }
    }


}