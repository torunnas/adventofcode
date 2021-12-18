import java.io.File
import java.lang.StringBuilder

data class Packet(val value: Long, val leftOvers: String, val versionSum: Int)

fun main() {
    val lines = File("input2021/input16.txt").readLines()
    val bits =
        lines[0].map { it.toString().toInt(16).toString(2) }.map { if (it.length != 4) it.padStart(4, '0') else it }
            .joinToString("")
    val result = decodePacket(bits)
    println("Part1: ${result.versionSum}")
    println("Part2: ${result.value}")
}

private fun decodePacket(packetBits: String): Packet {
    val packetValues = mutableListOf<Long>()
    var versionSum = 0
    val version = getVersion(packetBits)
    versionSum += version
    val typeId = getTypeId(packetBits)
    var currentIndex = 6
    if (typeId == 4) {
        val literal = getLiteral(packetBits.substring(currentIndex))
        return Packet(literal.first, literal.second, versionSum)
    } else {
        val lengthTypeId = packetBits[currentIndex].toString().toInt()
        currentIndex++
        if (lengthTypeId == 0) {
            val subPacketLength = packetBits.substring(currentIndex..currentIndex + 14).toInt(2)
            currentIndex += 15
            var subPackets = packetBits.substring(currentIndex until currentIndex + subPacketLength)
            currentIndex += subPacketLength
            while (subPackets.isNotEmpty()) {
                val decodedPacket = decodePacket(subPackets)
                packetValues.add(decodedPacket.value)
                subPackets = decodedPacket.leftOvers
                versionSum += decodedPacket.versionSum
            }
        } else if (lengthTypeId == 1) {
            val numberOfSubPackets = packetBits.substring(currentIndex..currentIndex + 10).toLong(2)
            currentIndex += 11
            var subPackets = packetBits.substring(currentIndex)
            for (k in 1..numberOfSubPackets) {
                val decodedPacket = decodePacket(subPackets)
                packetValues.add(decodedPacket.value)
                currentIndex += subPackets.length - decodedPacket.leftOvers.length
                subPackets = decodedPacket.leftOvers
                versionSum += decodedPacket.versionSum
            }
        }
    }
    val value = getValue(typeId, packetValues)
    return Packet(value!!.toLong(), packetBits.substring(currentIndex), versionSum)
}

private fun getTypeId(packetBits: String) = packetBits.substring(3..5).toInt(2)

private fun getVersion(packetBits: String) = packetBits.substring(0..2).toInt(2)

private fun getValue(typeId: Int, packets: MutableList<Long>) =
    when (typeId) {
        0 -> packets.sum()
        1 -> packets.reduce { acc, t -> acc * t }
        2 -> packets.minOrNull()
        3 -> packets.maxOrNull()
        5 -> if (packets.first() > packets.last()) 1 else 0
        6 -> if (packets.first() < packets.last()) 1 else 0
        7 -> if (packets.first() == packets.last()) 1 else 0
        else -> 0
    }

private fun getLiteral(bitsInput: String): Pair<Long, String> {
    var i = 0
    val literal = StringBuilder()
    var padding = bitsInput.first()
    while (i < bitsInput.length && padding == '1') {
        literal.append(bitsInput.substring(i + 1..i + 4))
        i += 5
        padding = bitsInput[i]
    }
    literal.append(bitsInput.substring(i + 1..i + 4))
    i += 5
    return Pair(literal.toString().toLong(2), bitsInput.substring(i))
}