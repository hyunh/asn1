package hyunh.asn1

import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class UtilsTest {
    @Test
    fun hexCharToHexIntTest() {
        val expected1 = 0
        Assert.assertEquals(expected1, '0'.hexCharToInt())

        val expected2 = 9
        Assert.assertEquals(expected2, '9'.hexCharToInt())

        val expected3 = 10
        Assert.assertEquals(expected3, 'a'.hexCharToInt())
        Assert.assertEquals(expected3, 'A'.hexCharToInt())

        val expected4 = 15
        Assert.assertEquals(expected4, 'f'.hexCharToInt())
        Assert.assertEquals(expected4, 'F'.hexCharToInt())
    }

    @Test(expected = RuntimeException::class)
    fun charToHexIntExceptionTest() {
        'g'.hexCharToInt()
    }

    @Test
    fun hexStringToBytesTest() {
        val expected1 = byteArrayOf(0x00)
        Assert.assertArrayEquals(expected1, "00".hexStringToBytes())

        val expected2 = byteArrayOf(0x01)
        Assert.assertArrayEquals(expected2, "01".hexStringToBytes())

        val expected3 = byteArrayOf(0x1F)
        Assert.assertArrayEquals(expected3, "1F".hexStringToBytes())

        val expected4 = byteArrayOf(0x10)
        Assert.assertArrayEquals(expected4, "10".hexStringToBytes())

        val expected5 = byteArrayOf(0xFF.toByte())
        Assert.assertArrayEquals(expected5, "ff".hexStringToBytes())

        val expected6 = byteArrayOf(0xFF.toByte())
        Assert.assertArrayEquals(expected6, "FF".hexStringToBytes())

        val expected = expected1 + expected2 + expected3 + expected4 + expected5 + expected6
        Assert.assertArrayEquals(expected, "00011F10ffFF".hexStringToBytes())
    }

    @Test
    fun byteArrayToHexStringTest() {
        val actual = byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte()
            , 0xBC.toByte(), 0xDE.toByte(), 0xFF.toByte())
        Assert.assertEquals("123456789ABCDEFF", actual.toHexString())
    }

    @Test
    fun byteToHexStringTest() {
        val actual = 0xFF.toByte()
        Assert.assertEquals("FF", actual.toHexString())
    }
}
