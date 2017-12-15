package de.d3adspace.mercantor.commons.codec

import com.google.gson.JsonSyntaxException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.InputStream
import java.io.OutputStream
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedHashMap

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class GsonJaxRSProviderTest {

    private val provider = GsonJaxRSProvider()

    @Before
    fun setUp() {
    }

    @Test
    fun isReadable() {
        assertTrue(provider.isReadable(GsonJaxRSProviderTest::class.java, GsonJaxRSProviderTest::class.java.genericSuperclass, emptyArray(), MediaType.APPLICATION_JSON_TYPE))
    }

    @Test(expected = JsonSyntaxException::class)
    fun readFrom() {
        provider.readFrom(GsonJaxRSProviderTest::provider.javaClass, GsonJaxRSProviderTest::class.java.genericSuperclass, emptyArray(), MediaType.APPLICATION_JSON_TYPE, MultivaluedHashMap(), Mockito.mock(InputStream::class.java))
    }

    @Test
    fun isWriteable() {
        assertTrue(provider.isWriteable(GsonJaxRSProviderTest::class.java, GsonJaxRSProviderTest::class.java.genericSuperclass, emptyArray(), MediaType.APPLICATION_JSON_TYPE))
    }

    @Test
    fun getSize() {
        assertEquals(-1, provider.getSize(Object(), GsonJaxRSProviderTest::class.java, GsonJaxRSProviderTest::class.java.genericSuperclass, emptyArray(), MediaType.APPLICATION_JSON_TYPE))
    }

    @Test
    fun writeTo() {
        provider.writeTo(Object(), GsonJaxRSProviderTest::provider.javaClass, GsonJaxRSProviderTest::class.java.genericSuperclass, emptyArray(), MediaType.APPLICATION_JSON_TYPE, MultivaluedHashMap(), Mockito.mock(OutputStream::class.java))
    }
}