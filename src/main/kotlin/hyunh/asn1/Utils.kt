/**
 * Copyright (C) 2021 Hyunhae Lee
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

private const val HEX ="0123456789ABCDEF"

fun Char.hexToInt() = when (this) {
    in '0'..'9' -> this - '0'
    in 'a'..'f' -> this - 'a' + 10
    in 'A'..'F' -> this - 'A' + 10
    else -> throw IllegalStateException("$this can't be converted to Int")
}

fun String.hexToBytes(): ByteArray {
    if (length % 2 == 1) {
        throw IllegalStateException("Invalid length for HEX")
    }
    return ByteArray(length / 2).apply {
        val from = toUpperCase()
        for (i in indices) {
            this[i] = (from[i * 2].hexToInt().shl(4) or
                    from[i * 2 + 1].hexToInt()).toByte()
        }
    }
}

fun ByteArray.toHexString(): String {
    return StringBuilder().apply {
        this@toHexString.forEach {
            append(HEX[it.toInt().ushr(4) and 0x0F])
            append(HEX[it.toInt() and 0x0F])
        }
    }.toString()
}

fun Byte.toHexString(): String {
    return "${HEX[toInt().ushr(4) and 0x0F]}${HEX[toInt() and 0x0F]}"
}

fun Byte.asInt() = toInt() and 0xFF

fun Byte.isSet(target: Byte) = this and target == target

fun Byte.isNotSet(target: Byte) = !isSet(target)