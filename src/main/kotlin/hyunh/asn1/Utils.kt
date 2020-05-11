/**
 * Copyright (C) 2020 Hyunhae Lee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hyunh.asn1

import kotlin.experimental.and

fun Char.hexCharToInt(): Int {
    if (this in '0'..'9') return (this - '0')
    if (this in 'a'..'f') return (this - 'a' + 10)
    if (this in 'A'..'F') return (this - 'A' + 10)

    throw RuntimeException()
}

fun String.hexStringToBytes(): ByteArray {
    val from = toUpperCase()

    val bytes = ByteArray(length / 2)

    for (i in bytes.indices) {
        bytes[i] = (from[i * 2].hexCharToInt().shl(4) or
                from[i * 2 + 1].hexCharToInt()).toByte()
    }

    return bytes
}

fun Byte.toHexString(): String {
    return StringBuilder().apply {
        val hex = "0123456789ABCDEF"
        append(hex[toInt().ushr(4) and 0x0F])
        append(hex[toInt() and 0x0F])
    }.toString()
}

fun Byte.asInt() = toInt() and 0xFF

fun Byte.isSet(target: Byte): Boolean {
    return this and target == target
}

fun Byte.isNotSet(target: Byte) = !isSet(target)

fun ByteArray.toHexString(): String {
    return StringBuilder(this.size * 2).also { builder ->
        val hex = "0123456789ABCDEF"
        forEach {
            builder.append(hex[it.toInt().ushr(4) and 0x0F])
            builder.append(hex[it.toInt() and 0x0F])
        }
    }.toString()
}

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