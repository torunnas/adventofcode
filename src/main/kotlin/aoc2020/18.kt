package main.kotlin.aoc2020

import java.io.File
import java.lang.StringBuilder
import java.math.BigInteger

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input18.txt").forEachLine { list.add(it) }
    var s = File("input2020/input18.txt").readLines()[0]
    //var list = s.split(",").map { it -> Integer.parseInt(it) }.toMutableList()
    var res = BigInteger.ZERO
    var j = 0
    for (a in list){
        //val evaluate = evaluate(a)
        //println(evaluate)
        res += evaluateInner(a).toBigInteger()
        j++

    }
    println(res)
}
private fun evaluateNumber(s:String):BigInteger{
    val split = s.split('*')
    var sum = BigInteger.ZERO
    for(a in split){
        var b = a.trim()
        if(b.toLongOrNull() != null){
            var c = b.toLong()
            if(sum == BigInteger.ZERO){
                sum = BigInteger.valueOf(c)
            }
            else{
                sum *=BigInteger.valueOf(c)
            }
        }
        else if(a.contains('+')){
            val splita = a.split('+')
            var v = BigInteger.ZERO
            for(c in splita){
                if(c.trim().toLongOrNull() != null){
                    v += BigInteger.valueOf(c.trim().toLong())
                }
            }
            if(sum == BigInteger.ZERO){
                sum = v
            }
            else{
                sum *=v
            }
        }
    }
    return sum
}

private fun evaluateInner(s:String):String{
    var i = 0
    var  g = StringBuilder()
    if(!s.contains('(')){
        g.append(evaluateNumber(s))
        return g.toString()
    }
    while(i < s.length) {
        var b = s[i]
        var new = StringBuilder()
        if (b == '(') {
            var par = 1
            i++
            while (par != 0) {
                if (s[i] == ')') {
                    par--
                } else if (s[i] == '(') {
                    par++
                }
                if (par != 0) {
                    new.append(s[i])
                }

                i++
            }
            g.append(evaluateInner(new.toString()))
        }
        else{
            g.append(b)
        }
        i++
    }
    if(!g.toString().contains('(')){
        return evaluateNumber(g.toString()).toString()
    }
    return g.toString()
}


private fun evaluate(s:String):BigInteger{
    var sum = BigInteger.ZERO
    var op = ' '
    var i  = 0
    var number= BigInteger.ZERO
    while(i < s.length){
        var b = s[i]
        if(b.isDigit()){
            number = BigInteger.valueOf(Integer.parseInt(b.toString()).toLong())
        }
        else if(b in listOf('*', '+')){
            op = b
        }
        else if(b == '('){
            var  g = StringBuilder()
            var par = 1
            i++
            while(par != 0){
                if(s[i] == ')'){
                    par--
                }
                else if(s[i]=='('){
                    par++
                }
                if(par!= 0){
                    g.append(s[i])
                }

                i++
            }
            number = evaluate(g.toString())
        }

            if(op == '+' && number != BigInteger.ZERO){
                sum += number
                number = BigInteger.ZERO
            }
            else if(op == '*' && number != BigInteger.ZERO){
                sum *= number
                number = BigInteger.ZERO
            }
            else if(number != BigInteger.ZERO){
                sum = number
                number = BigInteger.ZERO
            }
        i++
    }
    return sum
}