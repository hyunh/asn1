package hyunh.asn1

fun ByteArray.toHexString(): String {
    return StringBuilder(this.size * 2).also { builder ->
        val hex = "0123456789ABCDEF"
        forEach {
            builder.append(hex[it.toInt().ushr(4) and 0x0F])
            builder.append(hex[it.toInt() and 0x0F])
        }
    }.toString()
}

fun Byte.toHexString(): String {
    return StringBuilder().apply {
        val hex = "0123456789ABCDEF"
        append(hex[toInt().ushr(4) and 0x0F])
        append(hex[toInt() and 0x0F])
    }.toString()
}

fun Byte.asInt() = toInt() and 0xFF