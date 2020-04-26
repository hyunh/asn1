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

fun Int.toByteArray(): ByteArray {
    when {
        this <= 0xFF -> {
            return byteArrayOf(toByte())
        }
        this <= 0xFFFF -> {
            return byteArrayOf(
                (this and 0xFF00).ushr(Byte.SIZE_BITS).toByte(),
                (this and 0xFF).toByte()
            )
        }
        this <= 0xFFFFFF -> {
            return byteArrayOf(
                (this and 0xFF0000).ushr(Byte.SIZE_BITS * 2).toByte(),
                (this and 0xFF00).ushr(Byte.SIZE_BITS).toByte(),
                (this and 0xFF).toByte()
            )
        }
        else -> {
            return byteArrayOf(
                (this and 0xFF000000.toInt()).ushr(Byte.SIZE_BITS * 3).toByte(),
                (this and 0xFF0000).ushr(Byte.SIZE_BITS * 2).toByte(),
                (this and 0xFF00).ushr(Byte.SIZE_BITS).toByte(),
                (this and 0xFF).toByte()
            )
        }
    }
}