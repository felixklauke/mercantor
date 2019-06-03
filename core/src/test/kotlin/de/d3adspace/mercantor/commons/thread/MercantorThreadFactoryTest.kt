package de.d3adspace.mercantor.commons.thread

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorThreadFactoryTest {

    private val prefix = "testPrefix"
    private val threadFactory = MercantorThreadFactory(prefix)

    @Test
    fun newThread() {
        val newThread = threadFactory.newThread({})

        assertEquals(prefix + " #0", newThread.name)
    }
}
