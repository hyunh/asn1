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

class Ber {
    companion object {
        private const val CONSTRUCTED_TAG = 0x20
        private const val HIGH_TAG_NUMBER = 0x1F
    }

    constructor(data: ByteArray) {
    }

    constructor(tag: Int, value: ByteArray? = null) {
    }

    constructor(tag: Int, children: List<Ber>) {
    }

    var tag: Int = 0
        private set

    var length: Int = 0
        private set

    var value: ByteArray? = null
        private set

    val children: List<Ber> by lazy {
        mutableListOf()
    }

    var data: ByteArray? = null
        private set

    fun find(tag: Int): Ber? {
        return null
    }

    fun findAll(tag: Int): List<Ber> {
        return listOf()
    }

    private fun decode(data: ByteArray) {
    }
}