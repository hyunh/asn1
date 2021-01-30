package hyunh.asn1

import org.junit.Assert
import org.junit.Test

class UtilsTest {
    @Test
    fun charHexToIntTest() {
        val expected1 = 0
        Assert.assertEquals(expected1, '0'.hexToInt())

        val expected2 = 9
        Assert.assertEquals(expected2, '9'.hexToInt())

        val expected3 = 10
        Assert.assertEquals(expected3, 'a'.hexToInt())
        Assert.assertEquals(expected3, 'A'.hexToInt())

        val expected4 = 15
        Assert.assertEquals(expected4, 'f'.hexToInt())
        Assert.assertEquals(expected4, 'F'.hexToInt())
    }

    @Test(expected = IllegalStateException::class)
    fun charHexToIntExceptionTest() {
        'g'.hexToInt()
    }

    @Test
    fun stringHexToBytesTest() {
        val expected1 = byteArrayOf(0x00)
        Assert.assertArrayEquals(expected1, "00".hexToBytes())

        val expected2 = byteArrayOf(0x01)
        Assert.assertArrayEquals(expected2, "01".hexToBytes())

        val expected3 = byteArrayOf(0x1F)
        Assert.assertArrayEquals(expected3, "1F".hexToBytes())

        val expected4 = byteArrayOf(0x10)
        Assert.assertArrayEquals(expected4, "10".hexToBytes())

        val expected5 = byteArrayOf(0xFF.toByte())
        Assert.assertArrayEquals(expected5, "ff".hexToBytes())

        val expected6 = byteArrayOf(0xFF.toByte())
        Assert.assertArrayEquals(expected6, "FF".hexToBytes())

        val expected = expected1 + expected2 + expected3 + expected4 + expected5 + expected6
        Assert.assertArrayEquals(expected, "00011F10ffFF".hexToBytes())
    }

    @Test(expected = IllegalStateException::class)
    fun stringHexToBytesInvalidExceptionTest() {
        "ABCDEFGH".hexToBytes()
    }

    @Test(expected = IllegalStateException::class)
    fun stringHexToBytesLengthExceptionTest() {
        "ABCDEFGH".hexToBytes()
    }

    @Test
    fun byteArrayToHexStringTest() {
        val actual = byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte(),
            0xBC.toByte(), 0xDE.toByte(), 0xFF.toByte())
        Assert.assertEquals("123456789ABCDEFF", actual.toHexString())
    }

    @Test
    fun byteToHexStringTest() {
        val actual1 = 0x00.toByte()
        Assert.assertEquals("00", actual1.toHexString())

        val actual2 = 0x7F.toByte()
        Assert.assertEquals("7F", actual2.toHexString())

        val actual3 = 0xFF.toByte()
        Assert.assertEquals("FF", actual3.toHexString())
    }

    @Test
    fun byteAsIntTest() {
        val actual1 = 0x00.toByte()
        Assert.assertEquals(0, actual1.asInt())

        val actual2 = 0x7F.toByte()
        Assert.assertEquals(127, actual2.asInt())

        val actual3 = 0x80.toByte()
        Assert.assertEquals(128, actual3.asInt())
        Assert.assertNotEquals(actual3.toInt(), actual3.asInt())

        val actual4 = 0xFF.toByte()
        Assert.assertEquals(255, actual4.asInt())
        Assert.assertNotEquals(actual4.toInt(), actual4.asInt())
    }

    @Test
    fun byteIsSetTest() {
        val actual1 = 0x01.toByte()
        Assert.assertTrue(actual1.isSet(0x01))

        val actual2 = 0x03.toByte()
        Assert.assertTrue(actual2.isSet(0x01))
        Assert.assertTrue(actual2.isSet(0x02))

        val actual3 = 0x07.toByte()
        Assert.assertTrue(actual3.isSet(0x01))
        Assert.assertTrue(actual3.isSet(0x02))
        Assert.assertTrue(actual3.isSet(0x04))

        val actual4 = 0x0F.toByte()
        Assert.assertTrue(actual4.isSet(0x01))
        Assert.assertTrue(actual4.isSet(0x02))
        Assert.assertTrue(actual4.isSet(0x04))
        Assert.assertTrue(actual4.isSet(0x08))

        val actual5 = 0x10.toByte()
        Assert.assertTrue(actual5.isSet(0x10))

        val actual6 = 0x30.toByte()
        Assert.assertTrue(actual6.isSet(0x10))
        Assert.assertTrue(actual6.isSet(0x20))

        val actual7 = 0x70.toByte()
        Assert.assertTrue(actual7.isSet(0x10))
        Assert.assertTrue(actual7.isSet(0x20))
        Assert.assertTrue(actual7.isSet(0x40))

        val actual8 = 0xF0.toByte()
        Assert.assertTrue(actual8.isSet(0x10))
        Assert.assertTrue(actual8.isSet(0x20))
        Assert.assertTrue(actual8.isSet(0x40))
        Assert.assertTrue(actual8.isSet(0x80.toByte()))
    }

    @Test
    fun byteIsNotSetTest() {
        val actual1 = 0x01.toByte()
        Assert.assertTrue(actual1.isNotSet(0xFE.toByte()))

        val actual2 = 0x03.toByte()
        Assert.assertTrue(actual2.isNotSet(0xFC.toByte()))

        val actual3 = 0x07.toByte()
        Assert.assertTrue(actual3.isNotSet(0xF8.toByte()))

        val actual4 = 0x0F.toByte()
        Assert.assertTrue(actual4.isNotSet(0xF0.toByte()))

        val actual5 = 0x10.toByte()
        Assert.assertTrue(actual5.isNotSet(0xEF.toByte()))

        val actual6 = 0x30.toByte()
        Assert.assertTrue(actual6.isNotSet(0xCF.toByte()))

        val actual7 = 0x70.toByte()
        Assert.assertTrue(actual7.isNotSet(0x8F.toByte()))

        val actual8 = 0xF0.toByte()
        Assert.assertTrue(actual8.isNotSet(0x0F))
    }
}
