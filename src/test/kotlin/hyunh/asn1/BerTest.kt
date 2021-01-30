package hyunh.asn1

import org.junit.Assert
import org.junit.Test

class BerTest {
    @Test
    fun isConstructedTest() {
        val constructed = Ber.make(byteArrayOf(0xBF.toByte(), 0x30, 0x00))
        Assert.assertTrue(constructed.isConstructed())

        val primitive = Ber.make(byteArrayOf(0x80.toByte(), 0x00))
        Assert.assertTrue(!primitive.isConstructed())
    }

    @Test
    fun classOfTagTest() {
        val ber1 = Ber.make("0100".hexToBytes())
        Assert.assertEquals(Ber.CLASS_UNIVERSAL, ber1.classOfTag())

        val ber2 = Ber.make("4000".hexToBytes())
        Assert.assertEquals(Ber.CLASS_APPLICATION, ber2.classOfTag())

        val ber3 = Ber.make("8000".hexToBytes())
        Assert.assertEquals(Ber.CLASS_CONTEXT_SPECIFIC, ber3.classOfTag())

        val ber4 = Ber.make("C000".hexToBytes())
        Assert.assertEquals(Ber.CLASS_PRIVATE, ber4.classOfTag())
    }
}