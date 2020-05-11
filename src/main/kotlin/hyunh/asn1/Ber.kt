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

class Ber private constructor(
    private val data: ByteArray
) {
    companion object {
        private const val LAST_OCTET = 0x80.toByte()
        private const val CONSTRUCTED_TAG: Byte = 0x20
        private const val HIGH_TAG_NUMBER: Byte = 0x1F

        const val CLASS_UNIVERSAL = 0
        const val CLASS_APPLICATION = 1
        const val CLASS_CONTEXT_SPECIFIC = 2
        const val CLASS_PRIVATE = 3

        /**
         * Create Ber object from [data]
         *
         * @param data Encoded data array. This shall not be empty.
         * @throws IllegalArgumentException If [data] is empty, or it is not valid for decoding.
         */
        fun make(data: ByteArray): Ber {
            if (data.isEmpty()) {
                throw IllegalArgumentException("data shall not be empty")
            }
            return Ber(data).apply {
                decode()
            }
        }
    }

    lateinit var identifier: ByteArray
        private set

    lateinit var length: ByteArray
        private set

    var contents: ByteArray? = null
        private set

    fun classOfTag(): Int {
        if (identifier.isNotEmpty()) {
            return identifier.first().asInt().ushr(6)
        }
        throw IllegalStateException("identifier is empty")
    }

    fun isConstructed() = identifier.isNotEmpty() && identifier.first().isSet(0x20)

    private fun decode() {
        var offset = 0

        identifier = decodeIdentifier(data, offset)
        offset += identifier.size

        length = decodeLength(data, offset)
        offset += length.size

        contents = data.copyOfRange(offset, data.size)
        if (isConstructed()) {
            contents?.let {
                decodeChildren(it, offset)
            }
        }
    }

    private fun decodeIdentifier(
            data: ByteArray,
            start: Int
    ): ByteArray {
        if (data.size <= start) {
            throw IndexOutOfBoundsException("data size: ${data.size} start: $start")
        }
        return if (data[start].isSet(HIGH_TAG_NUMBER)) {
            for (offset in start until data.size) {
                if (data[offset].isNotSet(LAST_OCTET)) {
                    return data.copyOfRange(start, offset + 1)
                }
            }
            throw IllegalArgumentException("Last octet can't be identified")
        } else {
            byteArrayOf(data[start])
        }
    }

    private fun decodeLength(
            data: ByteArray,
            start: Int
    ): ByteArray {
        if (data.size <= start) {
            throw IndexOutOfBoundsException("data size: ${data.size} start: $start")
        }
        return when {
            data[start] == 0x80.toByte() -> { // indefinite
                return byteArrayOf(0x80.toByte())
            }
            data[start].isSet(0x80.toByte()) -> { // definite long form
                var offset = start
                var numberOfSubsequentOctet = data[offset++] and 0x7F
                while (numberOfSubsequentOctet-- > 0) {
                    offset++
                }
                return data.copyOfRange(start, offset)
            }
            else -> { // definite short form
                byteArrayOf(data[start])
            }
        }
    }

    private fun decodeChildren(data: ByteArray, start: Int) {
    }
}