package `2020`

import java.io.File

fun main() {

    var list = mutableListOf<String>()
    File("input2020/input5.txt").forEachLine { list.add(it) }
    var s = File("input2020/input5.txt").readLines()[0]
    //var list = s.split(",").map { it -> Integer.parseInt(it) }.toMutableList()
    var highest = 0L
    var seats = mutableListOf<Long>()
    for(l in list){
        val seatID = seatID(l)
        seats.add(seatID)
        if(seatID >highest){
    highest = seatID
}
    }
    println(highest)
    //listOf(1..2).max()
    (0..highest).toList()
   println(((0..highest).toList()-seats).max())
for(i in 0..highest){
    if(!seats.contains(i)){
        println(i)
    }
}
}

private fun seatID(s:String):Long{
    var low = 0
    var high = 127
    for(i in 0..6){
      if(s[i]=='F'){
          high = high - (high-low)/2-1
      }
        else if(s[i]=='B'){
          low = low + (high-low)/2+1
      }
    }

    var high2 = 7
    var low2 =0
    for(i in 7..9){
        if(s[i]=='L'){
            high2 = high2 - (high2-low2)/2-1
        }
        else if(s[i]=='R'){
            low2 = low2 + (high2-low2)/2+1
        }
    }
    return low*8L + low2
}