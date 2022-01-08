package com.phono.allinonecalculator.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import com.phono.allinonecalculator.Constant
import com.phono.allinonecalculator.R
import com.phono.allinonecalculator.adapter.CurrencyAdapter
import com.phono.allinonecalculator.adapter.CurrencyToAdapter
import com.phono.allinonecalculator.databinding.FragmentCurrencyConverterBinding
import com.phono.allinonecalculator.model.CurrencyAndCountry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class CurrencyConverter : Fragment() {

    private var _binding: FragmentCurrencyConverterBinding? = null
    private val binding get() = _binding!!
    var currency = "EUR"
    var convertedToCurrency = "USD"
    private var conversionRate = 0f

    private val countryList: List<CurrencyAndCountry> = Constant.CURRENCY_CODES_LIST

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConverterBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerSetup()

        binding.convertBtn.setOnClickListener {
            getData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {

        if (binding.etConversion.text.toString()
                .isNotEmpty() && binding.etConversion.text.toString().isNotBlank()
        ) {
            val api = "https://v6.exchangerate-api.com/v6/4fde8a9c64f1f0a982f24027/latest/$currency"

            CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {

                try {

                    val apiResult = URL(api).readText()
                    val jsonObject = JSONObject(apiResult)
                    conversionRate =
                        jsonObject.getJSONObject("conversion_rates")
                            .getString(convertedToCurrency)
                            .toFloat()


                    withContext(Dispatchers.Main) {
                        val text =
                            ((binding.etConversion.text.toString()
                                .toFloat()) * conversionRate).toString()

                        binding.resultTxt.text = "$text $convertedToCurrency"
                        binding.dotLine1.visibility = View.VISIBLE
                        binding.result.text = "1 $currency = $conversionRate $convertedToCurrency"
                    }
                } catch (e: Exception) {
                    Log.e("Main", "$e")
                }
            }
        }
    }

    private fun spinnerSetup() {

        val adapter = CurrencyAdapter(requireContext(), countryList)
        val adapterTo = CurrencyToAdapter(requireContext(), countryList)

        binding.spinner1.adapter = adapter
        binding.spinner2.adapter = adapterTo

        binding.spinner1.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val countryCodeFrom: String =
                    ((requireView().findViewById<MaterialTextView>(R.id.country_code)).text.toString())
                currency = countryCodeFrom
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        })

        binding.spinner2.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val countryCodeTo: String =
                    ((requireView().findViewById<MaterialTextView>(R.id.country_code_to)).text.toString())
                convertedToCurrency = countryCodeTo
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}