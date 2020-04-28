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

class Ber private constructor(private val data: ByteArray) {

    companion object {
        private const val LAST_OCTET = 0x80
        private const val CONSTRUCTED_TAG = 0x20
        private const val HIGH_TAG_NUMBER = 0x1F

        fun make(data: ByteArray) = Ber(data).apply {
            decode(data)
        }
    }

    var identifier: Int = 0
        private set

    var length: Int = 0
        private set

    var contents: ByteArray? = null
        private set

    val children: List<Ber> by lazy {
        mutableListOf()
    }

    fun isConstructed() = false

    fun find(tag: Int): Ber? {
        TODO("find")
    }

    fun findAll(tag: Int): List<Ber> {
        return listOf()
    }

    private fun decode(data: ByteArray) {
        if (data.isEmpty()) {
            throw IllegalArgumentException("data shall not be empty")
        }

        var offset = 0
        identifier = decodeIdentifier(data, offset)
        offset += identifier.toByteArray().size

        length = decodeLength(data, offset)
        offset += length.toByteArray().size

        contents = data.copyOfRange(offset, data.size)

        make(data.copyOfRange(0, 0))
    }

    private fun decodeIdentifier(data: ByteArray, start: Int): Int {
        if (data.size <= start) {
            throw IndexOutOfBoundsException()
        }
        var offset = start

        var identifier = data[offset++].asInt()
        if (identifier and HIGH_TAG_NUMBER == HIGH_TAG_NUMBER) {
            while (true) {
                identifier = identifier.shl(Byte.SIZE_BITS) or data[offset++].asInt()
                if (identifier and LAST_OCTET != LAST_OCTET) {
                    break
                }
            }
        }

        return identifier
    }

    private fun decodeLength(data: ByteArray, start: Int): Int {
        if (data.size <= start) {
            throw IndexOutOfBoundsException()
        }
        var offset = start
        var length = data[offset++].asInt()
        return when {
            length == 0x80 -> { // Indefinite
                -1
            }
            length and 0x80 == 0x80 -> { // Long Form
                var numberOfSubsequentOctet = length and 0x7F
                length = 0
                while (numberOfSubsequentOctet-- > 0) {
                    length.shl(Byte.SIZE_BITS)
                    length = length or data[offset++].toInt()
                }
                length
            }
            else -> { //Short Form
                length
            }
        }
    }
}