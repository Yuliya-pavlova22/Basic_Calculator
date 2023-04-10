package org.hyperskill.calculator

import android.annotation.SuppressLint
import android.icu.math.BigDecimal
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.lang.reflect.Type
import java.util.*

class Expression () {
    val operands = ArrayDeque<Double>()
    val operators = ArrayDeque<Char>()
    var lastOperand: Double? = null
    var lastOperator: Char? = null
    var isStart: Boolean = true


    fun addOperands(num: Double) {
        operands.addLast(num)
        isStart = false
    }

    fun addOperators(ch: Char) {
        operators.addLast(ch)
        isStart = false

    }

    fun isEnd(): Boolean{
        return operands.size == 2 && operators.size == 1
    }

    fun clear() {
        operands.clear()
        operators.clear()
        lastOperand = null
        lastOperator = null
        isStart = true
    }

    @SuppressLint("NewApi")
    fun result(): Double {
        println(operands)
        println(operators)


        while (operators.isNotEmpty()) {
            val op1 = operands.pop()
            val op2 = operands.pop()

            val op = operators.pop()
            operands.push(applyOp(op, op1, op2))
        }
        return operands.pop()
    }


    @SuppressLint("NewApi")
    fun applyOp(op: Char, a: Double, b: Double): Double {
        when (op) {
            '+' -> return a + b
            '-' -> return a - b
            '*' -> return a * b
            '/' -> return a / b
        }
        return 0.0
    }

}



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ex = Expression()

        var disEdTxt: EditText = findViewById(R.id.displayEditText)

        disEdTxt.inputType = InputType.TYPE_NULL

        val btn0: Button = findViewById(R.id.button0)
        val btn1: Button = findViewById(R.id.button1)
        val btn2: Button = findViewById(R.id.button2)
        val btn3: Button = findViewById(R.id.button3)
        val btn4: Button = findViewById(R.id.button4)
        val btn5: Button = findViewById(R.id.button5)
        val btn6: Button = findViewById(R.id.button6)
        val btn7: Button = findViewById(R.id.button7)
        val btn8: Button = findViewById(R.id.button8)
        val btn9: Button = findViewById(R.id.button9)

        val btnClear: Button = findViewById(R.id.clearButton)
        val btnDot: Button = findViewById(R.id.dotButton)
        val btnSub: Button = findViewById(R.id.subtractButton)
        val btnAdd: Button = findViewById(R.id.addButton)
        val btnMultiply: Button = findViewById(R.id.multiplyButton)
        val btnDivide: Button = findViewById(R.id.divideButton)
        val btnEqual: Button = findViewById(R.id.equalButton)


        btnMultiply.setOnClickListener {
            var num = if (disEdTxt.text.toString() == "") disEdTxt.hint.toString().toDouble() else
                disEdTxt.text.toString().toDouble()
            ex.lastOperator = '*'
            ex.lastOperand = num
            ex.addOperands(num)
            ex.addOperators('*')
            disEdTxt.text.clear()
            if (num % 1 != 0.0) disEdTxt.hint = num.toString() else
                disEdTxt.hint = String.format("%.0f", num)

            if (ex.isEnd()) {
                var res = ex.result()
                ex.addOperands(res)
            }
        }

        btnDivide.setOnClickListener {

            var num = if (disEdTxt.text.toString() == "") disEdTxt.hint.toString().toDouble() else
                disEdTxt.text.toString().toDouble()
            ex.lastOperator = '/'
            ex.lastOperand = num
            ex.addOperands(num)
            ex.addOperators('/')
            disEdTxt.text.clear()
            if (num % 1 != 0.0) disEdTxt.hint = num.toString() else
                disEdTxt.hint = String.format("%.0f", num)

            if (ex.isEnd()) {
                var res = ex.result()
                ex.addOperands(res)
            }
        }

        btnAdd.setOnClickListener {
            var num = if (disEdTxt.text.toString() == "") disEdTxt.hint.toString().toDouble() else
                disEdTxt.text.toString().toDouble()
            ex.addOperands(num)
            ex.lastOperator = '+'
            ex.lastOperand = num
            ex.addOperators('+')
            disEdTxt.text.clear()
            if (num % 1 != 0.0) disEdTxt.hint = num.toString() else
                disEdTxt.hint = String.format("%.0f", num)

            if (ex.isEnd()) {
                var res = ex.result()
                ex.addOperands(res)
            }

        }

        btnSub.setOnClickListener {
            try {

                var num = 0.0
                // если самое начало
                if (ex.isStart && disEdTxt.text.toString() == "" ) {
                    disEdTxt.setText("-")
                    ex.isStart = false
                    return@setOnClickListener
                } else {
                    if (ex.operands.size == ex.operators.size && ex.operands.size != 0){
                        disEdTxt.setText("-")
                    } else
                        if (disEdTxt.text.toString() == "0."){
                            disEdTxt.setText("-")}


                    if (disEdTxt.text.toString() == "" && disEdTxt.hint.toString() != "") {
                        num = disEdTxt.hint.toString().toDouble()
                    } else {
                        num = disEdTxt.text.toString().toDouble()
                    }

                    ex.addOperands(num)

                    ex.lastOperator = '-'
                    ex.lastOperand = num
                    ex.addOperators('-')
                    disEdTxt.text.clear()
                    if (num % 1 != 0.0) disEdTxt.hint = num.toString() else
                        disEdTxt.hint = String.format("%.0f", num)

                    if (ex.isEnd()) {
                        var res = ex.result()
                        ex.addOperands(res)
                    }
                }

            } catch (e: Exception) {
                println("ERRRRROR ----- $e")
            }
        }



            btnEqual.setOnClickListener {
                var rez: Double = 0.0
                try {
                    var num: Double = 0.0

                        if (disEdTxt.text.toString() != "") {
                            num = disEdTxt.text.toString().toDouble()
                            ex.addOperands(num)
                            disEdTxt.text.clear()
                            ex.lastOperand = num
                        } else {
                            ex.addOperands(disEdTxt.hint.toString().toDouble())
                        }

                    if (ex.operands.size == 1 && ex.operators.size == 1) {
                        ex.addOperands(ex.lastOperand!!)
                    }
                    if (ex.operands.size == 1 && ex.operators.size == 0) {
                        ex.addOperators(ex.lastOperator!!)
                        ex.addOperands(ex.lastOperand!!)
                    }

                    if (ex.operands.size > 2 && ex.operators.size >= 2) {
                    }

                    rez = ex.result()

                    if (rez % 1 != 0.0) disEdTxt.hint = rez.toString() else
                        disEdTxt.hint = String.format("%.0f", rez)
                    println(rez)


                } catch (e: Exception) {
                    println("ERRRRROR ----- $e")
                    println(ex.operators.toString())
                    println(ex.operands.toString())
                }

            }


            btnClear.setOnClickListener {
                disEdTxt.text.clear()
                disEdTxt.hint = "0"
                ex.clear()
            }


            btnDot.setOnClickListener {
                if (!disEdTxt.text.contains('.')) {
                    if (disEdTxt.text.toString() == "" || disEdTxt.text.toString() == "-") {
                        disEdTxt.append("0.")
                    } else disEdTxt.append(".")

                }


            }

            btn0.setOnClickListener {
                if (disEdTxt.text.toString() != "-")
                    if (disEdTxt.text.toString() != "0") disEdTxt.append("0")

            }
            btn1.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("1")
            }
            btn2.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("2")
            }
            btn3.setOnClickListener {
                disEdTxt.append("3")
            }
            btn4.setOnClickListener {
                disEdTxt.setTextColor(resources.getColor(R.color.colorText))
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("4")
            }
            btn5.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("5")
            }
            btn6.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("6")
            }
            btn7.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("7")
            }
            btn8.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("8")
            }
            btn9.setOnClickListener {
                if (disEdTxt.text.toString() == "0") disEdTxt.text.clear()
                disEdTxt.append("9")
            }


        }
    }

