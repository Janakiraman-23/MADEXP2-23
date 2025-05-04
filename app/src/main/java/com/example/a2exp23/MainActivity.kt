package com.example.a2exp23

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvInput: TextView
    private var inputText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigitClick(view: View) {
        val btn = view as Button
        inputText += btn.text
        tvInput.text = inputText
    }

    fun onOperatorClick(view: View) {
        val btn = view as Button
        inputText += " ${btn.text} "
        tvInput.text = inputText
    }

    fun onFunctionClick(view: View) {
        val btn = view as Button
        inputText += "${btn.text}("
        tvInput.text = inputText
    }

    fun onClearClick(view: View) {
        inputText = ""
        tvInput.text = ""
    }

    fun onEqualClick(view: View) {
        try {
            val result = evaluateExpression(inputText)
            tvInput.text = result.toString()
            inputText = result.toString()
        } catch (e: Exception) {
            tvInput.text = "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        // Basic parsing (for simplicity). You may replace this with a full expression parser.
        val cleaned = expression.replace(" ", "")
        return when {
            cleaned.contains("sqrt(") -> sqrt(evaluateExpression(cleaned.substringAfter("sqrt(").dropLast(1)))
            cleaned.contains("log(") -> ln(evaluateExpression(cleaned.substringAfter("log(").dropLast(1)))
            cleaned.contains("sin(") -> sin(Math.toRadians(evaluateExpression(cleaned.substringAfter("sin(").dropLast(1))))
            cleaned.contains("cos(") -> cos(Math.toRadians(evaluateExpression(cleaned.substringAfter("cos(").dropLast(1))))
            cleaned.contains("tan(") -> tan(Math.toRadians(evaluateExpression(cleaned.substringAfter("tan(").dropLast(1))))
            cleaned.contains("mod") -> {
                val parts = cleaned.split("mod")
                parts[0].toDouble() % parts[1].toDouble()
            }
            cleaned.contains("pow") -> {
                val parts = cleaned.split("pow")
                parts[0].toDouble().pow(parts[1].toDouble())
            }
            cleaned.contains("+") -> {
                val parts = cleaned.split("+")
                parts[0].toDouble() + parts[1].toDouble()
            }
            cleaned.contains("-") -> {
                val parts = cleaned.split("-")
                parts[0].toDouble() - parts[1].toDouble()
            }
            cleaned.contains("*") -> {
                val parts = cleaned.split("*")
                parts[0].toDouble() * parts[1].toDouble()
            }
            cleaned.contains("/") -> {
                val parts = cleaned.split("/")
                parts[0].toDouble() / parts[1].toDouble()
            }
            else -> cleaned.toDouble()
        }
    }
}
