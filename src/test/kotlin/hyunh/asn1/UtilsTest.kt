package hyunh.asn1

import org.junit.Assert
import org.junit.Test

class UtilsTest {
    @Test
    fun toHexStringTest() {
        val actual = byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte()
            , 0xBC.toByte(), 0xDE.toByte(), 0xFF.toByte())
        Assert.assertEquals("123456789ABCDEFF", actual.toHexString())
    }
}
