package com.phono.allinonecalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phono.allinonecalculator.R
import com.phono.allinonecalculator.databinding.FragmentEmiCalculatorBinding
import java.text.NumberFormat
import java.util.*
import kotlin.math.pow

class EMICalculatorFragment : Fragment() {

    private var _binding: FragmentEmiCalculatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmiCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calculateBtn.setOnClickListener {
            calculateInterestRate()
        }
    }

    private fun calculateInterestRate() {

        val r = binding.interestRateEditText.text.toString().toDouble() / (12 * 100)
        val t = binding.loanTenureEditText.text.toString().toDouble() * 12

        val emi = (binding.loanAmountEditText.text.toString()
            .toDouble() * r * (1 + r).pow(t)) / ((1 + r).pow(t) - 1)
        val formattedEmi = NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(emi)
        binding.monthlyEmi.text = getString(R.string.emi, formattedEmi)
        binding.dotLine1.visibility = View.VISIBLE

        val compoundInterest = emi * t
        val formattedTotalInterest =
            NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(compoundInterest)
        binding.totalPayment.text = getString(R.string.total_amount, formattedTotalInterest)
        binding.dotLine2.visibility = View.VISIBLE

        val interest = compoundInterest - binding.loanAmountEditText.text.toString().toDouble()
        val formattedInterest =
            NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(interest)
        binding.totalInterest.text = getString(R.string.total_interest, formattedInterest)
        binding.dotLine3.visibility = View.VISIBLE

        val totalAmount = binding.loanAmountEditText.text.toString().toDouble()
        val formattedAmount =
            NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(totalAmount)
        binding.amount.text = getString(R.string.amount, formattedAmount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
