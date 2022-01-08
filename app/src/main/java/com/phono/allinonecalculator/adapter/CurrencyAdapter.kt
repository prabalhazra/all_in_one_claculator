package com.phono.allinonecalculator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.textview.MaterialTextView
import com.phono.allinonecalculator.R
import com.phono.allinonecalculator.model.CurrencyAndCountry

class CurrencyAdapter(context: Context, currencyList: List<CurrencyAndCountry>) :
    ArrayAdapter<CurrencyAndCountry>(context, 0, currencyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val currency = getItem(position)
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_item, parent, false)

        val countryName: MaterialTextView = view.findViewById(R.id.country_name)
        val countryCode: MaterialTextView = view.findViewById(R.id.country_code)

        if (currency != null) {
            countryName.text = currency.CountryName
            countryCode.text = currency.CountyCode
        }

        return view

    }

}