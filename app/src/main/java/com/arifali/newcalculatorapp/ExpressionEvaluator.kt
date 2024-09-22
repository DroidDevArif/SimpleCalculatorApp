package com.arifali.newcalculatorapp

import net.objecthunter.exp4j.ExpressionBuilder

class ExpressionEvaluator {
    fun eval(expression: String): Double {
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }


}
