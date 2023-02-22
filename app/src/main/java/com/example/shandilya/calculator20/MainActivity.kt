package com.example.shandilya.calculator20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.shandilya.calculator20.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private  lateinit var expression: Expression


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClearClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTV.text = ""
        stateError = false
        lastNumeric = false
        lastDot = false
        binding.resultTV.visibility = View.GONE
    }



    fun onBackClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)


        try {
            val lastChar = binding.dataTv.toString().last()

            if(lastChar.isDigit()){
                onEqual()
            }
        } catch (e : Exception){
            binding.resultTV.text = ""
            binding.resultTV.visibility = View.GONE

            Log.e("last char error",e.toString())
        }
    }


    fun onOperatorClick(view: View) {
        if(!stateError && lastNumeric){
            binding.dataTv.append((view as Button).text)

            lastDot = false
            lastNumeric = false

            onEqual()
        }
    }


    fun onDigitClick(view: View) {
        if(stateError) {
            binding.dataTv.text = (view as Button).text
            stateError = false
        } else{
            binding.dataTv.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }


    fun onAllclearClick(view: View) {
        binding.dataTv.text = ""
        lastNumeric = false
    }


    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.text = binding.resultTV.text.toString().drop(1)

    }


    fun onEqual(){
        if(lastNumeric && !stateError){
            val text = binding.dataTv.text.toString()

            expression = ExpressionBuilder(text).build()


             try {
                val result = expression.evaluate()

                binding.resultTV.visibility = View.VISIBLE

                binding.resultTV.text = "=" + result.toString()
            } catch (ex: ArithmeticException) {
                Log.e("evaluate error", ex.toString())

                 binding.resultTV.text = "Error"
                 stateError = true
                 lastNumeric = false
            }
        }
    }
}