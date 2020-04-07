package hyunh.asn1

fun ByteArray.toHexString() : String {
    val hex = "0123456789ABCDEF"
    val builder = StringBuilder(this.size * 2);
    forEach {
        builder.append(hex[it.toInt().ushr(4) and 0x0F])
        builder.append(hex[it.toInt() and 0x0F])
    }
    return builder.toString()
}